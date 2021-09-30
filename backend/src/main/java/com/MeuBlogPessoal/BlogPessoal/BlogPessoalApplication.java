package com.MeuBlogPessoal.BlogPessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BlogPessoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogPessoalApplication.class, args);
	}

}
