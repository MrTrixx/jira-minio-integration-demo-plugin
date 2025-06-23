package ru.jira.minio.integration.custom.plugin.configuration

import com.atlassian.plugins.osgi.javaconfig.ExportOptions
import com.atlassian.plugins.osgi.javaconfig.OsgiServices.exportOsgiService
import com.atlassian.plugins.osgi.javaconfig.OsgiServices.importOsgiService
import org.osgi.framework.ServiceRegistration
import org.springframework.beans.factory.FactoryBean

object OsgiUtil {

    fun <T> getComponent(osgiComponentClazz: Class<T>): T = importOsgiService(osgiComponentClazz)

    fun wrapExportOsgiService(
        bean: Any,
        exportAs: Class<*>
    ): FactoryBean<ServiceRegistration<Any>> = exportOsgiService(bean, ExportOptions.`as`(exportAs))
}