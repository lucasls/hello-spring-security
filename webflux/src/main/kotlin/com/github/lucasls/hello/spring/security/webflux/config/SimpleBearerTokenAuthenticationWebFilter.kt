package com.github.lucasls.hello.spring.security.webflux.config

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

/**
 * An authentication web filter for simple Bearer token check
 */
class SimpleBearerTokenAuthenticationWebFilter(
    expectedToken: String
) : AuthenticationWebFilter(ReactiveAuthenticationManager { authentication: Authentication ->
    // the authentication manager is responsible for checking if credentials are valid

    if (authentication.credentials != expectedToken) {
        throw BadCredentialsException("Invalid Bearer Token")
    }

    // if credentials are valid, returns a new auth token with set role (which means user is authenticated)
    PreAuthenticatedAuthenticationToken(
        authentication.principal,
        authentication.credentials,
        listOf(SimpleGrantedAuthority("ROLE_$USER_ROLE"))
    ).toMono()
}) {
    init {
        setServerAuthenticationConverter converter@{ exchange ->
            val regex = "Bearer (.+)".toRegex()

            // if header was not sent or is not a Bearer, returns empty, which means another auth method should be tried
            val authorizationHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
                ?.takeIf { it.matches(regex) }
                ?: return@converter Mono.empty()

            // the principal will be set to a fixed `USER_PRINCIPAL` value
            val principal = USER_PRINCIPAL

            // extracts the "xxxxxxxx" of a "Bearer xxxxxxxx" header value to be set as credentials
            val credentials = authorizationHeader
                .replace(regex, "$1")

            // returns a non-authenticated auth token, that will be checked by the authentication managet
            PreAuthenticatedAuthenticationToken(principal, credentials).toMono()
        }
    }

    companion object {
        const val USER_PRINCIPAL = "apiUser"
        const val USER_ROLE = "API_USER"
    }
}