package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.ShippingDetailsForm;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.BILLING_DETAILS;
import static com.kiroule.jpetstore.vaadinspring.ui.util.CurrentCart.Key.SHIPPING_DETAILS;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = ShippingDetailsView.VIEW_NAME)
@ViewConfig(displayName = "Shipping Details", authRequired = true)
public class ShippingDetailsView extends AbstractView {

  private static final long serialVersionUID = -3510510663472621690L;

  public static final String VIEW_NAME = "shipping-details";

  @Autowired
  private ShippingDetailsForm shippingDetailsForm;

  @PostConstruct
  void init() {

    shippingDetailsForm.setSavedHandler(shippingDetails -> {

      if (!shippingDetailsForm.validate()) {
        return;
      }
      CurrentCart.set(SHIPPING_DETAILS, shippingDetails);
      UIEventBus.post(new UINavigationEvent(ConfirmOrderView.VIEW_NAME));
    });
    shippingDetailsForm.setResetHandler(shippingDetails -> shippingDetailsForm.clear());

    Panel contentPanel = new Panel(shippingDetailsForm);
    addComponents(initTitleLabel(), contentPanel, shippingDetailsForm.getToolbar());
    setSizeFull();
    expand(contentPanel);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    if (CurrentCart.isEmpty() || CurrentCart.get(BILLING_DETAILS) == null) {
      UIEventBus.post(new UINavigationEvent(CartView.VIEW_NAME));
      return;
    }
    shippingDetailsForm.setEntity(new ShippingDetails());
  }
}