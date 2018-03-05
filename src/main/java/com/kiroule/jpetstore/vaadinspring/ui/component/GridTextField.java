package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;

import org.vaadin.viritin.fields.MTextField;

/**
 * @author Igor Baiborodine
 */
public class GridTextField extends MTextField {

  public GridTextField() {
    super();
    addStyleName(JPetStoreTheme.GRID_TEXTFIELD);
  }
}
