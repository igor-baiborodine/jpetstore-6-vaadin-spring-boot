package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.form.ItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
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

    super();
    this.withProperties("itemId", "product", "listPrice", "attribute5")
        .withColumnHeaders("Item ID", "Description", "List Price", "")
        .setSortableProperties("itemId", "product", "listPrice")
        .withGeneratedColumn("itemId", item -> {
          Button itemIdButton = new Button(item.getItemId(), this::viewDetails);
          itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
          itemIdButton.setData(item);
          return itemIdButton;
        })
        .withGeneratedColumn("product", item -> item.getAttribute1() + " " + item.getProduct().getName())
        .withGeneratedColumn("attribute5", item -> {
          Button addToCartButton = new Button("Add to Cart",
              event -> {
                UIEventBus.post(new UIAddItemToCartEvent(item));
              });
          return addToCartButton;
        })
        .withFullWidth();
  }

  private void viewDetails(Button.ClickEvent event) {
    ItemForm itemForm = new ItemForm((Item) event.getButton().getData());
    Window popup = itemForm.openInModalPopup();
    popup.setCaption("View Details");
  }
}