package com.kiroule.jpetstore.vaadinspring.ui.converter;

import com.vaadin.data.util.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class DateConverter implements Converter<String, Date> {

  private static final long serialVersionUID = 6226046049923436569L;

  @Override
  public Date convertToModel(String value, Class<? extends Date> targetType, Locale locale) throws ConversionException {
    return null;
  }

  @Override
  public String convertToPresentation(Date value, Class<? extends String> targetType, Locale locale)
      throws ConversionException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    if (value != null) {
      return sdf.format(value);
    }
    return null;
  }

  @Override
  public Class<Date> getModelType() {
    return Date.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}