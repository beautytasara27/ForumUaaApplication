package com.binus.project.forumuaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//enabling Oauth
@EnableAuthorizationServer
//there is protected data
@EnableResourceServer

@SpringBootApplication
public class ForumUaaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumUaaApplication.class, args);
	}

}
//my OG code
