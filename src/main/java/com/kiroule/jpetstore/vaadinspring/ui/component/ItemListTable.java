package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import org.vaadin.viritin.fields.MTable;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ItemListTable extends MTable<Item> {

  private static final long serialVersionUID = -2847546238729364925L;

  public ItemListTable() {

    addContainerProperty("addToCart", Component.class, null);
    addContainerProperty("description", String.class, "Not defined");

    withProperties("itemId", "description", "listPrice", "addToCart");
    withColumnHeaders("Item ID", "Description", "List Price", "");
    setSortableProperties("itemId", "listPrice");
    withGeneratedColumn("itemId", item -> {
          Button itemIdButton = new Button(item.getItemId(), this::viewDetails);
          itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
          itemIdButton.setData(item);
          return itemIdButton;
    });
    withGeneratedColumn("description", item -> item.getAttribute1() + " " + item.getProduct().getName());
    withGeneratedColumn("addToCart",
            item -> new Button("Add to Cart", event -> UIEventBus.post(new UIAddItemToCartEvent(item))));
    setConverter("listPrice", new CurrencyConverter());
    withFullWidth();
  }

  private void viewDetails(Button.ClickEvent event) {
    ProductItemForm productItemForm = new ProductItemForm((Item) event.getButton().getData());
    Window popup = productItemForm.openInModalPopup();
    popup.setCaption("View Details");
  }
}