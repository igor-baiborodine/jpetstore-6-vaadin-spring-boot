package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.view.ItemListView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;

import org.vaadin.viritinv7.fields.MTable;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ProductListTable extends MTable<Product> implements HasUIEventBus {

  private static final long serialVersionUID = 2029031508462137840L;

  public ProductListTable() {

    withProperties("productId", "name");
    withColumnHeaders("Product ID", "Name");
    setSortableProperties("productId", "name");
    withGeneratedColumn("productId", entity -> {
      String uri = ItemListView.VIEW_NAME + "/" + entity.getProductId();
      Button inventoryButton = new Button(entity.getProductId(),
          event -> getUIEventBus().post(new UINavigationEvent(uri)));
      inventoryButton.setData(entity.getProductId());
      inventoryButton.addStyleName("link");
      return inventoryButton;
    });
    withFullWidth();
  }
}
