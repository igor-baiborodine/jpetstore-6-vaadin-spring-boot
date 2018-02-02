package com.kiroule.jpetstore.vaadinspring.ui.event;

import com.google.common.eventbus.EventBus;

import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class UIEventBus implements HasLogger, Serializable {

  private static final long serialVersionUID = -8901267813972113072L;

  private EventBus eventBus;

  public UIEventBus() {
    this.eventBus = new EventBus((throwable, subscriberExceptionContext) -> {
      getLogger().error("Subscriber event error: ", throwable);
    });
    getLogger().info("UI event bus initialized");
  }

  public void register(final Object listener) {
    this.eventBus.register(listener);
  }

  public void unregister(final Object listener) {
    this.eventBus.unregister(listener);
  }

  public void post(final Object event) {
    this.eventBus.post(event);
  }
}
