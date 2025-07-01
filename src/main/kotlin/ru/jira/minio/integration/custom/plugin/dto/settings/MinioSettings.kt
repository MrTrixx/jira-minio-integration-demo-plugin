package ru.jira.minio.integration.custom.plugin.dto.settings

import com.fasterxml.jackson.annotation.JsonFormat
import ru.jira.minio.integration.custom.plugin.dto.common.RequestPluginDto
import ru.jira.minio.integration.custom.plugin.dto.common.ResponsePluginDto
import java.time.Duration
import javax.validation.constraints.NotBlank

data class MinioSettings(
    @field:NotBlank
    val minioEndpoint: String,
    @field:NotBlank
    val minioRegion: String,
    @field:NotBlank
    val minioAccessKey: String,
    @field:NotBlank
    val minioSecretKey: String,
    @field:JsonFormat(pattern = "MILLIS")
    val minioHttpClientConnectionTimeout: Duration,
    @field:JsonFormat(pattern = "MILLIS")
    val minioHttpClientReadTimeout: Duration,
    @field:JsonFormat(pattern = "MILLIS")
    val minioHttpClientWriteTimeout: Duration,
) : RequestPluginDto(), ResponsePluginDto


