package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.domain.Cart;
import com.vaadin.server.VaadinSession;

/**
 * @author Igor Baiborodine
 */
public class CurrentCart {

  private static final String KEY = "current-count";

  public static void set(Cart cart) {
    VaadinSession.getCurrent().setAttribute(KEY, cart);
  }

  public static Cart get() {
    return (Cart) VaadinSession.getCurrent().getAttribute(KEY);
  }

  public static boolean isEmpty() {
    return get() == null;
  }
}
