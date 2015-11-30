package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Maps;

import com.eijsink.vaadin.components.formcheckbox.FormCheckBox;
import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.domain.Banner;
import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.service.AccountService;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.view.HomeView;
import com.vaadin.data.Validator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

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
import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class AccountForm extends AbstractForm<Account> {

  @Autowired
  private AccountService accountService;
  @Autowired
  private CatalogService catalogService;

  // Sign on
  private TextField username = new MTextField("Username");
  private PasswordField password = new PasswordField("Password");
  private PasswordField passwordConfirmation = new PasswordField("Password Confirmation");

  // Profile
  private ComboBox languagePreference = new ComboBox("Language Preference");
  private ComboBox favouriteCategoryId = new ComboBox("Favourite Category");
  private FormCheckBox listOption = new FormCheckBox("Wish List");
  private FormCheckBox bannerOption = new FormCheckBox("Show Banner");
  private Label bannerImage = new Label();
  private String bannerName;

  // User
  private TextField firstName = new MTextField("First Name");
  private TextField lastName = new MTextField("LastName");
  private TextField email = new MTextField("Email");
  private TextField phone = new MTextField("Phone");
  private TextField address1 = new MTextField("Address 1");
  private TextField address2 = new MTextField("Address 2");
  private TextField city = new MTextField("City");
  private TextField state = new MTextField("State");
  private TextField zip = new MTextField("ZIP Code");
  private TextField country = new MTextField("Country");

  Map<String, String> categoryIdToBannerNameMap = Maps.newHashMap();

  @PostConstruct
  public void init() {

    categoryIdToBannerNameMap = catalogService.getBannerList().stream()
        .collect(Collectors.toMap(Banner::getFavouriteCategoryId, Banner::getBannerName));

    setSavedHandler(account -> {
      account.setStatus("OK");
      account.setBannerName(bannerName);

      try {
        accountService.insertAccount(account);
        navigateToHomeView();
      } catch (Throwable t) {
        Notification.show("An error occurred while adding new account: " + t.getMessage(), ERROR_MESSAGE);
      }
    });

    setResetHandler(account -> navigateToHomeView());
    setSizeUndefined();
  }

  @Override
  protected Component createContent() {

    password.setNullRepresentation("");
    bannerImage.setCaption("Banner Image");
    bannerImage.setContentMode(ContentMode.HTML);

    bannerImage.setStyleName(JPetStoreTheme.BANNER);
    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setLanguagePreferenceCombo();
    setFavouriteCategoryCombo();
    setRequiredFields();
    setToolBarVisible();
    setListeners();
    setValidators();

    MFormLayout signonFormLayout = new MFormLayout(
        username,
        password,
        passwordConfirmation
    ).withWidth("");
    signonFormLayout.setStyleName(JPetStoreTheme.ACCOUNT_FORM);

    MFormLayout profileFormLayout = new MFormLayout(
        languagePreference,
        favouriteCategoryId,
        listOption,
        bannerOption,
        bannerImage
    ).withWidth("");
    profileFormLayout.setStyleName(JPetStoreTheme.ACCOUNT_FORM);

    MFormLayout userFormLayout = new MFormLayout(
        firstName,
        lastName,
        email,
        phone,
        address1,
        address2,
        city,
        state,
        zip,
        country
    ).withWidth("");
    userFormLayout.setStyleName(JPetStoreTheme.ACCOUNT_FORM);

    MVerticalLayout content = new MVerticalLayout(
        getSectionTitle("Sign on"),
        signonFormLayout,
        getSectionTitle("Profile"),
        profileFormLayout,
        getSectionTitle("User"),
        userFormLayout)
          .withSpacing(false)
          .withWidth("");

    return content;
  }

  private void setListeners() {

    favouriteCategoryId.addValueChangeListener(event -> {
      if (favouriteCategoryId.getValue() != null) {
        bannerName = categoryIdToBannerNameMap.get(favouriteCategoryId.getValue());
        if (bannerName == null) {
          return;
        }
        bannerImage.setValue(bannerName);
      }
    });
  }

  private void setValidators() {

    addValidator(account -> {
      if (isNullOrEmpty(account.getPassword()) || !account.getPassword().equals(passwordConfirmation.getValue())) {
        throw new Validator.InvalidValueException("Confirmation Password is not identical");
      }
    }, passwordConfirmation);
  }

  private void setLanguagePreferenceCombo() {

    languagePreference.addItem("english");
    languagePreference.setItemCaption("english", "English");
    languagePreference.addItem("russian");
    languagePreference.setItemCaption("russian", "Russian");
    languagePreference.setNullSelectionAllowed(false);
  }

  private void setFavouriteCategoryCombo() {

    List<Category> categories = catalogService.getCategoryList();
    categories.sort(Comparator.comparing(Category::getName));
    categories.forEach(category -> {
      favouriteCategoryId.addItem(category.getCategoryId());
      favouriteCategoryId.setItemCaption(category.getCategoryId(), category.getName());
    });
    favouriteCategoryId.setNullSelectionAllowed(false);
  }

  private void setRequiredFields() {

    username.setRequired(true);
    password.setRequired(true);
    passwordConfirmation.setRequired(true);
    languagePreference.setRequired(true);
    firstName.setRequired(true);
    lastName.setRequired(true);
    email.setRequired(true);
    phone.setRequired(true);
    address1.setRequired(true);
    city.setRequired(true);
    state.setRequired(true);
    zip.setRequired(true);
    country.setRequired(true);
  }

  private void setToolBarVisible() {
    getSaveButton().setVisible(true);
    getResetButton().setVisible(true);
  }

  private Label getSectionTitle(String content) {
    Label title = new Label(content);
    title.addStyleName(JPetStoreTheme.LABEL_H3);
    title.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return title;
  }

  private void navigateToHomeView() {
    UIEventBus.post(new UINavigationEvent(HomeView.VIEW_NAME));
  }
}