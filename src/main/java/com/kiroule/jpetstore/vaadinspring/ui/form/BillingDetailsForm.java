package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Lists;

import com.eijsink.vaadin.components.formcheckbox.FormCheckBox;
import com.kiroule.jpetstore.vaadinspring.domain.BillingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.data.Validator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification;
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
public class BillingDetailsForm extends AbstractForm<BillingDetails> {

  private static final long serialVersionUID = 9027401445749403710L;

  // Payment method
  private ComboBox cardType = new ComboBox("Card Type");
  private MTextField cardNumber = new MTextField("Card Number").withNullRepresentation("");
  private MTextField expiryDate = new MTextField("Expiry Date (MM/YYYY)").withNullRepresentation("");

  // Billing address
  private TextField firstName = new MTextField("First Name").withNullRepresentation("");
  private TextField lastName = new MTextField("LastName").withNullRepresentation("");
  private TextField email = new MTextField("Email").withNullRepresentation("");
  private TextField phone = new MTextField("Phone").withNullRepresentation("");
  private TextField address1 = new MTextField("Address 1").withNullRepresentation("");
  private TextField address2 = new MTextField("Address 2").withNullRepresentation("");
  private TextField city = new MTextField("City").withNullRepresentation("");
  private TextField state = new MTextField("State").withNullRepresentation("");
  private TextField zip = new MTextField("ZIP Code").withNullRepresentation("");
  private TextField country = new MTextField("Country").withNullRepresentation("");
  private FormCheckBox shipToDifferentAddress = new FormCheckBox("Ship to Different Address", false);

  @PostConstruct
  public void init() {
    setStyleName(JPetStoreTheme.BASE_FORM);
    setEagerValidation(false);
    setHeightUndefined();
  }

  public boolean validate() {

    try {
      getFieldGroup().getFields().forEach(field -> {
        field.focus();
        field.validate();
      });
    } catch (Validator.InvalidValueException e) {
      Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  public boolean isShipToDifferentAddress() {
    return shipToDifferentAddress.getValue();
  }

  public void clear() {
    setEntity(new BillingDetails());
    shipToDifferentAddress.setValue(false);
  }

  @Override
  protected Component createContent() {

    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setCardTypeCombo();
    setToolBarVisible();
    setRequiredFields(cardType, cardNumber, expiryDate, firstName, lastName, email,
        phone, address1, city, state, zip, country);

    MFormLayout paymentMethodFormLayout = new MFormLayout(cardType, cardNumber, expiryDate).withWidth("-1px");
    MFormLayout billingDetailsFormLayout = new MFormLayout(firstName, lastName, email, phone, address1, address2,
        city, state, zip, country, shipToDifferentAddress).withWidth("-1px");

    return new MVerticalLayout(
        new Panel("Payment Method", paymentMethodFormLayout),
        new Panel("Billing Address", billingDetailsFormLayout));
  }

  private void setCardTypeCombo() {

    cardType.addItem("American Express");
    cardType.addItem("Master Card");
    cardType.addItem("Visa");
    cardType.setNullSelectionAllowed(false);
  }

  private void setRequiredFields(Field<?>... fields) {

    Lists.newArrayList(fields).forEach(field -> {
      field.setRequired(true);
      field.setRequiredError(field.getCaption() + " is required");
    });
  }

  private void setToolBarVisible() {

    getSaveButton().setVisible(true);
    getSaveButton().setCaption("Continue");
    getResetButton().setVisible(true);
    getResetButton().setCaption("Clear");
  }
}