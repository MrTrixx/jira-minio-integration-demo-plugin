package ru.jira.minio.integration.custom.plugin.service.minio.cluster

import com.atlassian.jira.cluster.ClusterManager
import com.atlassian.jira.crowd.embedded.JiraClusterService.SINGLE_NODE_ID


abstract class AbstractMinioClientMessageConsumer(
    private val clusterManager: ClusterManager
) : MinioClusterMessageConsumer {

    override fun receive(channel: String?, message: String?, senderId: String?) {
        if (!clusterManager.isClustered || !isCurrentNodeAlive() || clusterManager.nodeId == SINGLE_NODE_ID) {
            return
        }

        if (getSupportedData().channel != channel) {
            return
        }

        if (message.isNullOrBlank() && getSupportedData().msg != message) {
            return
        }

        runCommand()
    }

    private fun isCurrentNodeAlive() = with(clusterManager.nodeId) {
        this != null && clusterManager.findLiveNodes().any { liveNode -> liveNode.nodeId == this }
    }

}