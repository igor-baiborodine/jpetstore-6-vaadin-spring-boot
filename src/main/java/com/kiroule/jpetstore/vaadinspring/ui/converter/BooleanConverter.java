package com.kiroule.jpetstore.vaadinspring.ui.converter;

import com.vaadin.data.util.converter.Converter;

import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class BooleanConverter implements Converter<String, Boolean> {

  @Override
  public Boolean convertToModel(String s, Class<? extends Boolean> aClass, Locale locale) throws ConversionException {
    return null;
  }

  @Override
  public String convertToPresentation(Boolean aBoolean, Class<? extends String> aClass, Locale locale) throws ConversionException {
    return aBoolean ? "yes" : "no";
  }

  @Override
  public Class<Boolean> getModelType() {
    return Boolean.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}
