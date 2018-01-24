package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Order;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.converter.DateConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

import org.vaadin.viritinv7.fields.MTable;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class OrderListTable extends MTable<Order> {

  private static final long serialVersionUID = -2208557159696611423L;

  public OrderListTable() {

    addContainerProperty("shipTo", String.class, "Not Defined");

    withProperties("orderId", "orderDate", "totalPrice", "shipTo");
    withColumnHeaders("Order ID", "Order Placed", "Total", "Ship to");
    setSortableProperties("productId", "orderDate");
    withGeneratedColumn("orderId", entity -> String.valueOf(entity.getOrderId()));
    withGeneratedColumn("shipTo", entity -> entity.getShipToFirstName() + " " + entity.getShipToLastName());

    setConverter("orderDate", new DateConverter());
    setConverter("totalPrice", new CurrencyConverter());
    withFullWidth();
  }
}