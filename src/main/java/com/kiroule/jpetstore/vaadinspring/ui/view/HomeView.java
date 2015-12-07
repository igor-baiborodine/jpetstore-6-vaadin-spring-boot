package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;

import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = HomeView.VIEW_NAME)
@ViewConfig(displayName = "")
public class HomeView extends AbstractView {

  private static final long serialVersionUID = -6807822856519883640L;

  public static final String VIEW_NAME = "";

  @PostConstruct
  void init() {

    Image splashImage = new Image(null, new ThemeResource("img/splash2.gif"));
    splashImage.setSizeUndefined();
    MVerticalLayout content = new MVerticalLayout(splashImage);
    content.setComponentAlignment(splashImage, Alignment.MIDDLE_CENTER);

    addComponents(content);
    expand(content);
    setSizeFull();
  }
}