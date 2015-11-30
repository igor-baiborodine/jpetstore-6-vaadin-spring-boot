package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.component.CartItemListTable;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = CartView.VIEW_NAME)
@ViewConfig(displayName = "Cart")
public class CartView extends AbstractView {

  public static final String VIEW_NAME = "cart";

  @Autowired
  private CartItemListTable cartItemList;

  private Component content;

  @PostConstruct
  void init() {

    content = CurrentCart.isEmpty() ? getEmptyCartLabel() : cartItemList;
    addComponents(getTitle(), content);
    setSizeFull();
    expand(content);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

    super.enter(event);
    if (!CurrentCart.isEmpty()) {
      cartItemList.setBeans(CurrentCart.get().getCartItemList());
    }
  }

  private Label getEmptyCartLabel() {

    Label title = new Label("Your cart is empty!");
    title.addStyleName(JPetStoreTheme.LABEL_H3);
    title.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return title;
  }
}