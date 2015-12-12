package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import org.vaadin.viritin.MBeanFieldGroup;
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
public class ProductItemForm extends AbstractForm<Item> {

  private static final long serialVersionUID = -3035656440388295692L;

  private Label image = new Label();
  private TextField itemId = new MTextField("ID");
  private TextField itemDescription = new MTextField("Description");
  private TextField productDescription = new MTextField("Product");
  private TextField listPrice = new MTextField("Price");
  private TextField quantity = new MTextField("Stock Quantity");
  private Button addToCartButton = new MButton("Add to Cart");

  @PostConstruct
  public void init() {

    image.setContentMode(ContentMode.HTML);
    listPrice.setConverter(new CurrencyConverter());
    addToCartButton.addClickListener(event -> {
          UI.getCurrent().removeWindow(getPopup());
          UIEventBus.post(new UIAddItemToCartEvent(getEntity()));
        }
    );
    addToCartButton.focus();
    setStyleName(JPetStoreTheme.BASE_FORM);
    setSizeUndefined();
  }

  @Override
  public MBeanFieldGroup<Item> setEntity(Item entity) {

    setReadOnly(false);
    MBeanFieldGroup<Item> fieldGroup = super.setEntity(entity);
    image.setValue(entity.getProduct().getDescription());
    itemDescription.setValue(entity.getAttribute1() + " " + entity.getProduct().getName());
    productDescription.setValue(entity.getProduct().getName());
    setReadOnly(true);

    return fieldGroup;
  }

  @Override
  public void setReadOnly(boolean readOnly) {

    super.setReadOnly(readOnly);
    itemId.setReadOnly(readOnly);
    itemDescription.setReadOnly(readOnly);
    productDescription.setReadOnly(readOnly);
    listPrice.setReadOnly(readOnly);
    quantity.setReadOnly(readOnly);
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