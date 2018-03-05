package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.view.ItemListView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;

import org.vaadin.viritin.grid.MGrid;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ProductListGrid extends MGrid<Product> implements HasUIEventBus {

  private static final long serialVersionUID = 2029031508462137840L;

  public ProductListGrid() {
    withFullWidth();

    addComponentColumn(product -> {
      String uri = ItemListView.VIEW_NAME + "/" + product.getProductId();
      Button inventoryButton = new GridButton(product.getProductId(),
          event -> getUIEventBus().publish(this, new UINavigationEvent(uri)));
      inventoryButton.setData(product.getProductId());
      inventoryButton.addStyleName("link");
      return inventoryButton;
    })
        .setId("productId")
        .setCaption("Product ID");

    addColumn(Product::getName)
        .setId("name")
        .setCaption("Name");
  }
}
