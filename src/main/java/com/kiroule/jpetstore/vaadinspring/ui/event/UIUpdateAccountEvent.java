package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.kiroule.jpetstore.vaadinspring.domain.Account;

/**
 * @author Igor Baiborodine
 */
public class UIUpdateAccountEvent {

  private Account account;

  public UIUpdateAccountEvent(Account account) {
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }
}
