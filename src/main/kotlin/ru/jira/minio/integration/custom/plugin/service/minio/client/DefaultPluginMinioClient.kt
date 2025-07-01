package ru.jira.minio.integration.custom.plugin.service.minio.client

import io.minio.MinioClient
import okhttp3.OkHttpClient
import ru.jira.minio.integration.custom.plugin.util.extensions.MinioExtensions.bucketExistsArg

// Wrapper of io.minio.MinioClient to use object this class to manually inject in Spring context as lazy bean (singleton)
class DefaultPluginMinioClient(
    private val minioClient: MinioClient,
    private val httpClient: OkHttpClient
) : PluginMinioClient {

    override fun bucketExists(bucketName: String) = minioClient.bucketExists(bucketExistsArg(bucketName))

    override fun destroy() = MinioHttpClientDestroyer.destroy(httpClient)

}