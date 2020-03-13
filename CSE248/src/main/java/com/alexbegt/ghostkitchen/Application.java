package com.alexbegt.ghostkitchen;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
