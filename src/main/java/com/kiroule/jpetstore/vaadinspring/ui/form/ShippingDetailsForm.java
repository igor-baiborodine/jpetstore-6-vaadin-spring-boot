package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Lists;

import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

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
public class ShippingDetailsForm extends AbstractForm<ShippingDetails> {

  private static final long serialVersionUID = 3450336789838413879L;

  private TextField firstName = new MTextField("First Name");
  private TextField lastName = new MTextField("LastName");
  private TextField email = new MTextField("Email");
  private TextField phone = new MTextField("Phone");
  private TextField address1 = new MTextField("Address 1");
  private TextField address2 = new MTextField("Address 2");
  private TextField city = new MTextField("City");
  private TextField state = new MTextField("State");
  private TextField zip = new MTextField("ZIP Code");
  private TextField country = new MTextField("Country");

  public ShippingDetailsForm() {
    super(ShippingDetails.class);
  }

  @PostConstruct
  public void init() {
    setStyleName(JPetStoreTheme.BASE_FORM);
    setHeightUndefined();
  }

  public void clear() {
    setEntity(new ShippingDetails());
  }

  @Override
  protected Component createContent() {

    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setToolBarVisible();
    configureFields();

    MFormLayout shippingDetailsFormLayout = new MFormLayout(firstName, lastName, email, phone, address1, address2,
        city, state, zip, country).withWidth("-1px");

    return new MVerticalLayout(
        new Panel("Shipping Address", shippingDetailsFormLayout)
    );
  }

  private void configureFields() {
    Lists.newArrayList(firstName, lastName, email, phone, address1, city, state, zip, country)
        .forEach(field -> getBinder()
            .forField(field)
            .withNullRepresentation("")
            .asRequired(field.getCaption() + " is required"));
  }

  private void setToolBarVisible() {
    getSaveButton().setVisible(true);
    getSaveButton().setCaption("Continue");
    getResetButton().setVisible(true);
    getResetButton().setCaption("Clear");
  }
}