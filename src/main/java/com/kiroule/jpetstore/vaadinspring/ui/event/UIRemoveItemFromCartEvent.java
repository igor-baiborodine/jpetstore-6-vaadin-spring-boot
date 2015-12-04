package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;

/**
 * @author Igor Baiborodine
 */
public class UIRemoveItemFromCartEvent {

  private CartItem cartItem;

  public UIRemoveItemFromCartEvent(CartItem cartItem) {
    this.cartItem = cartItem;
  }

  public CartItem getCartItem() {
    return cartItem;
  }
}
