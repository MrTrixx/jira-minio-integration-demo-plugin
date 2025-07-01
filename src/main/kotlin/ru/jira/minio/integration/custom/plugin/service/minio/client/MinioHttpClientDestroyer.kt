package ru.jira.minio.integration.custom.plugin.service.minio.client

import mu.KLogging
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

object MinioHttpClientDestroyer {
    private val logger = KLogging().logger(MinioHttpClientDestroyer::class.java.name)

    private const val AWAT_TERMINATION_ENV_PROPERTY_KEY = "minio.http.client.await.termination.ms"
    private const val DEFAULT_AWAT_TERMINATION_MS = 500L


    fun destroy(httpClient: OkHttpClient) = with(httpClient) {
        connectionPool.evictAll()
        destroyThreadPool(this)
        destroyCache(this)
    }

    private fun destroyThreadPool(httpClient: OkHttpClient) {
        val executor: ExecutorService = httpClient.dispatcher.executorService
        executor.shutdown()
        try {
            val awaitTermination = System.getenv(AWAT_TERMINATION_ENV_PROPERTY_KEY)?.toLong() ?: DEFAULT_AWAT_TERMINATION_MS
            if (!executor.awaitTermination(awaitTermination, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow()
                if (!executor.awaitTermination(awaitTermination, TimeUnit.MILLISECONDS)) {
                    logger.error { "ExecutorService of minio http client did not terminate" }
                }
            }
        } catch (ie: InterruptedException) {
            logger.error(ie) { "Interrupted exception was thrown during destroying minio http client" }
            Thread.currentThread().interrupt()
            executor.shutdownNow()
        }
    }

    private fun destroyCache(httpClient: OkHttpClient) {
        val cache = httpClient.cache
        if (cache != null) {
            try {
                cache.delete()
            } catch (e: IOException) {
                logger.error(e) { "Error happened during cleaning minio http client cache" }
            }
        }
    }
}