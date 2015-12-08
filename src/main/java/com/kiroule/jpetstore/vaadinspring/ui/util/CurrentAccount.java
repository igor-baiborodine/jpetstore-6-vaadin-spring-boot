package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.vaadin.server.VaadinSession;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount.Key.CURRENT_ACCOUNT;

/**
 * @author Igor Baiborodine
 */
public class CurrentAccount {

  public static void set(Account account) {
    VaadinSession.getCurrent().setAttribute(CURRENT_ACCOUNT.toString(), account);
  }

  public static Account get() {
    return (Account) VaadinSession.getCurrent().getAttribute(CURRENT_ACCOUNT.toString());
  }

  public static boolean isLoggedIn() {
    return get() != null;
  }

  public enum Key {
    CURRENT_ACCOUNT
  }
}
