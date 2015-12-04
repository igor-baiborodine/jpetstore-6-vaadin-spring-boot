package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.ui.form.AccountForm;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = AccountView.VIEW_NAME)
@ViewConfig(displayName = "Account")
public class AccountView extends AbstractView {

  public static final String VIEW_NAME = "account";

  @Autowired
  private AccountForm accountForm;

  @PostConstruct
  void init() {

    accountForm.setEntity(new Account());
    Panel contentPanel = new Panel(accountForm);
    addComponents(createTitleLabel(), contentPanel, accountForm.getToolbar());
    setSizeFull();
    expand(contentPanel);
  }
}