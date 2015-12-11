package com.kiroule.jpetstore.vaadinspring.ui.form;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.BILLING_DETAILS;

import com.eijsink.vaadin.components.formcheckbox.FormCheckBox;
import com.kiroule.jpetstore.vaadinspring.domain.BillingDetails;
import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.view.ConfirmOrderView;
import com.kiroule.jpetstore.vaadinspring.ui.view.ShippingDetailsView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
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

  // Payment method
  private ComboBox cardType = new ComboBox("Card Type");
  private MTextField cardNumber = new MTextField("Card Number").withNullRepresentation("");
  private MTextField expiryDate = new MTextField("Expiry Date (MM/YYYY)").withNullRepresentation("");

  // Billing details
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

    setSavedHandler(billingDetails -> {
      CurrentCart.set(BILLING_DETAILS, billingDetails);
      boolean shipToDifferentAddressSelected = Boolean.valueOf(shipToDifferentAddress.getValue());

      if (shipToDifferentAddressSelected) {
        UIEventBus.post(new UINavigationEvent(ShippingDetailsView.VIEW_NAME));
      } else {
        CurrentCart.set(CurrentCart.Key.SHIPPING_DETAILS, new ShippingDetails(billingDetails));
        UIEventBus.post(new UINavigationEvent(ConfirmOrderView.VIEW_NAME));
      }
    });
    setResetHandler(billingDetails -> setEntity(new BillingDetails()));
    setStyleName("base-form");
    setSizeUndefined();
  }

  @Override
  protected Component createContent() {

    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setCardTypeCombo();
    setRequiredFields();
    setToolBarVisible();

    MFormLayout paymentMethodFormLayout = new MFormLayout(
        cardType,
        cardNumber,
        expiryDate
    ).withWidth("");
    paymentMethodFormLayout.setStyleName(JPetStoreTheme.BASE_FORM);

    MFormLayout billingDetailsFormLayout = new MFormLayout(
        firstName,
        lastName,
        email,
        phone,
        address1,
        address2,
        city,
        state,
        zip,
        country,
        shipToDifferentAddress
    ).withWidth("");
    billingDetailsFormLayout.setStyleName(JPetStoreTheme.BASE_FORM);

    MVerticalLayout content = new MVerticalLayout(
        createSectionTitle("Payment Method"),
        paymentMethodFormLayout,
        createSectionTitle("Billing Details"),
        billingDetailsFormLayout)
          .withSpacing(false)
          .withWidth("");

    return content;
  }

  private void setCardTypeCombo() {

    cardType.addItem("visa");
    cardType.setItemCaption("visa", "Visa");
    cardType.addItem("master");
    cardType.setItemCaption("master", "Master Card");
    cardType.addItem("amex");
    cardType.setItemCaption("amex", "American Express");
    cardType.setNullSelectionAllowed(false);
    cardType.setImmediate(true);
  }

  private void setRequiredFields() {

    cardType.setRequired(true);
    cardNumber.setRequired(true);
    expiryDate.setRequired(true);
    firstName.setRequired(true);
    lastName.setRequired(true);
    email.setRequired(true);
    phone.setRequired(true);
    address1.setRequired(true);
    city.setRequired(true);
    state.setRequired(true);
    zip.setRequired(true);
    country.setRequired(true);
  }

  private void setToolBarVisible() {

    getSaveButton().setVisible(true);
    getSaveButton().setCaption("Continue");
    getResetButton().setVisible(true);
    getResetButton().setCaption("Clear");
  }

  private Label createSectionTitle(String content) {

    Label title = new Label(content);
    title.addStyleName(JPetStoreTheme.LABEL_H3);
    title.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return title;
  }
}