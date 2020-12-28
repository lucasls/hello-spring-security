package com.github.lucasls.hello.spring.security.webmvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloSpringSecurityWebMvcApplication

fun main(args: Array<String>) {
    runApplication<HelloSpringSecurityWebMvcApplication>(*args)
}
