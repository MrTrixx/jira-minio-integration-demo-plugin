package ru.jira.minio.integration.custom.plugin.service

import com.atlassian.event.api.EventListener
import com.atlassian.event.api.EventPublisher
import com.atlassian.plugin.event.events.PluginEnabledEvent
import com.atlassian.sal.api.lifecycle.LifecycleAware
import mu.KLogging
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.minio.PluginDestroyerService
import ru.jira.minio.integration.custom.plugin.service.minio.PluginStarterService
import ru.jira.minio.integration.custom.plugin.util.consts.PLUGIN_KEY
import java.util.*

@Component
class PluginLauncher(
    private val eventPublisher: EventPublisher,
    private val pluginStarterServices: List<PluginStarterService>,
    private val pluginDestroyerServices: List<PluginDestroyerService>
) : InitializingBean, DisposableBean, LifecycleAware {
    private val lifecycleEvents: EnumSet<PluginState> = EnumSet.noneOf(PluginState::class.java)

    @EventListener
    fun onPluginEnabledEvent(pluginEnabledEvent: PluginEnabledEvent) {
        if (PLUGIN_KEY == pluginEnabledEvent.plugin.key) {
            logger.info {"start PluginEventListener#onPluginEnableEvent $PLUGIN_KEY" }

            onPluginReadyState(PluginState.ON_PLUGIN_ENABLED_EVENT)

            logger.info {"end PluginEventListener#onPluginEnableEvent $PLUGIN_KEY" }
        }
    }

    override fun afterPropertiesSet() {
        logger.info { "start PluginEventListener#afterPropertiesSet" }

        eventPublisher.register(this)
        onPluginReadyState(PluginState.AFTER_PROPERTIES_SET)

        logger.info { "end PluginEventListener#afterPropertiesSet" }
    }

    override fun destroy() {
        logger.info {"start PluginEventListener#destroy" }

        eventPublisher.unregister(this)
        pluginDestroyerServices.forEach(PluginDestroyerService::onStop)

        logger.info {"end PluginEventListener#destroy" }
    }

    override fun onStart() {
        logger.info {"start PluginEventListener#onStart"  }

        onPluginReadyState(PluginState.LIFECYCLE_ON_START)

        logger.info {"end PluginEventListener#onStart" }
    }

    override fun onStop() {
        logger.info { "PluginEventListener#onStop" }
    }

    private fun onPluginReadyState(pluginState: PluginState) {
        if (isPluginReady(pluginState)) {
            logger.info { "plugin $PLUGIN_KEY start to work" }
            pluginStarterServices.forEach(PluginStarterService::onStart)
            logger.info { "Successfully started plugin $PLUGIN_KEY" }
        }
    }

    @Synchronized
    private fun isPluginReady(pluginState: PluginState): Boolean {
        lifecycleEvents.add(pluginState)
        return lifecycleEvents.size == PluginState.values().size
    }

    private enum class PluginState {
        AFTER_PROPERTIES_SET,
        ON_PLUGIN_ENABLED_EVENT,
        LIFECYCLE_ON_START;
    }

    private companion object : KLogging()
}