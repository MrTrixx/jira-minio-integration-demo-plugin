package ru.jira.minio.integration.custom.plugin.service.minio

import mu.KLogging
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.settings.PluginSettingsService

@Component
class CurrentNodeMinioClientConfigurationService(
    private val pluginSettingsService: PluginSettingsService,
    private val minioConfigurationService: MinioConfigurationService
) {

    fun configure() {
        runCatching {
            pluginSettingsService.get().let { minioConfigurationService.configure(it) }
        }.onFailure {
            logger.error(it) { "Error happened during configuring minio client" }
        }
    }

    private companion object : KLogging()
}