package ru.jira.minio.integration.custom.plugin.service.minio

import org.springframework.stereotype.Component

@Component
class MinioClientLifecycleService(
    private val minioConfigurationService: MinioConfigurationService,
    private val currentNodeMinioClientConfigurationService: CurrentNodeMinioClientConfigurationService
) : PluginStarterService, PluginDestroyerService {

    override fun onStart() = currentNodeMinioClientConfigurationService.configure()

    override fun onStop() = minioConfigurationService.destroy()
}