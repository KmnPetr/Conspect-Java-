package com.example.FirsRestApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

@SpringBootApplication
public class FirsRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirsRestAppApplication.class, args);
	}

	//это для перемапивания DTO
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
