package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = OrderListView.VIEW_NAME)
@ViewConfig(displayName = "My Orders", authRequired = true)
public class OrderListView extends AbstractView {

  private static final long serialVersionUID = -1297358094619700705L;

  public static final String VIEW_NAME = "order-list";

  @PostConstruct
  void init() {

    MVerticalLayout content = new MVerticalLayout(new Label("Not implemented!"));
    Panel contentPanel = new Panel(content);
    addComponents(initTitleLabel(), contentPanel);
    setSizeFull();
    expand(contentPanel);
 }
}