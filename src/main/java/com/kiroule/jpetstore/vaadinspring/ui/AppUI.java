package com.kiroule.jpetstore.vaadinspring.ui;

import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.kiroule.jpetstore.vaadinspring.ui.util.NavBarButtonUpdater;
import com.kiroule.jpetstore.vaadinspring.ui.util.PageTitleUpdater;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;

import javax.annotation.PreDestroy;

/**
 * @author Igor Baiborodine
 */
@Title("JPetStore Vaadin 8 Spring Demo")
@Theme("jpetstoretheme")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
@SpringUI
public class AppUI extends UI implements HasLogger {

  private static final long serialVersionUID = 4670701701584923650L;

  private final SpringViewProvider viewProvider;
  private final MainView mainView;
  private final EventBus.UIEventBus uiEventBus;
  private final PageTitleUpdater pageTitleUpdater;
  private final NavBarButtonUpdater navBarButtonUpdater;

  @Autowired
  public AppUI(SpringViewProvider viewProvider, MainView mainView, EventBus.UIEventBus uiEventBus,
               PageTitleUpdater pageTitleUpdater, NavBarButtonUpdater navBarButtonUpdater) {
    this.viewProvider = viewProvider;
    this.mainView = mainView;
    this.uiEventBus = uiEventBus;
    this.pageTitleUpdater = pageTitleUpdater;
    this.navBarButtonUpdater = navBarButtonUpdater;
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

    Navigator navigator = new Navigator(this, mainView.getViewContainer());
    navigator.addProvider(viewProvider);
    navigator.addViewChangeListener(navBarButtonUpdater);
    navigator.addViewChangeListener(pageTitleUpdater);

    uiEventBus.subscribe(mainView);
    getLogger().info("App UI initialized");
  }
}
