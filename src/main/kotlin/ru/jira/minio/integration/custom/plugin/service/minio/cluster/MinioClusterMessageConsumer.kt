package ru.jira.minio.integration.custom.plugin.service.minio.cluster

import com.atlassian.jira.cluster.ClusterMessageConsumer

sealed interface MinioClusterMessageConsumer : ClusterMessageConsumer {

    fun getSupportedData(): ClusterChannel

    fun runCommand()
}