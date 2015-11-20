package com.kiroule.jpetstore.vaadinspring.ui.menu;

import static com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil.getDisplayName;
import static java.lang.String.format;

import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILogoutEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.SigninForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.NavBarButtonUpdater;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class TopNavBar extends CssLayout {

  public static final String SIGNIN_BUTTON_URI = "sign-in";
  public static final String SIGNIN_CAPTION = "Sing in";
  public static final String SIGNOUT_CAPTION = "Sign out";

  @Autowired
  private NavBarButtonUpdater navBarButtonUpdater;
  @Autowired
  private SigninForm signinForm;

  private Label userLabel;

  @PostConstruct
  void init() {

    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.TOP_MENU);

    final TextField searchTextField = new TextField();
    searchTextField.setImmediate(true);
    searchTextField.addShortcutListener(new ShortcutListener("enter-shortcut", ShortcutAction.KeyCode.ENTER, null) {
      @Override
      public void handleAction(Object sender, Object target) {
        searchProducts(((TextField) target).getValue());
      }
    });
    addComponent(searchTextField);

    addButton(SearchView.VIEW_NAME, getDisplayName(SearchView.class),
        event -> searchProducts(searchTextField.getValue()));
    addSigninButton();
    addButton(CartView.VIEW_NAME, getDisplayName(CartView.class));
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
      UIEventBus.post(new UINavigationEvent(uri));
    }
  }

  private Button addSigninButton() {

    String caption = CurrentAccount.isLoggedIn() ? SIGNOUT_CAPTION : SIGNIN_CAPTION;
    Button signinButton = new Button(caption);
    navBarButtonUpdater.mapButtonToUri(SIGNIN_BUTTON_URI, signinButton);

    signinButton.addClickListener(event -> {
      navBarButtonUpdater.setSelectedButton(SIGNIN_BUTTON_URI);
      if (CurrentAccount.isLoggedIn()) {
        UIEventBus.post(new UILogoutEvent());
      } else {
        signinForm.openInModalWidow();
      }
    });
    setButtonStyle(signinButton);
    addComponent(signinButton);
    return signinButton;
  }

  private Button addButton(String viewName, String displayName) {
    return addButton(viewName, displayName, null);
  }

  private Button addButton(String viewName, String displayName, Button.ClickListener clickListener) {

    String uri = viewName;
    if (clickListener == null) {
      clickListener = event -> UIEventBus.post(new UINavigationEvent(uri));
    }
    Button viewButton = new Button(displayName, clickListener);
    navBarButtonUpdater.mapButtonToUri(uri, viewButton);

    setButtonStyle(viewButton);
    addComponent(viewButton);
    return viewButton;
  }

  private void setButtonStyle(Button button) {
    button.addStyleName(JPetStoreTheme.MENU_ITEM);
    button.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
  }

  public void updateUserLabel(String firstName) {
    userLabel.setValue(format("Hello, %s!", firstName));
  }
}