package ru.jira.minio.integration.custom.plugin.resource.filter

import com.atlassian.sal.api.user.UserManager
import com.atlassian.sal.api.user.UserProfile
import com.sun.jersey.spi.container.ContainerRequest
import com.sun.jersey.spi.container.ContainerRequestFilter
import com.sun.jersey.spi.container.ResourceFilter
import org.springframework.beans.factory.annotation.Qualifier
import ru.jira.minio.integration.custom.plugin.configuration.ImportOSGIComponentConfiguration.Companion.SAL_API_USER_MANAGER_BEAN_NAME
import javax.ws.rs.ext.Provider

@Provider
class AdminPermissionFilter(
    @Qualifier(SAL_API_USER_MANAGER_BEAN_NAME) private val userManager: UserManager
) : ResourceFilter, ContainerRequestFilter {


    override fun filter(containerRequest: ContainerRequest?): ContainerRequest? {
        val remoteUser = userManager.remoteUser
        if (remoteUser == null || !isAdmin(remoteUser)) {
            throw SecurityException("User must be an Admin to configure this plugin.")
        } else {
            return containerRequest
        }
    }

    override fun getRequestFilter() = this

    override fun getResponseFilter() = null

    private fun isAdmin(remoteUser: UserProfile) = with(remoteUser) {
        userManager.isAdmin(userKey) || userManager.isSystemAdmin(userKey)
    }
}