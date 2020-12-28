package com.github.lucasls.hello.spring.security.webmvc.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class WebSecurityConfiguration(
    @Value("\${app.expected-token}") val expectedToken: String
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            // creates and registers a filter for Bearer checking
            addFilterBefore<UsernamePasswordAuthenticationFilter>(SimpleBearerTokenAuthenticationFilter(expectedToken))

            authorizeRequests {
                authorize("/health", permitAll) // `/health` can be accessed without a token
                authorize("/**", authenticated) // all other endpoints require authentication
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
        }
    }
}