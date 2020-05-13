package com.gyx.sp.controller.iwant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
	@Bean("Logger")
	public Logger getLogger() {
		return LoggerFactory.getLogger("hillo");
	}

}
