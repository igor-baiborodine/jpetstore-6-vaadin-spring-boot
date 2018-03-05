package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.renderers.NumberRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.grid.MGrid;

import java.text.NumberFormat;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ProductItemListGrid extends MGrid<Item> implements HasUIEventBus {

  private static final long serialVersionUID = -2847546238729364925L;

  private final ProductItemForm productItemForm;

  @Autowired
  public ProductItemListGrid(ProductItemForm productItemForm) {
    this.productItemForm = productItemForm;

    withFullWidth();

    addComponentColumn(item -> {
      Button itemIdButton = new GridButton(item.getItemId(), this::viewDetails);
      itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
      itemIdButton.setData(item);
      return itemIdButton;
    })
        .setId("itemId")
        .setCaption("Item ID");

    addColumn(Item::getDescription)
        .setId("description")
        .setCaption("Description");

    addComponentColumn(item -> new GridButton("Add to Cart",
        event -> getUIEventBus().publish(this, new UIAddItemToCartEvent(item)))
    )
        .setId("addToCart")
        .setCaption("")
        .setSortable(false);

    addColumn(Item::getListPrice, new NumberRenderer(NumberFormat.getCurrencyInstance()))
        .setId("listPrice")
        .setCaption("List Price");
  }

  private void viewDetails(Button.ClickEvent event) {
    Item item = (Item) event.getButton().getData();
    productItemForm.setEntity(item);
    productItemForm.openInModalPopup().setCaption(getPopupCaption(item));
  }

  private String getPopupCaption(Item item) {
    return format("%s | %s", item.getProductId(), item.getProduct().getName());
  }
}