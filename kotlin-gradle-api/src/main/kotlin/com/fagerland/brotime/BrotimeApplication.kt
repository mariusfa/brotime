package com.fagerland.brotime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BrotimeApplication

fun main(args: Array<String>) {
	runApplication<BrotimeApplication>(*args)
}
