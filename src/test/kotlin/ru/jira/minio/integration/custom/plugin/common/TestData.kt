package ru.jira.minio.integration.custom.plugin.common

import ru.jira.minio.integration.custom.plugin.dto.settings.MinioSettings
import java.time.Duration

internal val DEFAULT_TIMEOUT_MS = Duration.ofMillis(5000L)

internal val MINIO_SETTINGS: MinioSettings = MinioSettings(
    minioEndpoint = "someEndpoint",
    minioRegion = "someRegion",
    minioAccessKey = "someAccessKey",
    minioSecretKey = "someSecretKey",
    minioHttpClientConnectionTimeout = DEFAULT_TIMEOUT_MS,
    minioHttpClientReadTimeout = DEFAULT_TIMEOUT_MS,
    minioHttpClientWriteTimeout = DEFAULT_TIMEOUT_MS,
)

