package com.kiroule.jpetstore.vaadinspring.ui.util;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.BILLING_DETAILS;
import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.SHIPPING_DETAILS;
import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.SHOPPING_CART;

import com.kiroule.jpetstore.vaadinspring.domain.Cart;
import com.vaadin.server.VaadinSession;

/**
 * @author Igor Baiborodine
 */
public class CurrentCart {

  public static void set(Key key, Object object) {
    VaadinSession.getCurrent().setAttribute(key.toString(), object);
  }

  public static Object get(Key key) {
    return VaadinSession.getCurrent().getAttribute(key.toString());
  }

  public static boolean isEmpty() {
    return get(SHOPPING_CART) == null || ((Cart) get(SHOPPING_CART)).getCartItemList().isEmpty();
  }

  public static void clear() {
    set(SHOPPING_CART, null);
    set(BILLING_DETAILS, null);
    set(SHIPPING_DETAILS, null);
  }

  public enum Key {
    SHOPPING_CART,
    BILLING_DETAILS,
    SHIPPING_DETAILS
  }
}
