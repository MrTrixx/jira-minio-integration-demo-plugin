package ru.jira.minio.integration.custom.plugin.util.extensions

import io.minio.BucketExistsArgs
import io.minio.MinioClient
import mu.KLogging
import org.apache.commons.lang3.StringUtils.EMPTY
import java.util.*

object MinioExtensions : KLogging() {

    fun MinioClient.customPingPong(bucketName: String = UUID.randomUUID().toUuidWithoutDelimiter()) = runCatching {
        bucketExists(
            BucketExistsArgs.Builder().bucket(bucketName).build()
        )
    }.onFailure {
        logger.error(it) { "Cannot ping pong by minio client" }
    }.onSuccess {
        logger.info { "Success custom pin-pong $bucketName" }
    }.isSuccess


    fun bucketExistsArg(bucketName: String): BucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build()

    private fun UUID.toUuidWithoutDelimiter() = toString().replace("-", EMPTY)
}