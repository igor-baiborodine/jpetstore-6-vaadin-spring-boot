package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.service.SigninService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILoginEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILogoutEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.SigninForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.util.NavBarButtonUpdater;
import com.kiroule.jpetstore.vaadinspring.ui.view.AccountView;
import com.kiroule.jpetstore.vaadinspring.ui.view.CartView;
import com.kiroule.jpetstore.vaadinspring.ui.view.HelpView;
import com.kiroule.jpetstore.vaadinspring.ui.view.SearchView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

import static com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil.getDisplayName;
import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class TopNavBar extends CssLayout implements HasUIEventBus {

  private static final long serialVersionUID = -4572532480213767784L;

  public static final String SIGNIN_BUTTON_URI = "sign-in";
  public static final String SIGNOUT_BUTTON_URI = "sign-out";

  private final NavBarButtonUpdater navBarButtonUpdater;
  private final SigninForm signinForm;
  private final SigninService signinService;

  private Button signinButton;
  private Button signoutButton;
  private Label userLabel;

  @Autowired
  public TopNavBar(NavBarButtonUpdater navBarButtonUpdater, SigninForm signinForm, SigninService signinService) {
    this.navBarButtonUpdater = navBarButtonUpdater;
    this.signinForm = signinForm;
    this.signinService = signinService;
  }

  @PostConstruct
  void init() {

    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.TOP_MENU);

    final TextField searchTextField = new TextField();
    searchTextField.setDescription("Product's name contains");
    searchTextField.addShortcutListener(new ShortcutListener("enter-shortcut", ShortcutAction.KeyCode.ENTER, null) {
      private static final long serialVersionUID = 4638926023595229738L;
      @Override
      public void handleAction(Object sender, Object target) {
        searchProducts(((TextField) target).getValue());
      }
    });
    addComponent(searchTextField);

    addButton(SearchView.VIEW_NAME, getDisplayName(SearchView.class),
        event -> searchProducts(searchTextField.getValue()));
    addButton(CartView.VIEW_NAME, getDisplayName(CartView.class));
    addButton(AccountView.VIEW_NAME, getDisplayName(AccountView.class))
        .setVisible(CurrentAccount.isLoggedIn());

    signinButton = addButton(SIGNIN_BUTTON_URI, "Sign in", event -> {
      final Window popup = signinForm.openInModalWidow();
      signinForm.addLoginListener(loginEvent -> {
        try {
          Account account = signinService.login(loginEvent.getLoginParameter("username"),
              loginEvent.getLoginParameter("password"));
          getUIEventBus().publish(this, new UILoginEvent(account));
          UI.getCurrent().removeWindow(popup);
        } catch (LoginException e) {
          Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
          signinForm.focusFirst();
        }
      });
    });
    signinButton.setVisible(!CurrentAccount.isLoggedIn());

    signoutButton = addButton(SIGNOUT_BUTTON_URI, "Sign out", event -> {
      signoutButton.setVisible(false);
      signinButton.setVisible(true);
      getUIEventBus().publish(this, new UILogoutEvent());
    });
    signoutButton.setVisible(CurrentAccount.isLoggedIn());

    addButton(HelpView.VIEW_NAME, getDisplayName(HelpView.class));
    userLabel = new Label();
    userLabel.addStyleName(JPetStoreTheme.WELCOME_USER_LABEL);
    addComponent(userLabel);
  }

  private void searchProducts(String keyword) {

    if (keyword.trim().length() < 3) {
      new Notification("Keyword length must be greater than 2", null, Notification.Type.WARNING_MESSAGE)
          .show(Page.getCurrent());
    } else {
      String uri = SearchView.VIEW_NAME + "/" + keyword.trim().toLowerCase().replaceAll("%", "");
      getUIEventBus().publish(this, new UINavigationEvent(uri));
    }
  }

  private Button addButton(String uri, String caption) {
    return addButton(uri, caption, event -> getUIEventBus().publish(TopNavBar.this, new UINavigationEvent(uri)));
  }

  private Button addButton(String uri, String caption, Button.ClickListener listener) {

    Button button = new Button(caption, listener);
    button.addStyleName(JPetStoreTheme.MENU_ITEM);
    button.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    navBarButtonUpdater.mapButtonToUri(uri, button);
    addComponent(button);
    return button;
  }

  public void updateUserLabel(String firstName) {
    userLabel.setValue(format("Hello, %s!", firstName));
  }
}