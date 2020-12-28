package com.github.lucasls.hello.spring.security.webmvc.config

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import javax.servlet.http.HttpServletRequest

/**
 * An authentication filter for simple Bearer token check
 */
class SimpleBearerTokenAuthenticationFilter(
    expectedToken: String
) : AbstractPreAuthenticatedProcessingFilter() {
    private val regex = "Bearer (.+)".toRegex()

    init {
        // the authentication manager is responsible for checking if credentials are valid
        setAuthenticationManager { authentication ->
            if (authentication.credentials != expectedToken) {
                throw BadCredentialsException("Invalid Bearer Token")
            }

            // if credentials are valid, returns a new auth token with set role (which means user is authenticated)
            PreAuthenticatedAuthenticationToken(
                authentication.principal,
                authentication.credentials,
                listOf(SimpleGrantedAuthority("ROLE_$USER_ROLE"))
            )
        }
    }

    /** @see AbstractPreAuthenticatedProcessingFilter.getPreAuthenticatedPrincipal */
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): String? {
        // if header was not sent or is not a Bearer, returns null, which means another auth method should be tried
        request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.matches(regex) }
            ?: return null

        // the principal will be set to a fixed `USER_PRINCIPAL` value
        return USER_PRINCIPAL
    }

    /** @see AbstractPreAuthenticatedProcessingFilter.getPreAuthenticatedCredentials */
    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): String {
        // extracts the "xxxxxxxx" of a "Bearer xxxxxxxx" header value to be set as credentials
        return request.getHeader(HttpHeaders.AUTHORIZATION).replace(regex, "$1")
    }

    companion object {
        const val USER_PRINCIPAL = "apiUser"
        const val USER_ROLE = "API_USER"
    }
}