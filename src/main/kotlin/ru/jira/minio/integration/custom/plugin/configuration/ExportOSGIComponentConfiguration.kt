package ru.jira.minio.integration.custom.plugin.configuration

import com.atlassian.sal.api.lifecycle.LifecycleAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.jira.minio.integration.custom.plugin.configuration.OsgiUtil.wrapExportOsgiService
import ru.jira.minio.integration.custom.plugin.service.PluginLauncher

@Configuration
class ExportOSGIComponentConfiguration {

    @Bean
    fun exportPluginLauncher(pluginLauncher: PluginLauncher) = wrapExportOsgiService(pluginLauncher, LifecycleAware::class.java)
}