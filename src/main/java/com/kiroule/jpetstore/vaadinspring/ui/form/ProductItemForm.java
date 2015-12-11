package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class ProductItemForm extends AbstractForm<Item> {

  private static final long serialVersionUID = -3035656440388295692L;

  private Label image;
  private TextField itemDescription;
  private TextField id;
  private TextField productDescription;
  private TextField price;
  private TextField stock;
  private Button addToCartButton;

  public ProductItemForm(Item item) {
    init(item);
    setSizeUndefined();
  }

  private void init(Item item) {

    image = new Label(item.getProduct().getDescription(), ContentMode.HTML);
    id = new MTextField("ID", item.getItemId()).withReadOnly(true);
    String itemDescriptionValue = item.getAttribute1() + " " + item.getProduct().getName();
    itemDescription = new MTextField("Description", itemDescriptionValue).withReadOnly(true);
    productDescription = new MTextField("Product", item.getProduct().getName()).withReadOnly(true);

    String listPriceValue = NumberFormat.getCurrencyInstance(Locale.CANADA).format(item.getListPrice());
    price = new MTextField("Price", listPriceValue).withReadOnly(true);
    stock = new MTextField("Stock", String.valueOf(item.getQuantity())).withReadOnly(true);
    addToCartButton = new Button("Add to Cart", event -> {
          UI.getCurrent().removeWindow(getPopup());
          UIEventBus.post(new UIAddItemToCartEvent(item));
        }
    );
  }

  @Override
  protected Component createContent() {

    return new MVerticalLayout(
        image,
        new MFormLayout(
            id,
            itemDescription,
            productDescription,
            price,
            stock
        ).withWidth(""),
        addToCartButton
    ).withWidth("");
  }
}
