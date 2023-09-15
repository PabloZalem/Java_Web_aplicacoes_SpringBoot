package com.zalempablo.javaspring.javaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*@SpringBootApplication
public class JavaspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaspringApplication.class, args);
	}

}
*/

@SpringBootApplication
public class JavaspringApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(JavaspringApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(JavaspringApplication.class, args);
  }

}