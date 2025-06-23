package ru.jira.minio.integration.custom.plugin.util.extensions

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.jira.minio.integration.custom.plugin.common.MinioBaseTest

class MinioClientTest : MinioBaseTest() {

    @Test
    fun `should create bucket`() {
        // GIVEN
        val makeBucketArgs = MakeBucketArgs.builder().bucket(TEST_BUCKET_NAME).build()
        minioClient.makeBucket(makeBucketArgs)

        // WHEN // THEN
        val bucketExistsArgs = BucketExistsArgs.builder().bucket(TEST_BUCKET_NAME).build()
        assertThat(minioClient.bucketExists(bucketExistsArgs)).isTrue
    }

    private companion object {
        const val TEST_BUCKET_NAME = "test-bucket-1"
    }
}