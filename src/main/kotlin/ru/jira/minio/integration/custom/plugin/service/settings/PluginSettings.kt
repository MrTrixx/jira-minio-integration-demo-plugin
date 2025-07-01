package ru.jira.minio.integration.custom.plugin.service.settings

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory
import org.springframework.stereotype.Component
import ru.jira.minio.integration.custom.plugin.util.consts.PLUGIN_KEY
import java.time.Duration

@Component
class PluginSettings(
    private val pluginSettingsFactory: PluginSettingsFactory
) {
    fun setMinioEndpoint(minioEndpoint: String) = setProp(MINIO_ENDPOINT_SETTING_KEY, minioEndpoint)
    fun getMinioEndpoint(): String = getStringProp(MINIO_ENDPOINT_SETTING_KEY)

    fun setMinioRegion(minioRegion: String) = setProp(MINIO_REGION_SETTING_KEY, minioRegion)
    fun getMinioRegion(): String = getStringProp(MINIO_REGION_SETTING_KEY)

    fun setMinioAccessKey(minioAccessKey: String) = setProp(MINIO_ACCESS_SETTING_KEY, minioAccessKey)
    fun getMinioAccessKey() = getStringProp(MINIO_ACCESS_SETTING_KEY)


    fun setMinioSecretKey(minioSecretKey: String) = setProp(MINIO_SECRET_SETTING_KEY, minioSecretKey)
    fun getMinioSecretKey() = getStringProp(MINIO_SECRET_SETTING_KEY)

    fun setMinioHttpClientConnectionTimeout(minioHttpClientReadTimeout: Duration) =
        setProp(MINIO_HTTP_CLIENT_CONNECTION_TIMEOUT_SETTING_KEY, minioHttpClientReadTimeout.toString())

    fun getMinioHttpClientConnectionTimeout() = getDurationProp(MINIO_HTTP_CLIENT_CONNECTION_TIMEOUT_SETTING_KEY)

    fun setMinioHttpClientReadTimeout(minioHttpClientReadTimeout: Duration) =
        setProp(MINIO_HTTP_CLIENT_READ_TIMEOUT_SETTING_KEY, minioHttpClientReadTimeout.toString())

    fun getMinioHttpClientReadTimeout() = getDurationProp(MINIO_HTTP_CLIENT_READ_TIMEOUT_SETTING_KEY)

    fun setMinioHttpClientWriteTimeout(minioHttpClientWriteTimeout: Duration) =
        setProp(MINIO_HTTP_CLIENT_WRITE_TIMEOUT_SETTING_KEY, minioHttpClientWriteTimeout.toString())

    fun getMinioHttpClientWriteTimeout() = getDurationProp(MINIO_HTTP_CLIENT_WRITE_TIMEOUT_SETTING_KEY)


    private fun setProp(key: String, value: Any) {
        pluginSettingsFactory.createGlobalSettings().put(key, value)
    }

    private fun getStringProp(key: String) =
        getProp<String>(key)?.takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("$key cannot be empty")

    private fun getDurationProp(key: String) = Duration.parse(getStringProp(key))

    private inline fun <reified T> getProp(key: String): T? = pluginSettingsFactory.createGlobalSettings().get(key) as? T


    private companion object {


        private const val MINIO_ENDPOINT_PREFIX = "minioEndpoint"
        private const val MINIO_ENDPOINT_SETTING_KEY = "$PLUGIN_KEY:$MINIO_ENDPOINT_PREFIX"

        private const val MINIO_REGION_PREFIX = "minioRegion"
        private const val MINIO_REGION_SETTING_KEY = "$PLUGIN_KEY:$MINIO_REGION_PREFIX"

        private const val MINIO_ACCESS_KEY_PREFIX = "accessKey"
        private const val MINIO_ACCESS_SETTING_KEY = "$PLUGIN_KEY:$MINIO_ACCESS_KEY_PREFIX"

        private const val MINIO_SECRET_KEY_PREFIX = "secretKey"
        private const val MINIO_SECRET_SETTING_KEY = "$PLUGIN_KEY:$MINIO_SECRET_KEY_PREFIX"

        private const val MINIO_HTTP_CLIENT_CONNECTION_TIMEOUT_KEY_PREFIX = "connectionTimeout"
        private const val MINIO_HTTP_CLIENT_CONNECTION_TIMEOUT_SETTING_KEY = "$PLUGIN_KEY:$MINIO_HTTP_CLIENT_CONNECTION_TIMEOUT_KEY_PREFIX"

        private const val MINIO_HTTP_CLIENT_READ_TIMEOUT_KEY_PREFIX = "readTimeout"
        private const val MINIO_HTTP_CLIENT_READ_TIMEOUT_SETTING_KEY = "$PLUGIN_KEY:$MINIO_HTTP_CLIENT_READ_TIMEOUT_KEY_PREFIX"

        private const val MINIO_HTTP_CLIENT_WRITE_TIMEOUT_KEY_PREFIX = "writeTimeout"
        private const val MINIO_HTTP_CLIENT_WRITE_TIMEOUT_SETTING_KEY = "$PLUGIN_KEY:$MINIO_HTTP_CLIENT_WRITE_TIMEOUT_KEY_PREFIX"
    }

}