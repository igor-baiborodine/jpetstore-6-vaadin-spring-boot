package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

import org.vaadin.viritin.fields.MTable;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class CartItemListTable extends MTable<CartItem> {

  public CartItemListTable() {

    // TODO: implement me
    super();
    withProperties("productId", "name")
        .withColumnHeaders("Product ID", "Name")
        .setSortableProperties("productId", "name")
//        .withGeneratedColumn("productId", entity -> {
//          String uri = ItemListView.VIEW_NAME + "/" + entity.getProductId();
//          Button inventoryButton = new Button(entity.getProductId(),
//              event -> UIEventBus.post(new UINavigationEvent(uri)));
//          inventoryButton.setData(entity.getProductId());
//          inventoryButton.addStyleName("link");
//          return inventoryButton;
//        })
    .withFullWidth();
  }
}
