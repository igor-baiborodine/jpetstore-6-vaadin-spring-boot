package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.ejt.vaadin.loginform.LoginForm;
import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.service.LoginService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILoginEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.security.auth.login.LoginException;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class SigninForm extends LoginForm {

  @Autowired
  private LoginService loginService;

  private Window parentWindow;

  @Override
  protected Component createContent(TextField textField, PasswordField passwordField, Button loginButton) {

    textField.setValue("guest");
    passwordField.setValue("guest");
    MVerticalLayout layout = new MVerticalLayout(textField, passwordField, loginButton);
    layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);

    MHorizontalLayout newCustomerLayout = new MHorizontalLayout();
    Label newCustomerLabel = new Label("New Customer?");
    newCustomerLayout.add(newCustomerLabel);
    newCustomerLayout.setComponentAlignment(newCustomerLabel, Alignment.MIDDLE_LEFT);

    Button createAccountButton = new Button("Start here.");
    createAccountButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
    createAccountButton.addClickListener(event -> {
      UIEventBus.post(new UINavigationEvent("account"));
      UI.getCurrent().removeWindow(parentWindow);
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

  @Override
  protected void login(String username, String password) {

    try {
      Account account = loginService.login(username, password);
      UIEventBus.post(new UILoginEvent(account));
      UI.getCurrent().removeWindow(parentWindow);
      Page.getCurrent().reload();
    } catch (LoginException e) {
      Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
      focusFirst();
    }
  }

  public Window openInModalWidow() {

    parentWindow = new Window("Sign in", this);
    parentWindow.setModal(true);
    UI.getCurrent().addWindow(parentWindow);
    focusFirst();
    return parentWindow;
  }

  private void focusFirst() {
    ((TextField) this.getState().userNameFieldConnector).focus();
  }
}
