package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.ui.Button;

/**
 * @author Igor Baiborodine
 */
public class GridButton extends Button {

  public GridButton() {
  }

  public GridButton(String caption, ClickListener listener) {
    super(caption, listener);
    addStyleName(JPetStoreTheme.GRID_BUTTON);
  }
}
