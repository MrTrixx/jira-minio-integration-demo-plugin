package ru.jira.minio.integration.custom.plugin.service.settings

import org.springframework.stereotype.Service
import ru.jira.minio.integration.custom.plugin.dto.settings.MinioSettings

@Service
class PluginSettingsService(
    private val pluginSettings: PluginSettings
) {

    fun save(minioSettings: MinioSettings) = with(pluginSettings) {
        setMinioEndpoint(minioSettings.minioEndpoint)
        setMinioRegion(minioSettings.minioRegion)
        setMinioAccessKey(minioSettings.minioAccessKey)
        setMinioSecretKey(minioSettings.minioSecretKey)
        setMinioHttpClientConnectionTimeout(minioSettings.minioHttpClientConnectionTimeout)
        setMinioHttpClientReadTimeout(minioSettings.minioHttpClientReadTimeout)
        setMinioHttpClientWriteTimeout(minioSettings.minioHttpClientWriteTimeout)
    }

    fun get() = with(pluginSettings) {
        MinioSettings(
            minioEndpoint = getMinioEndpoint(),
            minioRegion = getMinioRegion(),
            minioAccessKey = getMinioAccessKey(),
            minioSecretKey = getMinioSecretKey(),
            minioHttpClientConnectionTimeout = getMinioHttpClientConnectionTimeout(),
            minioHttpClientReadTimeout = getMinioHttpClientReadTimeout(),
            minioHttpClientWriteTimeout = getMinioHttpClientWriteTimeout()
        )
    }
}