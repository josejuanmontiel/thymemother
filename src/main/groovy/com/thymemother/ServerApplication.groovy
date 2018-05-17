package com.thymemother

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(exclude=[org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class])
class ServerApplication {

	static void main(String[] args) {
		SpringApplication.run ServerApplication, args
	}
}
