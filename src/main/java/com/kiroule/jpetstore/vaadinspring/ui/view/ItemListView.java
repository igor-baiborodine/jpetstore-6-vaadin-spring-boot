package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.persistence.ItemMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.component.ItemListTable;
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

  @Autowired
  private ProductMapper productRepository;
  @Autowired
  private ItemMapper itemRepository;
  @Autowired
  private ItemListTable itemList;

  private Product product;

  @PostConstruct
  public void init() {

    addComponents(initTitleLabel(), itemList);
    setSizeFull();
    expand(itemList);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    product = productRepository.getProduct(event.getParameters());
    itemList.setBeans(itemRepository.getItemListByProduct(product.getProductId()));
  }

  @Override
  public String getTitleLabelValue() {
    return format("%s | %s", product.getName(), product.getProductId());
  }
}