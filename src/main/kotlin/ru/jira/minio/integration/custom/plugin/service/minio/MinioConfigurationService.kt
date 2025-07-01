package ru.jira.minio.integration.custom.plugin.service.minio

import io.minio.MinioClient
import mu.KLogging
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.dto.settings.MinioSettings
import ru.jira.minio.integration.custom.plugin.service.minio.client.DefaultPluginMinioClient
import ru.jira.minio.integration.custom.plugin.service.minio.client.MinioClientType
import ru.jira.minio.integration.custom.plugin.service.minio.client.PluginMinioClient
import ru.jira.minio.integration.custom.plugin.service.spring.SpringContextAccessor
import ru.jira.minio.integration.custom.plugin.util.extensions.MinioExtensions.customPingPong

@Component
class MinioConfigurationService(
    @Lazy
    private val pluginMinioClient: PluginMinioClient,
    private val springContextAccessor: SpringContextAccessor
) {

    fun configure(minioSettings: MinioSettings): MinioClientType =
        synchronized(pluginMinioClient) { // block all calls on spring lazy proxy while client recreate
            destroy()
            with(minioSettings) {
                val httpClient = OkHttpClient.Builder()
                    .connectTimeout(minioHttpClientConnectionTimeout)
                    .readTimeout(minioHttpClientReadTimeout)
                    .writeTimeout(minioHttpClientWriteTimeout)
                    .build()
                MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .region(minioRegion)
                    .credentials(minioAccessKey, minioSecretKey)
                    .httpClient(httpClient)
                    .build()
                    .let {
                        if (it.customPingPong()) {
                            springContextAccessor.registerDefaultMinioClient(DefaultPluginMinioClient(it, httpClient))
                            logger.info("Real minio client was registered")
                            MinioClientType.DEFAULT
                        } else {
                            logger.error { "Real minio client cannot be registered - wrong configuration" }
                            springContextAccessor.registerMockPluginMinioClient()
                            MinioClientType.MOCK
                        }
                    }
            }
        }

    fun configureMock() {
        synchronized(pluginMinioClient) {
            destroy()
            springContextAccessor.registerMockPluginMinioClient()
        }
    }

    fun destroy() = springContextAccessor.destroyMinioClient()

    private companion object : KLogging()
}