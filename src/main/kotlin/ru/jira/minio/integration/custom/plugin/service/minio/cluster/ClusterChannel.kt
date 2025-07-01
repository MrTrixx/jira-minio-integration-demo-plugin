package ru.jira.minio.integration.custom.plugin.service.minio.cluster

enum class ClusterChannel(val channel: String, val msg: String) {
    DEFAULT_CLIENT("defmincli", "real"),
    MOCK_CLIENT("mockmincli", "mock")
}