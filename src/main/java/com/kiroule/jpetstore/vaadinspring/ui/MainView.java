package com.kiroule.jpetstore.vaadinspring.ui;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.domain.Cart;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIChangeCartItemQuantityEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILoginEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UILogoutEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIRemoveItemFromCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIUpdateAccountEvent;
import com.kiroule.jpetstore.vaadinspring.ui.menu.LeftNavBar;
import com.kiroule.jpetstore.vaadinspring.ui.menu.TopNavBar;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.util.NavBarButtonUpdater;
import com.kiroule.jpetstore.vaadinspring.ui.view.AccountView;
import com.kiroule.jpetstore.vaadinspring.ui.view.AuthRequiredView;
import com.kiroule.jpetstore.vaadinspring.ui.view.CartView;
import com.kiroule.jpetstore.vaadinspring.ui.view.HomeView;
import com.kiroule.jpetstore.vaadinspring.ui.view.ItemListView;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.v7.ui.HorizontalLayout;
import com.vaadin.v7.ui.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import static com.kiroule.jpetstore.vaadinspring.ui.menu.TopNavBar.SIGNIN_BUTTON_URI;
import static com.kiroule.jpetstore.vaadinspring.ui.menu.TopNavBar.SIGNOUT_BUTTON_URI;
import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.SHOPPING_CART;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
class MainView extends HorizontalLayout implements HasLogger, HasUIEventBus {

  private static final long serialVersionUID = 7419653252582861360L;

  private final LeftNavBar leftNavBar;
  private final TopNavBar topNavBar;
  private final CatalogService catalogService;
  private final NavBarButtonUpdater navBarButtonUpdater;
  private final VerticalLayout viewLayout = new VerticalLayout();
  private final VerticalLayout viewContainer = new VerticalLayout();

  @Autowired
  public MainView(LeftNavBar leftNavBar, TopNavBar topNavBar,
                  CatalogService catalogService, NavBarButtonUpdater navBarButtonUpdater) {
    super();
    this.leftNavBar = leftNavBar;
    this.topNavBar = topNavBar;
    this.catalogService = catalogService;
    this.navBarButtonUpdater = navBarButtonUpdater;

    setSizeFull();
    addComponent(this.leftNavBar);

    viewLayout.setSizeFull();
    addComponent(viewLayout);
    setExpandRatio(viewLayout, 1.0f);
    viewLayout.addComponent(this.topNavBar);

    viewContainer.setSizeFull();
    viewLayout.addComponent(viewContainer);
    viewLayout.setExpandRatio(viewContainer, 1.0f);
    getLogger().info("Main view initialized");
  }

  public VerticalLayout getViewContainer() {
    return viewContainer;
  }

  @EventBusListenerMethod
  public void navigateTo(UINavigationEvent event) {
    navigateTo(event.getViewName());
  }

  @EventBusListenerMethod
  public void userLoggedIn(UILoginEvent event) {

    CurrentAccount.set(event.getAccount());
    topNavBar.updateUserLabel(event.getAccount().getFirstName());
    navBarButtonUpdater.setButtonVisible(AccountView.VIEW_NAME, true);
    navBarButtonUpdater.setButtonVisible(SIGNIN_BUTTON_URI, false);
    navBarButtonUpdater.setButtonVisible(SIGNOUT_BUTTON_URI, true);
    navBarButtonUpdater.clear();

    String state = AppUI.getCurrent().getNavigator().getState();
    String viewName = state.equals(AuthRequiredView.VIEW_NAME) ? HomeView.VIEW_NAME : state;
    navigateTo(viewName); // reloading the current view to display the banner
  }

  @EventBusListenerMethod
  public void updateAccount(UIUpdateAccountEvent event) {

    Account account = event.getAccount();
    account.setPassword(null);
    CurrentAccount.set(account);
    topNavBar.updateUserLabel(account.getFirstName());
    navigateTo(AccountView.VIEW_NAME);
  }

  @EventBusListenerMethod
  public void logout(UILogoutEvent event) {

    // Don't invalidate the underlying HTTP session if you are using it for something else
    AppUI.getCurrent().getPage().setLocation("/"); // redirect to the Home page
    VaadinSession.getCurrent().getSession().invalidate();
    VaadinSession.getCurrent().close();
  }

  @EventBusListenerMethod
  public void addItemToCart(UIAddItemToCartEvent event) {

    if (CurrentCart.isEmpty()) {
      CurrentCart.set(SHOPPING_CART, new Cart());
    }
    boolean isInStock = catalogService.isItemInStock(event.getItem().getItemId());
    Cart cart = (Cart) CurrentCart.get(SHOPPING_CART);
    cart.addItem(event.getItem(), isInStock);

    // If an item added from the Item list view, redirect to the Cart view;
    // otherwise reload the current view which could be either Cart or Confirm Order
    String state = AppUI.getCurrent().getNavigator().getState();
    String viewName = state.contains(ItemListView.VIEW_NAME) ? CartView.VIEW_NAME : state;
    navigateTo(viewName);
  }

  @EventBusListenerMethod
  public void removeItemFromCart(UIRemoveItemFromCartEvent event) {

    Cart cart = (Cart) CurrentCart.get(SHOPPING_CART);
    cart.removeItemById(event.getItem().getItemId());
    String viewName = CurrentCart.isEmpty() ? CartView.VIEW_NAME : AppUI.getCurrent().getNavigator().getState();
    navigateTo(viewName);
  }

  @EventBusListenerMethod
  public void changeCartItemQuantity(UIChangeCartItemQuantityEvent event) {
    Cart cart = (Cart) CurrentCart.get(SHOPPING_CART);
    cart.changeQuantityByItemId(event.getItem().getItemId(), event.getDiff());
    navigateTo(AppUI.getCurrent().getNavigator().getState());
  }

  private void navigateTo(String viewName) {
    AppUI.getCurrent().getNavigator().navigateTo(viewName);
  }
}
