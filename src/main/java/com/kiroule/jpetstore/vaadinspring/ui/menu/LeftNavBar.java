package com.kiroule.jpetstore.vaadinspring.ui.menu;

import com.kiroule.jpetstore.vaadinspring.ui.MainUI;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.view.ProductListView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * @author Igor Baiborodine
 */
public class LeftNavBar extends CssLayout implements ViewChangeListener {

  public LeftNavBar() {

    setHeight("100%");
    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.LEFT_MENU);

    Label logo = new Label("<strong>JPetStore 6 Demo Vaadin</strong>", ContentMode.HTML);
    logo.addStyleName(JPetStoreTheme.MENU_TITLE);
    addComponent(logo);
    addButton("FISH", "Fish");
    addButton("DOGS", "Dogs");
    addButton("CATS", "Cats");
    addButton("REPTILES", "Reptiles");
    addButton("BIRDS", "Birds");
  }

  public void addButton(String categoryId, String displayName) {

    String uri = ProductListView.VIEW_NAME + "/" + categoryId;
    Button viewButton = new Button(displayName, click -> UIEventBus.post(new UINavigationEvent(uri)));
    MainUI.getUriToButtonMap().put(uri, viewButton);

    viewButton.addStyleName(JPetStoreTheme.MENU_ITEM);
    viewButton.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    addComponent(viewButton);
  }

  @Override
  public boolean beforeViewChange(ViewChangeEvent event) {
    return true; // false blocks navigation, always return true here
  }

  @Override
  public void afterViewChange(ViewChangeEvent event) {

    MainUI.getUriToButtonMap().values().forEach(button -> button.removeStyleName(JPetStoreTheme.SELECTED));
    Button button = MainUI.getUriToButtonMap().get(event.getViewName() + "/" + event.getParameters());
    if (button != null) {
      button.addStyleName(JPetStoreTheme.SELECTED);
    }
  }
}
