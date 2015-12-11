package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.ejt.vaadin.loginform.LoginForm;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.view.NewAccountView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class SigninForm extends LoginForm {

  private static final long serialVersionUID = 155111914696296557L;

  private Window popup;

  @Override
  protected Component createContent(TextField usernameField, PasswordField passwordField, Button loginButton) {

    MVerticalLayout layout = new MVerticalLayout(usernameField, passwordField, loginButton);
    usernameField.setWidth(100f, Unit.PERCENTAGE);
    passwordField.setWidth(100f, Unit.PERCENTAGE);
    layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);

    MHorizontalLayout newCustomerLayout = new MHorizontalLayout();
    Label newCustomerLabel = new Label("New Customer?");
    newCustomerLayout.add(newCustomerLabel);
    newCustomerLayout.setComponentAlignment(newCustomerLabel, Alignment.MIDDLE_LEFT);

    Button createAccountButton = new Button("Start Here!");
    createAccountButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
    createAccountButton.addClickListener(event -> {
      UIEventBus.post(new UINavigationEvent(NewAccountView.VIEW_NAME));
      UI.getCurrent().removeWindow(popup);
    });
    newCustomerLayout.add(createAccountButton);

    layout.addComponent(newCustomerLayout);
    layout.setComponentAlignment(newCustomerLayout, Alignment.BOTTOM_RIGHT);

    return layout;
  }

  @Override
  protected String getUserNameFieldCaption() {
    return "Username";
  }

  public Window openInModalWidow() {

    popup = new Window("Sign in", this);
    popup.setModal(true);
    UI.getCurrent().addWindow(popup);
    focusFirst();
    return popup;
  }

  public void focusFirst() {
    ((TextField) this.getState().userNameFieldConnector).focus();
  }
}