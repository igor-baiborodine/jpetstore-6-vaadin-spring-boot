package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;
import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIChangeCartItemQuantityEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIRemoveItemFromCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.grid.MGrid;

import java.text.NumberFormat;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class CartItemListGrid extends MGrid<CartItem> implements HasUIEventBus, HasLogger {

  private static final long serialVersionUID = 6841591708524361792L;

  private final ProductItemForm productItemForm;

  @Autowired
  public CartItemListGrid(ProductItemForm productItemForm) {
    this.productItemForm = productItemForm;

    withFullWidth();
    // Item ID
    addComponentColumn(cartItem -> {
      Button itemIdButton = new GridButton(cartItem.getItem().getItemId(), this::viewDetails);
      itemIdButton.setData(cartItem.getItem());
      itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
      return itemIdButton;})
        .setId("itemId")
        .setCaption("Item ID");
    // Product ID
    addColumn(CartItem::getProductId)
        .setId("productId")
        .setCaption("Product ID");
    // Description
    addColumn(CartItem::getDescription)
        .setId("description")
        .setCaption("Description");
    // In Stock
    addColumn(cartItem -> cartItem.isInStock() ? "yes" : "no")
        .setId("inStock")
        .setCaption("In Stock");
    // Quantity
    addComponentColumn(cartItem -> this.createQuantityField(cartItem))
        .setId("quantity")
        .setCaption("Quantity")
        .setSortable(false);
    // List Price
    addColumn(cartItem -> NumberFormat.getCurrencyInstance().format(cartItem.getListPrice()))
        .setId("listPrice")
        .setCaption("List Price")
        .setSortable(false);
    // Total Cost
    addColumn(cartItem -> NumberFormat.getCurrencyInstance().format(cartItem.getTotal()))
        .setId("total")
        .setCaption("Total")
        .setSortable(false);
    // Remove from Cart
    addComponentColumn(cartItem ->
        new GridButton("Remove", event -> {
          if (CurrentCart.isEmpty()) {
            return;
          }
          getUIEventBus().publish(this, new UIRemoveItemFromCartEvent(cartItem.getItem()));
        }))
        .setId("removeItem")
        .setCaption("")
        .setSortable(false);
  }

  private TextField createQuantityField(CartItem cartItem) {
    final TextField quantityField = new GridTextField();
    quantityField.setWidth(60f, Unit.PIXELS);
    quantityField.addStyleName("align-right");
    quantityField.setValue(String.valueOf(cartItem.getQuantity()));
    final RegexpValidator regexpValidator = new RegexpValidator("Numeric values only", "\\d+");

    quantityField.addValueChangeListener((HasValue.ValueChangeListener) event -> {
      if (((String) event.getValue()).isEmpty()) {
        Notification.show("Must not be empty", Notification.Type.ERROR_MESSAGE);
        return;
      }
      ValidationResult validationResult = regexpValidator.apply((String) event.getValue(), null);
      CartItemListGrid.this.getLogger().debug("quantity validation result: {}", validationResult.isError());

      if (!validationResult.isError()) {
        Integer newQuantity = Integer.valueOf((String) event.getValue());
        CartItemListGrid.this.getUIEventBus().publish(CartItemListGrid.this, new UIChangeCartItemQuantityEvent(
            cartItem.getItem(), newQuantity - cartItem.getQuantity()));
      } else {
        Notification.show("Numeric values only", Notification.Type.ERROR_MESSAGE);
        return;
      }
    });
    return quantityField;
  }

  private void viewDetails(Button.ClickEvent event) {
    productItemForm.getAddToCartButton().setVisible(false);
    Item item = (Item) event.getButton().getData();
    productItemForm.setEntity(item);
    productItemForm.openInModalPopup().setCaption(getPopupCaption(item));
  }

  private String getPopupCaption(Item item) {
    return format("%s | %s", item.getProductId(), item.getProduct().getName());
  }
}