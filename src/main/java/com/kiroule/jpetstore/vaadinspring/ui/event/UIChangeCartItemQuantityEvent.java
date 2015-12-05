package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.kiroule.jpetstore.vaadinspring.domain.Item;

/**
 * @author Igor Baiborodine
 */
public class UIChangeCartItemQuantityEvent {

  private Item item;
  private int diff;

  public UIChangeCartItemQuantityEvent(Item item, int changeValue) {
    this.item = item;
    this.diff = changeValue;
  }

  public Item getItem() {
    return item;
  }

  public int getDiff() {
    return diff;
  }
}
