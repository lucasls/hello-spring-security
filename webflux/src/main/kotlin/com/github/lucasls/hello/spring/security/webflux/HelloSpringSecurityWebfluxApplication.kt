package com.github.lucasls.hello.spring.security.webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloSpringSecurityWebfluxApplication

fun main(args: Array<String>) {
    runApplication<HelloSpringSecurityWebfluxApplication>(*args)
}
