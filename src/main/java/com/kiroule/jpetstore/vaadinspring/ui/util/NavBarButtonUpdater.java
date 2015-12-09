package com.kiroule.jpetstore.vaadinspring.ui.util;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme.SELECTED;

import com.google.common.collect.Maps;

import com.kiroule.jpetstore.vaadinspring.ui.view.SearchView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@UIScope
public class NavBarButtonUpdater implements Serializable, ViewChangeListener {

  private static final Logger logger = LoggerFactory.getLogger(NavBarButtonUpdater.class);

  private Map<String, Button> uriToButtonMap = Maps.newHashMap();

  public void mapButtonToUri(String uri, Button button) {
    uriToButtonMap.put(uri, button);
  }

  public void setButtonSelected(String mappedUri) {

    if (!uriToButtonMap.keySet().contains(mappedUri)) {
      return;
    }
    uriToButtonMap.forEach((uri, button) -> {
      if (button.getStyleName().contains(SELECTED)) {
        button.removeStyleName(SELECTED);
        logger.info("Removed [{}] style from button with uri[{}]", SELECTED, uri);
      }
      if (uri.equals(mappedUri)) {
        button.addStyleName(SELECTED);
        logger.info("Added [{}] style to button with uri[{}]", SELECTED, uri);
      }
    });
  }

  public void setButtonVisible(String mappedUri, boolean visible) {

    uriToButtonMap.forEach((uri, button) -> {
      if (uri.equals(mappedUri)) {
        button.setVisible(visible);
        logger.info("Set visible[{}] button with uri[{}]", visible, mappedUri);
      }
    });
  }

  @Override
  public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
    return true; // false blocks navigation, always return true here
  }

  @Override
  public void afterViewChange(ViewChangeEvent event) {

    String uri = event.getViewName();
    if (!uri.contains(SearchView.VIEW_NAME) && !isNullOrEmpty(event.getParameters())) {
      uri += "/" + event.getParameters();
    }
    setButtonSelected(uri);
  }
}