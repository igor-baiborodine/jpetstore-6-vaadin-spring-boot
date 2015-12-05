package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.component.CartItemListTable;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Iterator;

import javax.annotation.PostConstruct;

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
  private Label subtotalLabel;

  @PostConstruct
  void init() {
    emptyCartLabel = createEmptyCartLabel();
    subtotalLabel = createSubtotalLabel();

    addComponents(createTitleLabel(), cartItemList, subtotalLabel);
    setSizeFull();
    expand(cartItemList);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

    super.enter(event);

    if (CurrentCart.isEmpty()) {
      if (containsCartItemList()) {
        removeCartItemList();
      }
    } else {
      cartItemList.setBeans(CurrentCart.get().getCartItemList());
      subtotalLabel.setValue(format(SUBTOTAL_LABEL_PATTERN, formatSubtotal(CurrentCart.get().getSubTotal())));
    }
  }

  public void removeCartItemList() {
    replaceComponent(cartItemList, emptyCartLabel);
    removeComponent(subtotalLabel);
  }

  private boolean containsCartItemList() {

    Iterator<Component> it = iterator();
    while (it.hasNext()) {
      Component c = it.next();
      if (c instanceof CartItemListTable) {
        return true;
      }
    }
    return false;
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