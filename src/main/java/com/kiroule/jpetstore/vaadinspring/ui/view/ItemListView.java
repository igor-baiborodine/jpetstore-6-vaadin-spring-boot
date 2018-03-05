package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductItemListGrid;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = ItemListView.VIEW_NAME)
@ViewConfig(displayName = "Item")
public class ItemListView extends AbstractView {

  private static final long serialVersionUID = 3940999802009342145L;

  public static final String VIEW_NAME = "item-list";

  private final CatalogService catalogService;
  private final ProductItemListGrid itemList;
  private Product product;

  @Autowired
  public ItemListView(CatalogService catalogService, ProductItemListGrid itemList) {
    this.catalogService = catalogService;
    this.itemList = itemList;
  }

  @PostConstruct
  public void init() {
    addComponents(initTitleLabel(), itemList);
    setSizeFull();
    expand(itemList);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    product = catalogService.getProduct(event.getParameters());
    itemList.setItems(catalogService.getItemListByProduct(product.getProductId()));
  }

  @Override
  public String getTitleLabelValue() {
    return format("%s | %s", product.getProductId(), product.getName());
  }
}