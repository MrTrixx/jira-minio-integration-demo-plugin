package ru.jira.minio.integration.custom.plugin.configuration

import com.atlassian.beehive.ClusterLockService
import com.atlassian.event.api.EventPublisher
import com.atlassian.jira.cluster.ClusterManager
import com.atlassian.jira.cluster.ClusterMessagingService
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory
import com.atlassian.sal.api.user.UserManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.jira.minio.integration.custom.plugin.configuration.OsgiUtil.getComponent

@Configuration
class ImportOSGIComponentConfiguration {

    @Bean
    fun pluginSettingsFactory() = getComponent(PluginSettingsFactory::class.java)

    @Bean
    @Qualifier(SAL_API_USER_MANAGER_BEAN_NAME)
    fun salApiUserManager() = getComponent(UserManager::class.java)

    @Bean
    fun clusterManager() = getComponent(ClusterManager::class.java)

    @Bean
    fun clusterLockService() = getComponent(ClusterLockService::class.java)

    @Bean
    fun clusterMessagingService() = getComponent(ClusterMessagingService::class.java)

    @Bean
    fun eventPublisher() = getComponent(EventPublisher::class.java)

    companion object {
        const val SAL_API_USER_MANAGER_BEAN_NAME = "SalApiUserManager"
    }

}