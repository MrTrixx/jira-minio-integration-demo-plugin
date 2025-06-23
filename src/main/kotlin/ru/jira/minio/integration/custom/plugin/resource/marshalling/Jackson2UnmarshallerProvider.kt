package ru.jira.minio.integration.custom.plugin.resource.marshalling

import com.fasterxml.jackson.databind.ObjectMapper
import ru.jira.minio.integration.custom.plugin.dto.common.ResponsePluginDto

import java.io.IOException
import java.io.OutputStream

import java.lang.reflect.Type

import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider

@Provider
@Produces(APPLICATION_JSON)
class Jackson2UnmarshallerProvider(
    private val objectMapper: ObjectMapper
) : MessageBodyWriter<ResponsePluginDto> {

    override fun isWriteable(
        clazz: Class<*>,
        type: Type,
        annotations: Array<Annotation>,
        mediaType: MediaType
    ) = ResponsePluginDto::class.java.isAssignableFrom(clazz)

    override fun getSize(
        obj: ResponsePluginDto,
        clazz: Class<*>,
        type: Type,
        annotations: Array<Annotation>,
        mediaType: MediaType
    ) = -1L

    @Throws(IOException::class, WebApplicationException::class)
    override fun writeTo(
        obj: ResponsePluginDto,
        clazz: Class<*>,
        type: Type,
        annotations: Array<Annotation>,
        mediaType: MediaType,
        multivaluedMap: MultivaluedMap<String, Any>,
        outputStream: OutputStream
    ) = objectMapper.writeValue(outputStream, obj)
}
