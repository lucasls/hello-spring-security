package com.github.lucasls.hello.spring.security.webflux.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class WebfluxSecurityConfiguration(
    @Value("\${app.expected-token}") val expectedToken: String
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            // creates and registers a web filter for Bearer checking
            addFilterAt(SimpleBearerTokenAuthenticationWebFilter(expectedToken), SecurityWebFiltersOrder.AUTHENTICATION)

            authorizeExchange {
                authorize("/health", permitAll) // `/health` can be accessed without a token
                authorize("/**", authenticated) // all other endpoints require authentication
            }
        }
    }
}