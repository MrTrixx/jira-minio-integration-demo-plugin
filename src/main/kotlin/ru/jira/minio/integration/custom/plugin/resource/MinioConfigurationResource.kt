package ru.jira.minio.integration.custom.plugin.resource

import com.sun.jersey.spi.container.ResourceFilters
import ru.jira.minio.integration.custom.plugin.dto.settings.MinioSettings
import ru.jira.minio.integration.custom.plugin.resource.filter.AdminPermissionFilter
import ru.jira.minio.integration.custom.plugin.service.settings.ClusteredPluginSettingsService
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("minio_configuration")
@ResourceFilters(AdminPermissionFilter::class)
class MinioConfigurationResource(
    private val clusteredPluginSettingsService: ClusteredPluginSettingsService
) {

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    fun save(@QueryParam("configureNow") configureNow: Boolean = false, minioSettings: MinioSettings): MinioSettings {
        clusteredPluginSettingsService.save(configureNow, minioSettings)
        return minioSettings
    }

    @GET
    @Produces(APPLICATION_JSON)
    fun get() = clusteredPluginSettingsService.get()
}