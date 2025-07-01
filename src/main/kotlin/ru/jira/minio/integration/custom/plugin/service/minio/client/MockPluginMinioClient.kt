package ru.jira.minio.integration.custom.plugin.service.minio.client

import mu.KLogging

// Fully mock wrapper to not integrate with MinioClient giving to set default behavior
// if ru.jira.minio.integration.custom.plugin.service.minio.client.DefaultPluginMinioClient cannot be registered
object MockPluginMinioClient : PluginMinioClient {
    private val logger = KLogging().logger(MockPluginMinioClient::class.java.name)

    override fun bucketExists(bucketName: String) = null

    override fun destroy() {
        logger.info { "I cannot be destroyed" }
    }

}