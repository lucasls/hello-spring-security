# Hello Spring Security

A sample application with simple Bearer Token security, implemented for Web MVC and Webflux

## WebMVC

- [Bearer Token Authentication Filter](https://github.com/lucasls/hello-spring-security/blob/main/webmvc/src/main/kotlin/com/github/lucasls/hello/spring/security/webmvc/config/SimpleBearerTokenAuthenticationFilter.kt)
- [Spring Security Configuration](https://github.com/lucasls/hello-spring-security/blob/main/webmvc/src/main/kotlin/com/github/lucasls/hello/spring/security/webmvc/config/WebSecurityConfiguration.kt)

### Starting the server

```shell
cd webmvc
./gradlew bootRun 
```

Or run the main method at `HelloSpringSecurityWebMvcApplication`

### Calling a protected endpoint with a valid token

```shell
curl --location --request GET 'localhost:8080/' \
--header 'Authorization: Bearer abcd' --include
```
Should return `{"message":"Hello WebMvc World"}`

### Calling a protected endpoint with an invalid token
```shell
curl --location --request GET 'localhost:8080/' \
--header 'Authorization: Bearer 0000' --include
```
Should return an authentication error

### Calling a protected endpoint without a token
```shell
curl --location --request GET 'localhost:8080/' --include
```

Should return an authentication error

### Calling an unprotected endpoint without a token

```shell
curl --location --request GET 'localhost:8080/health' --include
```

Should return `{"message":"OK"}`

## Webflux

- [Bearer Token Authentication Web Filter](https://github.com/lucasls/hello-spring-security/blob/main/webflux/src/main/kotlin/com/github/lucasls/hello/spring/security/webflux/config/SimpleBearerTokenAuthenticationWebFilter.kt)
- [Spring Security Configuration for Webflux](https://github.com/lucasls/hello-spring-security/blob/main/webflux/src/main/kotlin/com/github/lucasls/hello/spring/security/webflux/config/WebfluxSecurityConfiguration.kt)

### Starting the server

```shell
cd webflux
./gradlew bootRun 
```

Or run the main method at `HelloSpringSecurityWebfluxApplication`

### Calling a protected endpoint with a valid token

```shell
curl --location --request GET 'localhost:8081/' \
--header 'Authorization: Bearer 1234' --include
```
Should return `{"message":"Hello Webflux World"}`

### Calling a protected endpoint with an invalid token
```shell
curl --location --request GET 'localhost:8081/' \
--header 'Authorization: Bearer 0000' --include
```
Should return an authentication error

### Calling a protected endpoint without a token

```shell
curl --location --request GET 'localhost:8081/' --include
```

Should return an authentication error

### Calling an unprotected endpoint without a token

```shell
curl --location --request GET 'localhost:8081/health' --include
```

Should return `{"message":"OK"}`