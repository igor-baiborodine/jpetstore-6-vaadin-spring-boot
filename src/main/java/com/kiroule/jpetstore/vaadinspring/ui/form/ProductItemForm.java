package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ProductItemForm extends AbstractForm<Item> implements HasUIEventBus {

  private static final long serialVersionUID = -3035656440388295692L;

  private Label image = new Label();
  private TextField itemId = new MTextField("ID");
  private TextField itemDescription = new MTextField("Description");
  private TextField productDescription = new MTextField("Product");
  private TextField listPrice = new MTextField("Price");
  private TextField quantity = new MTextField("Stock Quantity");
  private Button addToCartButton = new MButton("Add to Cart");

  public ProductItemForm() {
    super(Item.class);
    // Override binder initialized in the parent class
    setBinder(new Binder<>(Item.class));
  }

  @PostConstruct
  public void init() {
    setStyleName(JPetStoreTheme.BASE_FORM);
    setSizeUndefined();
    image.setContentMode(ContentMode.HTML);

    // bind will null setter to set read-only mode
    getBinder()
        .forField(itemId)
        .bind(Item::getItemId, null);
    getBinder()
        .forField(itemDescription)
        .bind(Item::getDescription, null);
    getBinder()
        .forField(productDescription)
        .bind(Item::getProductDescription, null);
    getBinder()
        .forField(listPrice)
        .withConverter(new CurrencyConverter())
        .bind(Item::getListPrice, null);
    getBinder()
        .forField(quantity)
        .withConverter(new StringToIntegerConverter(0, null))
        .bind(Item::getQuantity, null);
    addToCartButton.addClickListener(event -> {
          UI.getCurrent().removeWindow(getPopup());
          getUIEventBus().publish(this, new UIAddItemToCartEvent(getBinder().getBean()));
        }
    );
    addToCartButton.focus();
  }

  @Override
  public void setEntity(Item item) {
    image.setValue(item.getProduct().getDescription());
    getBinder().setBean(item);
  }

  public Button getAddToCartButton() {
    return addToCartButton;
  }

  @Override
  protected Component createContent() {

    return new MVerticalLayout(
        image,
        new MFormLayout(
            itemId,
            itemDescription,
            productDescription,
            listPrice,
            quantity
        ).withWidth(""),
        addToCartButton
    ).withWidth("");
  }
}