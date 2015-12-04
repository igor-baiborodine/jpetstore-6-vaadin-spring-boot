package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.kiroule.jpetstore.vaadinspring.domain.Item;

/**
 * @author Igor Baiborodine
 */
public class UIAddItemToCartEvent {

  private Item item;

  public UIAddItemToCartEvent(Item item) {
    this.item = item;
  }

  public Item getItem() {
    return item;
  }
}
