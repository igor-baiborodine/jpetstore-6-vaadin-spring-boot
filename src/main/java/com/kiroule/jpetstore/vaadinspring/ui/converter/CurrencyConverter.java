package com.kiroule.jpetstore.vaadinspring.ui.converter;

import com.vaadin.v7.data.util.converter.Converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class CurrencyConverter implements Converter<String, BigDecimal> {

  NumberFormat formatter = NumberFormat.getCurrencyInstance();

  @Override
  public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {
    return null;
  }

  @Override
  public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws ConversionException {
    return formatter.format(value);
  }

  @Override
  public Class<BigDecimal> getModelType() {
    return BigDecimal.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}
