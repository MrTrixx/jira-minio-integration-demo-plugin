package ru.jira.minio.integration.custom.plugin.service.minio

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import ru.jira.minio.integration.custom.plugin.service.minio.client.PluginMinioClient

// This class accepts Spring proxy (may be as Real or Mock client) - you can control behavior by type
@Service
class MinioService(
    @Lazy
    private val pluginMinioClient: PluginMinioClient
) {

    fun bucketsExists(bucketName: String) {
        runCatching {
            pluginMinioClient.bucketExists(bucketName)
        }.getOrThrow()
    }
}