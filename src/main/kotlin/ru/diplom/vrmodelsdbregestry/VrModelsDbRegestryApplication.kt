package ru.diplom.vrmodelsdbregestry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@SpringBootApplication
@EnableWebSecurity
class VrModelsDbRegestryApplication

fun main(args: Array<String>) {
    runApplication<VrModelsDbRegestryApplication>(*args)
}
