package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.vaadin.server.VaadinSession;

/**
 * @author Igor Baiborodine
 */
public class CurrentAccount {

  private static final String KEY = "current-account";

  public static void set(Account account) {
    VaadinSession.getCurrent().setAttribute(KEY, account);
  }

  public static Account get() {
    return (Account) VaadinSession.getCurrent().getAttribute(KEY);
  }

  public static boolean isLoggedIn() {
    return get() != null;
  }
}
