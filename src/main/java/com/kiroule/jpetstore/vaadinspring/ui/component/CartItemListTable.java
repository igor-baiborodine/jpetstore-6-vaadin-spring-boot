package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;
import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.BooleanConverter;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIChangeCartItemQuantityEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIRemoveItemFromCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.risto.stepper.IntStepper;
import org.vaadin.viritin.fields.MTable;

import java.math.BigDecimal;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class CartItemListTable extends MTable<CartItem> {

  private static final long serialVersionUID = 6841591708524361792L;

  @Autowired
  private ProductItemForm productItemForm;

  public CartItemListTable() {

    addContainerProperty("listPrice", BigDecimal.class, new BigDecimal("0.0"));
    addContainerProperty("productId", String.class, "XX-XX-00");
    addContainerProperty("description", String.class, "Not Defined");
    addContainerProperty("removeFromCart", Component.class, null);

    withProperties("itemId", "productId", "description", "inStock", "quantity", "listPrice", "total", "removeFromCart");
    withColumnHeaders("Item ID", "Product ID", "Description", "In Stock", "Quantity", "List Price", "Total Cost", "");
    setSortableProperties("itemId", "productId", "description", "inStock");

    withGeneratedColumn("itemId", cartItem -> {
      Button itemIdButton = new Button(cartItem.getItem().getItemId(), this::viewDetails);
      itemIdButton.setData(cartItem.getItem());
      itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
      return itemIdButton;
    });

    withGeneratedColumn("listPrice", cartItem -> {
      // setting a converter on this column will not work, thus format the value explicitly
      return convertToCurrencyPresentation(cartItem.getItem().getListPrice());
    });
    withGeneratedColumn("productId", cartItem -> cartItem.getItem().getProductId());
    withGeneratedColumn("description",
        cartItem -> cartItem.getItem().getAttribute1() + " " + cartItem.getItem().getProduct().getName());
    withGeneratedColumn("quantity", this::createQuantityStepper);
    withGeneratedColumn("removeFromCart",
        cartItem -> new Button("Remove", event -> {
          if (CurrentCart.isEmpty()) {
            return;
          }
          UIEventBus.post(new UIRemoveItemFromCartEvent(cartItem.getItem()));
        }
    ));

    setConverter("inStock", new BooleanConverter());
    setConverter("total", new CurrencyConverter());
    withFullWidth();
  }

  private IntStepper createQuantityStepper(CartItem cartItem) {

    IntStepper quantityStepper = new IntStepper();
    quantityStepper.setMinValue(1);
    quantityStepper.setMaxValue(99);
    quantityStepper.setWidth(60f, Unit.PIXELS);
    quantityStepper.setManualInputAllowed(false);
    quantityStepper.setValue(cartItem.getQuantity());
    quantityStepper.addValueChangeListener(event -> {
      if (CartItemListTable.this.isReadOnly()) {
        event.getProperty().setValue(cartItem.getQuantity());
        return;
      }
      Integer newQuantity = (Integer) event.getProperty().getValue();
      UIEventBus.post(new UIChangeCartItemQuantityEvent(cartItem.getItem(), newQuantity - cartItem.getQuantity()));
    });
    return quantityStepper;
  }

  private void viewDetails(Button.ClickEvent event) {
    if (isReadOnly()) {
      productItemForm.getAddToCartButton().setEnabled(false);
    }
    productItemForm.setEntity((Item) event.getButton().getData());
    productItemForm.openInModalPopup().setCaption("View Details");
  }

  private String convertToCurrencyPresentation(BigDecimal value) {
    return new CurrencyConverter().convertToPresentation(value, String.class, UI.getCurrent().getLocale());
  }
}