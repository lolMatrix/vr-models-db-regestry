package ru.diplom.vrmodelsdbregestry.exception.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(exception: AuthenticationException): ResponseEntity<Any> {
        log.info(exception.message)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Throwable): ResponseEntity<Any> {
        log.info(exception.message)
        return ResponseEntity.badRequest().body(exception.message)
    }
}