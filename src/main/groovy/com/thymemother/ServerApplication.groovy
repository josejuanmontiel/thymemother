package com.thymemother

import com.fasterxml.jackson.databind.ObjectMapper
import com.thymemother.thymeleaf.dialect.ServerJsDialect
import com.thymemother.thymeleaf.engine.ReloadableScriptEngineFactory
import com.thymemother.thymeleaf.resolver.JsonViewResolver
import com.thymemother.thymeleaf.view.ServerJsThymeleafView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.util.MimeType
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.view.ThymeleafViewResolver

@SpringBootApplication(exclude=[org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class])
class ServerApplication {

	static void main(String[] args) {
		SpringApplication.run ServerApplication, args
	}

	@Bean
	public JsonViewResolver jsonViewResolver(ObjectMapper mapper) {
		return new JsonViewResolver(mapper);
	}

	@Bean
	public ReloadableScriptEngineFactory reloadableScriptEngineFactory() {
		ReloadableScriptEngineFactory factory = new ReloadableScriptEngineFactory();
		factory.addScript("classpath:/static/js/polyfill.js");
		factory.addScript("classpath:/static/js/server.bundle.js");
		return factory;
	}

	@Bean
	public ServerJsDialect serverJsDialect(ReloadableScriptEngineFactory engineFactory) {
		return new ServerJsDialect(engineFactory);
	}

}
