package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.persistence.CategoryMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductListTable;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = ProductListView.VIEW_NAME)
@ViewConfig(displayName = "Product")
public class ProductListView extends AbstractView {

  private static final long serialVersionUID = -5060620619866232275L;

  public static final String VIEW_NAME = "product-list";

  @Autowired
  private ProductMapper productRepository;
  @Autowired
  private CategoryMapper categoryRepository;
  @Autowired
  private CatalogService catalogService;
  @Autowired
  private ProductListTable productList;

  private Category category;

  @PostConstruct
  public void init() {

    addComponents(initTitleLabel(), productList);
    setSizeFull();
    expand(productList);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    category = catalogService.getCategory(event.getParameters());
    productList.setBeans(catalogService.getProductListByCategory(category.getCategoryId()));
  }

  @Override
  public String getTitleLabelValue() {
    return category.getName();
  }
}