package ru.jira.minio.integration.custom.plugin.common

import io.minio.ListObjectsArgs
import io.minio.MinioClient
import io.minio.RemoveObjectsArgs
import io.minio.messages.DeleteObject
import mu.KLogging
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.testcontainers.containers.MinIOContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class MinioBaseTest {

    @BeforeEach
    fun cleanUp() {
        clean()
    }

    protected companion object : KLogging() {
        val minioContainer: MinIOContainer =
            MinIOContainer("minio/minio:latest")
                .withCommand("server /data")

        @JvmStatic
        @BeforeAll
        fun init() {
            minioContainer.start()
        }

        @JvmStatic
        private fun clean() {
            minioClient.listBuckets().forEach { bucket ->
                val listObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket.name()).recursive(true).build())
                val objectsToDelete = listObjects.map { bucketObject ->
                    DeleteObject(bucketObject.get().objectName())
                }
                if (objectsToDelete.isNotEmpty()) {
                    minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket.name()).objects(objectsToDelete).build())
                }
            }
        }

        val minioClient: MinioClient by lazy {
            MinioClient.builder()
                .endpoint("http://${minioContainer.host}:${minioContainer.firstMappedPort}")
                .credentials("minioadmin", "minioadmin")
                .build()
        }
    }
}