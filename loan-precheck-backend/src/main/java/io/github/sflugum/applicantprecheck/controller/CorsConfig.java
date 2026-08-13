package io.github.sflugum.applicantprecheck.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS so the frontend (a different origin than the backend) is
 * allowed to call the API. The allowed origin is read from a property instead
 * of hardcoded so it can differ between local dev and the deployed Vercel frontend.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${app.cors.allowed-origin:http://localhost:3000}")
	private String allowedOrigins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// allowedOrigins can be a comma-separated list, so split it into an array here.
		registry.addMapping("/**").allowedOrigins(allowedOrigins.split(","))
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600);
	}

}
