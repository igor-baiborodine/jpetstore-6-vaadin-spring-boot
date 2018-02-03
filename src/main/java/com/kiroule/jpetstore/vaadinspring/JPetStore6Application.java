package com.kiroule.jpetstore.vaadinspring;

import com.kiroule.jpetstore.vaadinspring.config.DataSourceConfig;
import com.kiroule.jpetstore.vaadinspring.config.ServiceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.vaadin.spring.events.annotation.EnableEventBus;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Import({DataSourceConfig.class, ServiceConfig.class})
@EnableEventBus
public class JPetStore6Application {

  public static void main(String[] args) {
    SpringApplication.run(JPetStore6Application.class, args);
  }
}
