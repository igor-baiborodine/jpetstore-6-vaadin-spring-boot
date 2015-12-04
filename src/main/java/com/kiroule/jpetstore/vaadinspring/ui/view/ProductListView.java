package com.kiroule.jpetstore.vaadinspring.ui.view;

import static java.lang.String.format;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.persistence.CategoryMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
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

  public static final String VIEW_NAME = "product-list";

  @Autowired
  private ProductMapper productRepository;

  @Autowired
  private CategoryMapper categoryRepository;

  @Autowired
  private ProductListTable productListTable;

  private Category category;

  @PostConstruct
  public void init() {

    addComponents(createTitleLabel(), productListTable);
    setSizeFull();
    expand(productListTable);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

    category = categoryRepository.getCategory(event.getParameters());
    productListTable.setBeans(productRepository.getProductListByCategory(category.getCategoryId()));
    super.enter(event);
  }

  @Override
  public String getTitleLabelValue() {
    return format(category.getName());
  }
}