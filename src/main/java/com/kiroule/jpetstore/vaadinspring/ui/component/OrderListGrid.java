package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Order;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

import org.vaadin.viritin.grid.MGrid;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class OrderListGrid extends MGrid<Order> {

  private static final long serialVersionUID = -2208557159696611423L;

  public OrderListGrid() {
    withFullWidth();

    addColumn(Order::getOrderId)
        .setCaption("Order ID")
        .setSortable(true);

    addColumn(order -> new SimpleDateFormat("dd-MMM-yyyy").format(order.getOrderDate()))
        .setCaption("Order Placed")
        .setSortable(true);

    addColumn(order -> NumberFormat.getCurrencyInstance().format(order.getTotalPrice()))
        .setCaption("Total")
        .setSortable(false);

    addColumn(Order::getShipToFullName)
        .setId("shipTo")
        .setCaption("Ship to")
        .setSortable(false);
  }
}