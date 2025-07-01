package ru.jira.minio.integration.custom.plugin.service.minio.client

sealed interface PluginMinioClient {

    fun bucketExists(bucketName: String): Boolean?

    fun destroy()

    // add needed your methods to integrate with Minio
}