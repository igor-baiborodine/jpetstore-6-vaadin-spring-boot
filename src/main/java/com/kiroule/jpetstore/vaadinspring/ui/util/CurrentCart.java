package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.domain.Cart;
import com.vaadin.server.VaadinSession;

/**
 * @author Igor Baiborodine
 */
public class CurrentCart {

  public static final String SHOPPING_CART = "shopping-cart";
  public static final String BILLING_DETAILS = "billing-details";
  public static final String SHIPPING_DETAILS = "shipping-details";

  public static void set(String key, Object object) {
    VaadinSession.getCurrent().setAttribute(key, object);
  }

  public static Object get(String key) {
    return VaadinSession.getCurrent().getAttribute(key);
  }

  public static boolean isEmpty() {
    return get(SHOPPING_CART) == null || ((Cart) get(SHOPPING_CART)).getCartItemList().isEmpty();
  }
}
