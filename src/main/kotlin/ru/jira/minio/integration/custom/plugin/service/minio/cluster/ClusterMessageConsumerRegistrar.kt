package ru.jira.minio.integration.custom.plugin.service.minio.cluster

import com.atlassian.jira.cluster.ClusterMessagingService
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.minio.PluginDestroyerService
import ru.jira.minio.integration.custom.plugin.service.minio.PluginStarterService

@Component
class ClusterMessageConsumerRegistrar(
    private val clusterMessagingService: ClusterMessagingService,
    private val minioClusterMessageConsumers: List<MinioClusterMessageConsumer>
) : PluginStarterService, PluginDestroyerService {

    override fun onStart() {
        minioClusterMessageConsumers.forEach { clusterMessagingService.registerListener(it.getSupportedData().channel, it) }
    }

    override fun onStop() {
        minioClusterMessageConsumers.forEach { clusterMessagingService.unregisterListener(it.getSupportedData().channel, it) }
    }

}