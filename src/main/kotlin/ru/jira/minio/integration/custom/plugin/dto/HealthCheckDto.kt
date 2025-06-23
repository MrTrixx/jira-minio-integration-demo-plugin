package ru.jira.minio.integration.custom.plugin.dto

import ru.jira.minio.integration.custom.plugin.dto.common.ResponsePluginDto

data class HealthCheckDto(
    val healthCheckMsg: String
) : ResponsePluginDto