package ru.jira.minio.integration.custom.plugin.service.minio.cluster

import com.atlassian.jira.cluster.ClusterManager
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.minio.CurrentNodeMinioClientConfigurationService
import ru.jira.minio.integration.custom.plugin.service.minio.cluster.ClusterChannel.DEFAULT_CLIENT

@Component
class DefaultMinioClientMessageConsumer(
    clusterManager: ClusterManager,
    private val currentNodeMinioClientConfigurationService: CurrentNodeMinioClientConfigurationService
) : AbstractMinioClientMessageConsumer(clusterManager) {

    override fun runCommand() = currentNodeMinioClientConfigurationService.configure()

    override fun getSupportedData() = DEFAULT_CLIENT
}