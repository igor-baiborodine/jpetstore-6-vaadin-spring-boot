package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.kiroule.jpetstore.vaadinspring.domain.Account;

/**
 * @author Igor Baiborodine
 */
public class UILoginEvent {

  private Account account;

  public UILoginEvent(Account account) {
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }
}
