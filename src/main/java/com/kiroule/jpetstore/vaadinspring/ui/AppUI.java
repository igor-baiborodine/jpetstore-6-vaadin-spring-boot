package com.kiroule.jpetstore.vaadinspring.ui;

import com.kiroule.jpetstore.vaadinspring.ui.navigation.NavigationManager;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;

/**
 * @author Igor Baiborodine
 */
@Title("JPetStore Vaadin 8 Spring Demo")
@Theme("jpetstoretheme")
@SpringUI
public class AppUI extends UI implements HasLogger {

  private static final long serialVersionUID = 4670701701584923650L;

  private final NavigationManager navigationManager;
  private final MainView mainView;
  private final EventBus.UIEventBus uiEventBus;

  @Autowired
  public AppUI(NavigationManager navigationManager, MainView mainView, EventBus.UIEventBus uiEventBus) {
    this.navigationManager = navigationManager;
    this.mainView = mainView;
    this.uiEventBus = uiEventBus;
  }

  public static AppUI getCurrent() {
    return (AppUI) UI.getCurrent();
  }

  public static EventBus.UIEventBus getUiEventBus() {
    return getCurrent().uiEventBus;
  }

  @Override
  protected void init(VaadinRequest request) {
    setContent(mainView);
    uiEventBus.subscribe(mainView);
    navigationManager.navigateToDefaultView();
    getLogger().info("App UI initialized");
  }
}
