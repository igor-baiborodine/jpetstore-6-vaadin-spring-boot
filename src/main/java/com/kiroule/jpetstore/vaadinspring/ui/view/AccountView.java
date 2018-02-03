package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.service.AccountService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIUpdateAccountEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.AccountForm;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.form.AccountForm.Mode.EDIT;
import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = AccountView.VIEW_NAME)
@ViewConfig(displayName = "Account", authRequired = true)
public class AccountView extends AbstractView {

  private static final long serialVersionUID = -6569401295596695005L;

  public static final String VIEW_NAME = "account";

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
        accountService.updateAccount(account);
        accountForm.clear();
        showConfirmation("Your account has been updated.");
        getUIEventBus().publish(AccountView.this, new UIUpdateAccountEvent(account));
      } catch (Throwable t) {
        Notification.show("An error occurred while updating account: " + t.getMessage(), ERROR_MESSAGE);
      }
    });
    accountForm.setResetHandler(account -> getUIEventBus().publish(this, new UINavigationEvent(OrderListView.VIEW_NAME)));

    Panel contentPanel = new Panel(accountForm);
    addComponents(initTitleLabel(), contentPanel, accountForm.getToolbar(EDIT));
    setSizeFull();
    expand(contentPanel);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    Account copy = new Account();
    BeanUtils.copyProperties(CurrentAccount.get(), copy);
    accountForm.setEntity(copy);
    accountForm.setReadOnlyFields(EDIT);
  }
}