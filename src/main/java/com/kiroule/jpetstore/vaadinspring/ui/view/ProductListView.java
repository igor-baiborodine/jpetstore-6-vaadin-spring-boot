package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductListGrid;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = ProductListView.VIEW_NAME)
@ViewConfig(displayName = "Product")
public class ProductListView extends AbstractView {

  private static final long serialVersionUID = -5060620619866232275L;

  public static final String VIEW_NAME = "product-list";

  private final CatalogService catalogService;
  private final ProductListGrid productList;

  private Category category;

  @Autowired
  public ProductListView(CatalogService catalogService, ProductListGrid productList) {
    this.catalogService = catalogService;
    this.productList = productList;
  }

  @PostConstruct
  public void init() {
    addComponents(initTitleLabel(), productList);
    setSizeFull();
    expand(productList);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    category = catalogService.getCategory(event.getParameters());
    List<Product> products = catalogService.getProductListByCategory(category.getCategoryId());
    productList.setItems(products);
  }

  @Override
  public String getTitleLabelValue() {
    return category.getName();
  }
}