package hera.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}