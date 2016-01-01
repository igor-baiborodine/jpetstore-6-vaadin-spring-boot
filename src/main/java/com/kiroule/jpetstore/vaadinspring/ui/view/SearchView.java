package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductListTable;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
  private CatalogService catalogService;
  @Autowired
  private ProductListTable productListTable;

  private Label noResultLabel;
  private String keyword;
  private String noResultMessage = "Your search \"%s\" did not match any products.";

  @PostConstruct
  public void init() {
    addComponents(initTitleLabel(), initNoResultLabel(), productListTable);
    setSizeFull();
  }

  @Override
  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {

    keyword = event.getParameters();
    List<Product> products = catalogService.searchProductList(keyword);
    noResultLabel.setValue(format(noResultMessage, keyword));
    noResultLabel.setVisible(products.isEmpty());

    productListTable.setBeans(products);
    productListTable.setVisible(!products.isEmpty());
    expand(products.isEmpty() ? noResultLabel : productListTable);
  }

  @Override
  public String getTitleLabelValue() {
    return format("%s | %s", ViewConfigUtil.getDisplayName(this.getClass()), keyword);
  }

  private Label initNoResultLabel() {
    noResultLabel = new Label(noResultMessage);
    noResultLabel.setStyleName(JPetStoreTheme.VIEW_LABEL_MEDIUM);
    return noResultLabel;
  }
}