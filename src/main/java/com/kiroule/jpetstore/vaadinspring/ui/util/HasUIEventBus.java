package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.ui.AppUI;

import org.vaadin.spring.events.EventBus;

/**
 * @author Igor Baiborodine
 */
public interface HasUIEventBus {

  default EventBus.UIEventBus getUIEventBus() {
    return AppUI.getUiEventBus();
  }
}
