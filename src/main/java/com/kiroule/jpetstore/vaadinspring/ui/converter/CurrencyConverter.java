package com.kiroule.jpetstore.vaadinspring.ui.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author Igor Baiborodine
 */
public class CurrencyConverter implements Converter<String, BigDecimal> {

  private static final long serialVersionUID = -904416628121822435L;

  private final NumberFormat formatter = NumberFormat.getCurrencyInstance();

  @Override
  public Result<BigDecimal> convertToModel(String value, ValueContext context) {
    return null;
  }

  @Override
  public String convertToPresentation(BigDecimal value, ValueContext context) {
    return formatter.format(value);
  }
}
