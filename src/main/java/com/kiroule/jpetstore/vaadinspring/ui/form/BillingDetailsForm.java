package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.kiroule.jpetstore.vaadinspring.domain.BillingDetails;
import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.view.ConfirmOrderView;
import com.kiroule.jpetstore.vaadinspring.ui.view.ShippingDetailsView;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.BILLING_DETAILS;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class BillingDetailsForm extends AbstractForm<BillingDetails> implements HasUIEventBus {

  private static final long serialVersionUID = 9027401445749403710L;

  // Payment method
  private ComboBox cardType = createCardTypeCombo();
  private MTextField cardNumber = new MTextField("Card Number");
  private MTextField expiryDate = new MTextField("Expiry Date (MM/YYYY)");

  // Billing address
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
  private CheckBox shipToDifferentAddress = new CheckBox("Ship to Different Address", false);

  public BillingDetailsForm() {
    super(BillingDetails.class);
  }

  @PostConstruct
  public void init() {
    setHeightUndefined();

    setStyleName(JPetStoreTheme.BASE_FORM);
    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setSavedHandler(billingDetails -> {
      BinderValidationStatus<BillingDetails> status = getBinder().validate();
      if (status.hasErrors()) {
        return;
      }
      CurrentCart.set(BILLING_DETAILS, billingDetails);

      if (isShipToDifferentAddress()) {
        getUIEventBus().publish(this, new UINavigationEvent(ShippingDetailsView.VIEW_NAME));
      } else {
        CurrentCart.set(CurrentCart.Key.SHIPPING_DETAILS, new ShippingDetails(billingDetails));
        getUIEventBus().publish(this, new UINavigationEvent(ConfirmOrderView.VIEW_NAME));
      }
    });
    setResetHandler(billingDetails -> {
      setEntity(new BillingDetails());
      shipToDifferentAddress.setValue(false);
    });
    // explicit binding for combo box
    getBinder()
        .forField(cardType)
        .bind("cardType");
  }

  public boolean isShipToDifferentAddress() {
    return shipToDifferentAddress.getValue();
  }

  @Override
  public Component createContent() {

    getSaveButton().setVisible(true);
    getSaveButton().setCaption("Continue");
    getResetButton().setVisible(true);
    getResetButton().setCaption("Clear");

    MFormLayout paymentMethodFormLayout = new MFormLayout(cardType, cardNumber, expiryDate).withWidth("-1px");
    MFormLayout billingDetailsFormLayout = new MFormLayout(shipToDifferentAddress, firstName, lastName, email, phone,
        address1, address2, city, state, zip, country).withWidth("-1px");

    return new MVerticalLayout(
        new Panel("Payment Method", paymentMethodFormLayout),
        new Panel("Billing Address", billingDetailsFormLayout));
  }

  private ComboBox createCardTypeCombo() {
    ComboBox cardTypeCombo = new ComboBox("Card Type");
    cardTypeCombo.setItems("", "American Express", "Master Card", "Visa");
    cardTypeCombo.setEmptySelectionAllowed(false);
    return cardTypeCombo;
  }
}