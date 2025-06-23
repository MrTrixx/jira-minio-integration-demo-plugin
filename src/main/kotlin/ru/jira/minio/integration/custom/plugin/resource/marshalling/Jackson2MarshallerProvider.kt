package ru.jira.minio.integration.custom.plugin.resource.marshalling

import com.fasterxml.jackson.databind.ObjectMapper
import ru.jira.minio.integration.custom.plugin.dto.common.RequestPluginDto

import java.io.IOException
import java.io.InputStream

import java.lang.reflect.Type

import javax.ws.rs.Consumes
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.Provider

@Provider
@Consumes(APPLICATION_JSON)
class Jackson2MarshallerProvider(
    private val objectMapper: ObjectMapper
) : MessageBodyReader<RequestPluginDto> {

    override fun isReadable(
        clazz: Class<*>,
        type: Type,
        annotations: Array<Annotation?>,
        mediaType: MediaType
    ) = RequestPluginDto::class.java.isAssignableFrom(clazz)

    @Throws(IOException::class, WebApplicationException::class)
    override fun readFrom(
        clazz: Class<RequestPluginDto>,
        type: Type,
        annotations: Array<Annotation>,
        mediaType: MediaType,
        multivaluedMap: MultivaluedMap<String, String>,
        inputStream: InputStream
    ): RequestPluginDto? = objectMapper.readValue(inputStream, clazz)
}
