package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;

/**
 * @author Igor Baiborodine
 */
public class UserLoginForm extends DefaultVerticalLoginForm {

  public UserLoginForm() {
    super();
    addLoginListener(event -> System.out.println("Logged in with user name " + event.getUserName() +
        " and password of length " + event.getPassword().length()));
  }
}
