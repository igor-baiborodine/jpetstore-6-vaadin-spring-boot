package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.BillingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.BillingDetailsForm;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasLogger;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = BillingDetailsView.VIEW_NAME)
@ViewConfig(displayName = "Billing Details", authRequired = true)
public class BillingDetailsView extends AbstractView implements HasLogger {

  private static final long serialVersionUID = 259508511415536436L;

  public static final String VIEW_NAME = "billing-details";

  private final BillingDetailsForm billingDetailsForm;

  @Autowired
  public BillingDetailsView(BillingDetailsForm billingDetailsForm) {
    this.billingDetailsForm = billingDetailsForm;
  }

  @PostConstruct
  void init() {
    Panel contentPanel = new Panel(billingDetailsForm);
    addComponents(initTitleLabel(), contentPanel, billingDetailsForm.getToolbar());
    setSizeFull();
    expand(contentPanel);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    if (CurrentCart.isEmpty()) {
      getUIEventBus().publish(this, new UINavigationEvent(CartView.VIEW_NAME));
      return;
    }
    BillingDetails billingDetails = new BillingDetails(CurrentAccount.get());
    billingDetails.setCardType("Visa");
    billingDetails.setCardNumber("9999 9999 9999 9999");
    billingDetails.setExpiryDate("01/1900");

    getLogger().debug("billing details[{}]", billingDetails);
    billingDetailsForm.setEntity(billingDetails);
  }
}