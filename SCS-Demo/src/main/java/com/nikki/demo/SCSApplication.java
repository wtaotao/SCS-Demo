package com.nikki.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
@MapperScan("com.nikki.mapper")
public class SCSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SCSApplication.class, args);
	}
}
