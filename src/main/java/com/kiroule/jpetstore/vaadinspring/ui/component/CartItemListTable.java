package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.CartItem;
import com.kiroule.jpetstore.vaadinspring.domain.Item;
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
public class CartItemListTable extends MTable<CartItem> {

  private static final long serialVersionUID = 6841591708524361792L;

  public CartItemListTable() {

    // TODO: add other columns
    super();
    this.withProperties("itemId")
        .withColumnHeaders("Item ID")
        .withGeneratedColumn("itemId", cartItem -> {
          Button itemIdButton = new Button(cartItem.getItem().getItemId(), this::viewDetails);
          itemIdButton.setData(cartItem.getItem());
          itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
          return itemIdButton;
        })
        .withFullWidth();
  }

  private void viewDetails(Button.ClickEvent event) {
    ItemForm itemForm = new ItemForm((Item) event.getButton().getData());
    Window popup = itemForm.openInModalPopup();
    popup.setCaption("View Details");
  }
}
