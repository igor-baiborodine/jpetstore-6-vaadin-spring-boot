package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.service.AccountService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.AccountForm;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.form.AccountForm.Mode.INSERT;
import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = NewAccountView.VIEW_NAME)
@ViewConfig(displayName = "New Account")
public class NewAccountView extends AbstractView {

  private static final long serialVersionUID = 4140126432578588414L;

  public static final String VIEW_NAME = "new-account";

  @Autowired
  private AccountForm accountForm;
  @Autowired
  private AccountService accountService;

  @PostConstruct
  void init() {

    accountForm.setSavedHandler(account -> {
      try {
        if (!accountForm.validate()) {
          return;
        }
        accountService.insertAccount(account);
        showConfirmation("New account has been created.");
        UIEventBus.post(new UINavigationEvent(HomeView.VIEW_NAME));
      } catch (Throwable t) {
        Notification.show("An error occurred while creating new account: " + t.getMessage(), ERROR_MESSAGE);
      }
    });
    accountForm.setResetHandler(account -> accountForm.clear());

    Panel contentPanel = new Panel(accountForm);
    addComponents(initTitleLabel(), contentPanel, accountForm.getToolbar(INSERT));
    setSizeFull();
    expand(contentPanel);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    if (CurrentAccount.isLoggedIn()) {
      UIEventBus.post(new UINavigationEvent(HomeView.VIEW_NAME));
    }
    accountForm.setEntity(new Account());
    accountForm.setReadOnlyFields(INSERT);
  }
}