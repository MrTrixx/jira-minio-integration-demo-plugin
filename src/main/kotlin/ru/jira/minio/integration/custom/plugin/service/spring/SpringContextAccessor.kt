package ru.jira.minio.integration.custom.plugin.service.spring

import mu.KLogging
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.service.minio.client.DefaultPluginMinioClient
import ru.jira.minio.integration.custom.plugin.service.minio.client.MockPluginMinioClient

@Component
class SpringContextAccessor(
    private val defaultListableBeanFactory: DefaultListableBeanFactory
) {

    fun registerDefaultMinioClient(pluginMinioClient: DefaultPluginMinioClient) {
        logger.info { "Start register minio client ${pluginMinioClient::class.java} as singleton" }

        if (defaultListableBeanFactory.containsSingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME)) {
            throw IllegalArgumentException("$DEFAULT_MINIO_CLIENT_SINGLETON_NAME already exists")
        }

        defaultListableBeanFactory.registerSingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME, pluginMinioClient)

        logger.info { "Successfully registered minio client ${pluginMinioClient::class.java} as singleton" }
    }

    fun destroyMinioClient() {
        logger.info { "Start destroy minio plugin client" }

        if (defaultListableBeanFactory.containsSingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME)) {
            val client = defaultListableBeanFactory.getSingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME)
            if (client is DefaultPluginMinioClient) {
                client.destroy()
                defaultListableBeanFactory.destroySingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME)
            }
        } else if (defaultListableBeanFactory.containsSingleton(MOCK_MINIO_CLIENT_SINGLETON_NAME)) {
            defaultListableBeanFactory.destroySingleton(DEFAULT_MINIO_CLIENT_SINGLETON_NAME)
        }

        logger.info { "Minio plugin client was destroyed" }
    }

    fun registerMockPluginMinioClient() {
        logger.info { "Start register minio client ${MockPluginMinioClient::class.java} as singleton" }

        if (defaultListableBeanFactory.containsSingleton(MOCK_MINIO_CLIENT_SINGLETON_NAME)) {
            throw IllegalArgumentException("$MOCK_MINIO_CLIENT_SINGLETON_NAME already exists")
        }

        defaultListableBeanFactory.registerSingleton(MOCK_MINIO_CLIENT_SINGLETON_NAME, MockPluginMinioClient)

        logger.info { "Successfully registered minio client ${MockPluginMinioClient::class.java} as singleton" }
    }


    private companion object : KLogging() {
        private const val DEFAULT_MINIO_CLIENT_SINGLETON_NAME = "defaultPluginMinioClient"
        private const val MOCK_MINIO_CLIENT_SINGLETON_NAME = "mockPluginMinioClient"

    }
}