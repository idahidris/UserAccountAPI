package com.idris;


import com.idris.interceptors.SwaggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserAccountAPIApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserAccountAPIApplication.class, args);


	}


	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SwaggerFilter());
		return filterRegistrationBean;
	}


}
