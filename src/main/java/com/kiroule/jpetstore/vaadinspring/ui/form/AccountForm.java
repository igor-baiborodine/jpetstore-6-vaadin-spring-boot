package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.eijsink.vaadin.components.formcheckbox.FormCheckBox;
import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.domain.Banner;
import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.data.Validator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.vaadin.data.Validator.InvalidValueException;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class AccountForm extends AbstractForm<Account> {

  private static final long serialVersionUID = 3039694090615398389L;

  @Autowired
  private CatalogService catalogService;

  // Sign on
  private MTextField username = new MTextField("Username");
  private PasswordField password = new PasswordField("Password");
  private PasswordField passwordConfirmation = new PasswordField("Password Confirmation");

  // Profile
  private ComboBox languagePreference = new ComboBox("Language Preference");
  private ComboBox favouriteCategoryId = new ComboBox("Favourite Category");
  private FormCheckBox listOption = new FormCheckBox("Wish List");
  private FormCheckBox bannerOption = new FormCheckBox("Show Banner");
  private Label bannerImage = new Label();

  // User
  private MTextField firstName = new MTextField("First Name");
  private MTextField lastName = new MTextField("LastName");
  private MTextField email = new MTextField("Email");
  private MTextField phone = new MTextField("Phone");
  private MTextField address1 = new MTextField("Address 1");
  private MTextField address2 = new MTextField("Address 2");
  private MTextField city = new MTextField("City");
  private MTextField state = new MTextField("State");
  private MTextField zip = new MTextField("ZIP Code");
  private MTextField country = new MTextField("Country");

  private Map<String, String> categoryIdToBannerNameMap = Maps.newHashMap();
  private List<Category> categories = newArrayList();

  @PostConstruct
  public void init() {

    categoryIdToBannerNameMap = catalogService.getBannerList().stream()
        .collect(Collectors.toMap(Banner::getFavouriteCategoryId, Banner::getBannerName));
    categories = catalogService.getCategoryList();
    categories.sort(Comparator.comparing(Category::getName));

    setStyleName(JPetStoreTheme.BASE_FORM);
    setEagerValidation(false);
    setHeightUndefined();
  }

  public void setReadOnlyFields(Mode mode) {
    if (mode == Mode.EDIT) {
      username.setReadOnly(true);
    }
  }

  public HorizontalLayout getToolbar(Mode mode) {

    getSaveButton().setVisible(true);
    getResetButton().setVisible(true);

    if (Mode.INSERT.equals(mode)) {
      getResetButton().setCaption("Clear");
    }
    if (Mode.EDIT.equals(mode)) {
      getResetButton().setCaption("View My Orders");
    }
    return getToolbar();
  }

  public void clear() {
    setEntity(new Account());
    passwordConfirmation.clear();
  }

  public enum Mode {
    INSERT, EDIT
  }

  public boolean validate() {

    try {
      getFieldGroup().getFields().forEach(field -> {
        field.focus();
        field.validate();
      });
      validatePasswordConfirmation();
    } catch (InvalidValueException e) {
      Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  @Override
  protected Component createContent() {

    password.setNullRepresentation("");
    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    listOption.setImmediate(true);
    bannerOption.setImmediate(true);

    bannerImage.setCaption("Banner Image");
    bannerImage.setContentMode(ContentMode.HTML);
    bannerImage.setWidth(300, Unit.PIXELS);
    bannerImage.setStyleName(JPetStoreTheme.BANNER);

    setLanguagePreferenceCombo();
    setFavouriteCategoryCombo();
    setRequiredFields(username, password, languagePreference, firstName, lastName, email, phone,
        address1, city, state, zip, country);
    setListeners();

    MFormLayout signonFormLayout = new MFormLayout(username, password, passwordConfirmation).withWidth("-1px");
    MFormLayout accountFormLayout = new MFormLayout(firstName, lastName, email, phone, address1, address2, city,
        state, zip, country).withWidth("-1px");
    MFormLayout profileFormLayout = new MFormLayout(languagePreference, favouriteCategoryId, listOption,
        bannerOption, bannerImage).withWidth("-1px");

    return new MVerticalLayout(
        new Panel("Sign on", signonFormLayout), 
        new Panel("User", accountFormLayout),
        new Panel("Profile", profileFormLayout)
    );
  }

  private void setListeners() {

    favouriteCategoryId.addValueChangeListener(event -> {
      if (favouriteCategoryId.getValue() != null) {
        String bannerName = categoryIdToBannerNameMap.get(favouriteCategoryId.getValue());
        if (bannerName == null) {
          return;
        }
        bannerImage.setValue(bannerName);
        getEntity().setBannerName(bannerName);
      }
    });
  }

  private void setLanguagePreferenceCombo() {

    languagePreference.addItem("english");
    languagePreference.setItemCaption("english", "English");
    languagePreference.addItem("russian");
    languagePreference.setItemCaption("russian", "Russian");
    languagePreference.setNullSelectionAllowed(false);
  }

  private void setFavouriteCategoryCombo() {

    categories.forEach(category -> {
      favouriteCategoryId.addItem(category.getCategoryId());
      favouriteCategoryId.setItemCaption(category.getCategoryId(), category.getName());
    });
    favouriteCategoryId.setNullSelectionAllowed(false);
  }

  private void setRequiredFields(Field<?>... fields) {

    Lists.newArrayList(fields).forEach(field -> {
      field.setRequired(true);
      field.setRequiredError(field.getCaption() + " is required");
    });
    passwordConfirmation.setRequired(true);
  }

  private void validatePasswordConfirmation() {

    passwordConfirmation.focus();
    if (isNullOrEmpty(passwordConfirmation.getValue())) {
      throw new Validator.InvalidValueException("Password Confirmation is required");
    }
    if (!passwordConfirmation.getValue().equals(getEntity().getPassword())) {
      throw new Validator.InvalidValueException("Confirmation Password is not identical");
    }
  }
}