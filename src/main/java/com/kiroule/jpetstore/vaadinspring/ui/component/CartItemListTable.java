package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;
import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.BooleanConverter;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIChangeCartItemQuantityEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIRemoveItemFromCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.v7.data.Validator;
import com.vaadin.v7.data.validator.RegexpValidator;
import com.vaadin.v7.ui.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritinv7.fields.MTable;
import org.vaadin.viritinv7.fields.MTextField;

import java.math.BigDecimal;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class CartItemListTable extends MTable<CartItem> implements HasUIEventBus {

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
    withGeneratedColumn("quantity", this::createQuantityField);
    withGeneratedColumn("removeFromCart",
        cartItem -> new Button("Remove", event -> {
          if (CurrentCart.isEmpty()) {
            return;
          }
          getUIEventBus().publish(this, new UIRemoveItemFromCartEvent(cartItem.getItem()));
        }
    ));

    setConverter("inStock", new BooleanConverter());
    setConverter("total", new CurrencyConverter());
    withFullWidth();
  }

  private TextField createQuantityField(CartItem cartItem) {
    final TextField quantityField = new MTextField();
    quantityField.setNullSettingAllowed(false);
    quantityField.setWidth(60f, Unit.PIXELS);
    quantityField.addStyleName("align-right");
    quantityField.setValue(String.valueOf(cartItem.getQuantity()));
    final RegexpValidator regexpValidator = new RegexpValidator("\\d+", "Numeric values only");

    quantityField.addValueChangeListener((ValueChangeListener) event -> {
        if (((String) event.getProperty().getValue()).isEmpty()) {
          Notification.show("Must not be empty", Notification.Type.ERROR_MESSAGE);
          return;
        }
        boolean valid = true;
        try {
          regexpValidator.validate(event.getProperty().getValue());
        } catch (Validator.InvalidValueException e) {
          valid = false;
        }
        if (valid) {
          Integer newQuantity = Integer.valueOf((String) event.getProperty().getValue());
          getUIEventBus().publish(this, new UIChangeCartItemQuantityEvent(
              cartItem.getItem(), newQuantity - cartItem.getQuantity()));
        } else {
          Notification.show("Numeric values only", Notification.Type.ERROR_MESSAGE);
        }
    });
    return quantityField;
// TODO: use stepper after migrating to v8 without v7 compatibility mode
//    IntStepper quantityStepper = new IntStepper();
//    quantityStepper.setMinValue(1);
//    quantityStepper.setMaxValue(99);
//    quantityStepper.setWidth(60f, Unit.PIXELS);
//    quantityStepper.setManualInputAllowed(false);
//    quantityStepper.setValue(cartItem.getQuantity());
//    quantityStepper.addValueChangeListener(event -> {
//      if (CartItemListTable.this.isReadOnly()) {
//        event.getSource().setValue(cartItem.getQuantity());
//        return;
//      }
//      Integer newQuantity = event.getSource().getValue();
//      getUIEventBus().publish(this, new UIChangeCartItemQuantityEvent(cartItem.getItem(), newQuantity - cartItem.getQuantity()));
//    });
//    return quantityStepper;
  }

  private void viewDetails(Button.ClickEvent event) {
    if (isReadOnly()) {
      productItemForm.getAddToCartButton().setEnabled(false);
    }
    Item item = (Item) event.getButton().getData();
    productItemForm.setEntity(item);
    productItemForm.openInModalPopup().setCaption(getPopupCaption(item));
  }

  private String getPopupCaption(Item item) {
    return format("%s | %s", item.getProductId(), item.getProduct().getName());
  }

  private String convertToCurrencyPresentation(BigDecimal value) {
    return new CurrencyConverter().convertToPresentation(value, String.class, UI.getCurrent().getLocale());
  }
}