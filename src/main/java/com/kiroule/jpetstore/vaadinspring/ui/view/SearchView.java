package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductListTable;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = SearchView.VIEW_NAME)
@ViewConfig(displayName = "Search")
public class SearchView extends AbstractView {

  private static final long serialVersionUID = -431958453976123288L;

  public static final String VIEW_NAME = "search";

  @Autowired
  private ProductMapper productRepository;
  @Autowired
  private ProductListTable productListTable;

  private String keyword;

  @PostConstruct
  public void init() {

    addComponents(initTitleLabel(), productListTable);
    setSizeFull();
    expand(productListTable);
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    keyword = event.getParameters();
    productListTable.setBeans(productRepository.searchProductList(keyword));
  }

  @Override
  public String getTitleLabelValue() {
    return format("%s | %s", ViewConfigUtil.getDisplayName(this.getClass()), keyword);
  }
}