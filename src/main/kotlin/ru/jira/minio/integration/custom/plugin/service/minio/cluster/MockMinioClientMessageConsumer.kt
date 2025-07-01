package ru.jira.minio.integration.custom.plugin.service.minio.cluster

import com.atlassian.jira.cluster.ClusterManager
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.minio.MinioConfigurationService
import ru.jira.minio.integration.custom.plugin.service.minio.cluster.ClusterChannel.MOCK_CLIENT

@Component
class MockMinioClientMessageConsumer(
    clusterManager: ClusterManager,
    private val minioConfigurationService: MinioConfigurationService
) : AbstractMinioClientMessageConsumer(clusterManager) {

    override fun runCommand() = minioConfigurationService.configureMock()

    override fun getSupportedData() = MOCK_CLIENT
}