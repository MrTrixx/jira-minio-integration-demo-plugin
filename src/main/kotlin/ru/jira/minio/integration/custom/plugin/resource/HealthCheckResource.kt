package ru.jira.minio.integration.custom.plugin.resource;

import ru.jira.minio.integration.custom.plugin.dto.HealthCheckDto
import javax.ws.rs.GET
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("health_check")
@Produces(APPLICATION_JSON)
class HealthCheckResource {

    @GET
    fun healthCheck() = HealthCheckDto(healthCheckMsg = "OK")
}
