package ru.jira.minio.integration.custom.plugin.service.settings

import com.atlassian.beehive.ClusterLock
import com.atlassian.beehive.ClusterLockService
import com.atlassian.jira.cluster.ClusterManager
import com.atlassian.jira.cluster.ClusterMessagingService
import mu.KLogging
import org.springframework.stereotype.Service
import ru.jira.minio.integration.custom.plugin.dto.settings.MinioSettings
import ru.jira.minio.integration.custom.plugin.service.minio.MinioConfigurationService
import ru.jira.minio.integration.custom.plugin.service.minio.client.MinioClientType.DEFAULT
import ru.jira.minio.integration.custom.plugin.service.minio.client.MinioClientType.MOCK
import ru.jira.minio.integration.custom.plugin.service.minio.cluster.ClusterChannel.DEFAULT_CLIENT
import ru.jira.minio.integration.custom.plugin.service.minio.cluster.ClusterChannel.MOCK_CLIENT
import java.util.concurrent.locks.ReentrantLock

@Service
class ClusteredPluginSettingsService(
    private val pluginSettingsService: PluginSettingsService,
    private val clusterManager: ClusterManager,
    private val minioConfigurationService: MinioConfigurationService,
    private val clusterMessagingService: ClusterMessagingService,
    clusterLockService: ClusterLockService
) {

    private val clusterLock: ClusterLock = clusterLockService.getLockForName(PLUGIN_LOCK)
    private val reentrantLock: ReentrantLock = ReentrantLock()

    fun save(configureNow: Boolean, minioSettings: MinioSettings) {
        if (clusterManager.isClustered) {
            runClusterSave(minioSettings, configureNow)
        } else {
            runLocalSave(minioSettings, configureNow)
        }
    }

    private fun runLocalSave(minioSettings: MinioSettings, configureNow: Boolean) {
        if (reentrantLock.tryLock()) {
            try {
                pluginSettingsService.save(minioSettings)
                if (configureNow) {
                    minioConfigurationService.configure(minioSettings)
                    logger.info { "Successfully saved minio settings to address ${minioSettings.minioEndpoint} on single node ${clusterManager.nodeId}" }
                } else {
                    logger.info { "Successfully saved minio settings to address ${minioSettings.minioEndpoint} on single node ${clusterManager.nodeId}" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error happened during executing minio configuration on single node Jira environment" }
                throw e
            } finally {
                clusterLock.unlock()
            }
        } else {
            throw IllegalStateException("Cannot configure minio client. Wait completing currently running configuration")
        }
    }

    private fun runClusterSave(minioSettings: MinioSettings, configureNow: Boolean) {
        if (clusterLock.tryLock()) {
            try {
                logger.info { "Start save minio settings to address ${minioSettings.minioEndpoint}" }
                pluginSettingsService.save(minioSettings)
                if (configureNow) {
                    val result = minioConfigurationService.configure(minioSettings)
                    when (result) {
                        DEFAULT -> clusterMessagingService.sendRemote(DEFAULT_CLIENT.channel, DEFAULT_CLIENT.msg)
                        MOCK -> clusterMessagingService.sendRemote(MOCK_CLIENT.channel, MOCK_CLIENT.msg)
                    }
                    logger.info { "Successfully saved minio settings to address ${minioSettings.minioEndpoint}" }
                } else {
                    logger.info { "Successfully saved minio settings to address ${minioSettings.minioEndpoint}" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error happened during executing minio configuration on node ${clusterManager.nodeId}" }
                throw e
            } finally {
                clusterLock.unlock()
            }
        } else {
            throw IllegalStateException("Cannot configure minio client. Wait completing currently running configuration")
        }
    }

    fun get() = pluginSettingsService.get()

    private companion object : KLogging() {
        @JvmStatic
        private val PLUGIN_LOCK = "${ClusteredPluginSettingsService::class.java}.minioConfigurationTask"
    }
}