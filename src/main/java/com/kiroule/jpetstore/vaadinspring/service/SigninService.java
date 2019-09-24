package com.kiroule.jpetstore.vaadinspring.service;

import com.kiroule.jpetstore.vaadinspring.domain.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

/**
 * @author Igor Baiborodine
 */
@Component
public class SigninService {

  @Autowired
  private AccountService accountService;

  public Account login(String username, String password) throws LoginException {

    if (!username.isEmpty() && !password.isEmpty()) {
      Account account = accountService.getAccount(username, password);
      if (account == null) {
        throw new LoginException("There was an error with your Username/Password combination. Please try again.");
      }
      return account;
    }
    throw new LoginException("Username and/or Password fields contain empty values.");
  }
}