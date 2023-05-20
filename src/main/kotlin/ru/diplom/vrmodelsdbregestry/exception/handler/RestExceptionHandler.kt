package ru.diplom.vrmodelsdbregestry.exception.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.ResponseEntity

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(): ResponseEntity<Any> {
        log.info("mlem")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Throwable) = ResponseEntity.badRequest().body(null)
}