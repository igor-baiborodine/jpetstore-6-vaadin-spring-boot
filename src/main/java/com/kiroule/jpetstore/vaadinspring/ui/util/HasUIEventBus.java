package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.kiroule.jpetstore.vaadinspring.ui.AppUI;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;

/**
 * @author Igor Baiborodine
 */
public interface HasUIEventBus {

  default UIEventBus getUIEventBus() {
    return AppUI.getUiEventBus();
  }
}
