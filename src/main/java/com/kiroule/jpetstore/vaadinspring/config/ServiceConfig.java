package com.kiroule.jpetstore.vaadinspring.config;

import com.kiroule.jpetstore.vaadinspring.service.AccountService;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.service.SigninService;
import com.kiroule.jpetstore.vaadinspring.service.OrderService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * @author Igor Baiborodine
 */
@Configuration
@EnableTransactionManagement
public class ServiceConfig {

  @Bean
  public AccountService accountService() {
    return new AccountService();
  }

  @Bean
  public CatalogService catalogService() {
    return new CatalogService();
  }

  @Bean
  public OrderService orderService() {
    return new OrderService();
  }

  @Bean
  public SigninService loginService() {
    return new SigninService();
  }

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }
}
