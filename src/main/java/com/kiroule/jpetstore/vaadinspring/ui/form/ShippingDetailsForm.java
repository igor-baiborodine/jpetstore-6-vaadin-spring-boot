package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.view.ConfirmOrderView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.SHIPPING_DETAILS;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ShippingDetailsForm extends AbstractForm<ShippingDetails> {

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

  @PostConstruct
  public void init() {

    setSavedHandler(shippingDetails -> {
      CurrentCart.set(SHIPPING_DETAILS, shippingDetails);
      UIEventBus.post(new UINavigationEvent(ConfirmOrderView.VIEW_NAME));
    });
    setResetHandler(shippingDetails -> setEntity(new ShippingDetails()));
    setSizeUndefined();
  }

  @Override
  protected Component createContent() {

    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setRequiredFields();
    setToolBarVisible();

    MFormLayout shippingDetailsFormLayout = new MFormLayout(
        firstName,
        lastName,
        email,
        phone,
        address1,
        address2,
        city,
        state,
        zip,
        country
    ).withWidth("");
    shippingDetailsFormLayout.setStyleName(JPetStoreTheme.ACCOUNT_FORM);

    MVerticalLayout content = new MVerticalLayout(
        shippingDetailsFormLayout)
          .withSpacing(false)
          .withWidth("");

    return content;
  }

  private void setRequiredFields() {

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
}