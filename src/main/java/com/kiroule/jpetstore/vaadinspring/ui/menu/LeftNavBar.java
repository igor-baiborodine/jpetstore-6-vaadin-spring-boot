package com.kiroule.jpetstore.vaadinspring.ui.menu;

import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.NavBarButtonUpdater;
import com.kiroule.jpetstore.vaadinspring.ui.view.ProductListView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class LeftNavBar extends CssLayout {

  private static final long serialVersionUID = 3267397700833804590L;

  @Autowired
  private NavBarButtonUpdater navBarButtonUpdater;

  @PostConstruct
  public void init() {

    setHeight("100%");
    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.LEFT_MENU);

    Label logo = new Label("<strong>JPetStore 6 Demo Vaadin</strong>", ContentMode.HTML);
    logo.addStyleName(JPetStoreTheme.MENU_TITLE);
    addComponent(logo);
    addButton("BIRDS", "Birds");
    addButton("CATS", "Cats");
    addButton("DOGS", "Dogs");
    addButton("FISH", "Fish");
    addButton("REPTILES", "Reptiles");
  }

  private void addButton(String categoryId, String displayName) {

    String uri = ProductListView.VIEW_NAME + "/" + categoryId;
    Button viewButton = new Button(displayName, click -> UIEventBus.post(new UINavigationEvent(uri)));
    navBarButtonUpdater.mapButtonToUri(uri, viewButton);

    viewButton.addStyleName(JPetStoreTheme.MENU_ITEM);
    viewButton.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    addComponent(viewButton);
  }
}
