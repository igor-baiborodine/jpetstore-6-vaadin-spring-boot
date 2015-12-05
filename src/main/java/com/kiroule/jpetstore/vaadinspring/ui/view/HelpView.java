package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Panel;

import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = HelpView.VIEW_NAME)
@ViewConfig(displayName = "Help")
public class HelpView extends AbstractView {

  public static final String VIEW_NAME = "help";

  @PostConstruct
  void init() {

    MVerticalLayout content = new MVerticalLayout(new CustomLayout("help-content-layout"));
    Panel contentPanel = new Panel(content);
    addComponents(createTitleLabel(), contentPanel);
    setSizeFull();
    expand(contentPanel);
 }
}