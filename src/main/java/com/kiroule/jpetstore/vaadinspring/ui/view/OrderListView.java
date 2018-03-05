package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Order;
import com.kiroule.jpetstore.vaadinspring.service.OrderService;
import com.kiroule.jpetstore.vaadinspring.ui.component.OrderListGrid;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = OrderListView.VIEW_NAME)
@ViewConfig(displayName = "Your Orders", authRequired = true)
public class OrderListView extends AbstractView {

  private static final long serialVersionUID = -1297358094619700705L;

  public static final String VIEW_NAME = "order-list";

  private final OrderService orderService;
  private final OrderListGrid orderList;

  private Label noOrdersLabel;

  @Autowired
  public OrderListView(OrderService orderService, OrderListGrid orderList) {
    this.orderService = orderService;
    this.orderList = orderList;
  }

  @PostConstruct
  void init() {
    addComponents(initTitleLabel(), initNoOrdersLabel(), orderList);
    setSizeFull();
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    List<Order> orders = orderService.getOrdersByUsername(CurrentAccount.get().getUsername());
    orderList.setItems(orders);
    orderList.setVisible(!orders.isEmpty());
    noOrdersLabel.setVisible(orders.isEmpty());
    expand(orders.isEmpty() ? noOrdersLabel : orderList);
  }

  private Label initNoOrdersLabel() {
    noOrdersLabel = new Label("You have not placed any orders.");
    noOrdersLabel.setStyleName(JPetStoreTheme.VIEW_LABEL_MEDIUM);
    return noOrdersLabel;
  }
}