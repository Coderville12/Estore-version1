package com.gp.electro.store.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class ProjectConfig {
@Bean	
public ModelMapper mapper() {
	return new ModelMapper();
}
	
public ProjectConfig() {
	
}


}
