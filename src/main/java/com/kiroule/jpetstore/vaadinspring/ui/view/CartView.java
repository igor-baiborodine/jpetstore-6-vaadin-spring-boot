package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Cart;
import com.kiroule.jpetstore.vaadinspring.ui.component.CartItemListTable;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.SHOPPING_CART;
import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = CartView.VIEW_NAME)
@ViewConfig(displayName = "Cart")
public class CartView extends AbstractView {

  private static final long serialVersionUID = 7782338853156840634L;

  public static final String VIEW_NAME = "cart";
  public static final String SUBTOTAL_LABEL_PATTERN = "Subtotal: %s";

  @Autowired
  private CartItemListTable cartItemList;

  private Label emptyCartLabel;
  private MVerticalLayout cartItemListLayout;
  private Label subtotalLabel;

  @PostConstruct
  void init() {

    emptyCartLabel = createEmptyCartLabel();
    subtotalLabel = createSubtotalLabel();
    MButton checkOutButton = new MButton()
        .withCaption("Proceed to Checkout")
        .withListener(event -> {
          String viewName = CurrentAccount.isLoggedIn()
              ? BillingDetailsView.VIEW_NAME : AuthRequiredView.VIEW_NAME;
          UIEventBus.post(new UINavigationEvent(viewName));
        });
    MHorizontalLayout subtotalLayout = new MHorizontalLayout(subtotalLabel, checkOutButton);
    cartItemListLayout = new MVerticalLayout(cartItemList, subtotalLayout)
        .withMargin(false)
        .expand(cartItemList);

    addComponents(initTitleLabel(), emptyCartLabel, cartItemListLayout);
    setSizeFull();
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    emptyCartLabel.setVisible(CurrentCart.isEmpty());
    cartItemListLayout.setVisible(!CurrentCart.isEmpty());
    
    if (!CurrentCart.isEmpty()) {

      Cart cart = (Cart) CurrentCart.get(SHOPPING_CART);
      cartItemList.setBeans(cart.getCartItemList());
      subtotalLabel.setValue(format(SUBTOTAL_LABEL_PATTERN, formatSubtotal(cart.getSubTotal())));
      expand(cartItemListLayout);
    } else {
      expand(emptyCartLabel);
    }
  }

  private Label createEmptyCartLabel() {

    Label label = new Label("Your Shopping Cart is empty.");
    label.setStyleName(JPetStoreTheme.CART_VIEW_LABEL);
    return label;
  }

  private Label createSubtotalLabel() {

    Label label = new Label();
    label.addStyleName(JPetStoreTheme.CART_VIEW_LABEL);
    return label;
  }

  private String formatSubtotal(BigDecimal subtotal) {
    return new CurrencyConverter().convertToPresentation(subtotal, String.class, UI.getCurrent().getLocale());
  }
}