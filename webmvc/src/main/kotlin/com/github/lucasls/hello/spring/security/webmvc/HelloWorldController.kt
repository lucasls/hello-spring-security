package com.github.lucasls.hello.spring.security.webmvc

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    data class Response(
        val message: String
    )

    @GetMapping("/")
    fun helloWorld(): Response {
        return Response("Hello WebMvc World")
    }

    @GetMapping("/health")
    fun health(): Response {
        return Response("OK")
    }
}