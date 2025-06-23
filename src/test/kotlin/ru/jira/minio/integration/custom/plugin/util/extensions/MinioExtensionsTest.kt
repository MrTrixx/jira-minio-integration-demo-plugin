package ru.jira.minio.integration.custom.plugin.util.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.jira.minio.integration.custom.plugin.common.MinioBaseTest
import ru.jira.minio.integration.custom.plugin.util.extensions.MinioExtensions.customPingPong

class MinioExtensionsTest : MinioBaseTest() {

    @Test
    fun `should success custom ping pong to check connection`() {
        assertThat(minioClient.customPingPong()).isTrue
    }
}