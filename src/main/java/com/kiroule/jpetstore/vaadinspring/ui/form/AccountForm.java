package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Maps;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.domain.Banner;
import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class AccountForm extends AbstractForm<Account> {

  private static final long serialVersionUID = 3039694090615398389L;

  private final CatalogService catalogService;

  // Sign on
  private MTextField username = new MTextField("Username");
  private PasswordField password = new PasswordField("Password");
  private PasswordField passwordConfirmation = new PasswordField("Password Confirmation");

  // Profile
  private ComboBox languagePreference = createLanguagePreferenceComboBox();
  private ComboBox favouriteCategoryId = createFavoriteCategoryComboBox();
  private CheckBox listOption = new CheckBox("Wish List");
  private CheckBox bannerOption = new CheckBox("Show Banner");
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
  private Map<String, String> categoryIdToNameMap = Maps.newHashMap();
  private List<Category> categories = newArrayList();

  public enum Mode {
    ADD, EDIT
  }

  @Autowired
  public AccountForm(CatalogService catalogService) {
    super(Account.class);
    this.catalogService = catalogService;
  }

  @PostConstruct
  public void init() {
    setHeightUndefined();

    setStyleName(JPetStoreTheme.BASE_FORM);
    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    bannerImage.setCaption("Banner Image");
    bannerImage.setContentMode(ContentMode.HTML);
    bannerImage.setWidth(300, Unit.PIXELS);
    bannerImage.setStyleName(JPetStoreTheme.BANNER);

    categoryIdToBannerNameMap = catalogService.getBannerList().stream()
        .collect(Collectors.toMap(Banner::getFavouriteCategoryId, Banner::getBannerName));
    categories = catalogService.getCategoryList();
    categories.sort(Comparator.comparing(Category::getName));
    categoryIdToNameMap = categories.stream()
        .collect(Collectors.toMap(Category::getCategoryId, Category::getName));

    initBindings();
    setListeners();

    favouriteCategoryId.setItems(categoryIdToNameMap.keySet());
    favouriteCategoryId.setItemCaptionGenerator(categoryIdToNameMap::get);
  }

  public void setReadOnlyFields(Mode mode) {
    if (mode == Mode.EDIT) {
      username.setReadOnly(true);
    }
  }

  public HorizontalLayout getToolbar(Mode mode) {

    getSaveButton().setVisible(true);
    getResetButton().setVisible(true);

    if (Mode.ADD.equals(mode)) {
      getResetButton().setCaption("Clear");
    }
    if (Mode.EDIT.equals(mode)) {
      getResetButton().setCaption("View Your Orders");
    }
    return getToolbar();
  }

  public void clear() {
    setEntity(new Account());
    passwordConfirmation.clear();
  }

  @Override
  protected Component createContent() {

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
        bannerImage.setStyleName(getBannerStyleName(bannerName));
        getEntity().setBannerName(bannerName);
      }
    });
  }

  private ComboBox<Category> createFavoriteCategoryComboBox() {
    return new ComboBox<>("Favourite Category");
  }

  private ComboBox<String> createLanguagePreferenceComboBox() {
    ComboBox<String> comboBox = new ComboBox<>("Language Preference");
    comboBox.setEmptySelectionAllowed(false);
    comboBox.setItems("English", "Komi", "Russian");
    return comboBox;
  }

  private void initBindings() {
    // explicit binding for combo boxes and password field
    getBinder()
        .forField(languagePreference)
        .bind("languagePreference");

    getBinder()
        .forField(favouriteCategoryId)
        .bind("favouriteCategoryId");

    getBinder()
        .forField(password)
        .bind("password");

    getBinder()
        .forField(passwordConfirmation)
        .withValidator(value -> value.equals(password.getValue()), "Confirmation Password is not identical")
        .bind("password");
  }

  private String getBannerStyleName(String bannerName) {
    String bannerTheme = JPetStoreTheme.BANNER;
    if (bannerName.contains("reptiles")) {
      bannerTheme = JPetStoreTheme.BANNER_2;
    }
    return bannerTheme;
  }
}