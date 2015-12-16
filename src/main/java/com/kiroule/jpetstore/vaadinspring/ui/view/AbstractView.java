package com.kiroule.jpetstore.vaadinspring.ui.view;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractView extends MVerticalLayout implements View {

  private static final long serialVersionUID = 8810691351327628878L;

  protected Label titleLabel;
  protected MVerticalLayout bannerLayout;
  protected Label bannerImage;

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

    if (ViewConfigUtil.isAuthRequired(this.getClass()) && !CurrentAccount.isLoggedIn()) {
      UIEventBus.post(new UINavigationEvent(AuthRequiredView.VIEW_NAME));
      return;
    }
    executeOnEnter(event);
    setTitleLabelValue(getTitleLabelValue());
    setBannerVisible();
  }

  public void executeOnEnter(ViewChangeListener.ViewChangeEvent event) {
    // If needed, override in derived classes
  }

  @Override
  public void addComponents(Component... components) {

    super.addComponents(components);
    Component banner = initBanner();
    banner.setVisible(false);
    super.addComponent(banner);
  }

  public String getTitleLabelValue() {
    return ViewConfigUtil.getDisplayName(this.getClass());
  }

  protected Label initTitleLabel() {
    titleLabel = new Label("[View Title]");
    titleLabel.setStyleName(JPetStoreTheme.VIEW_LABEL_LARGE);
    return titleLabel;
  }

  protected void setBannerVisible() {

    Account account = CurrentAccount.get();
    if (account != null && account.isBannerOption() && !isNullOrEmpty(account.getBannerName())) {
      bannerImage.setValue(account.getBannerName());
      bannerLayout.setVisible(true);
      bannerLayout.setStyleName(getBannerStyleName(account.getBannerName()));
    }
  }

  protected void showConfirmation(String caption) {
    Notification notification = new Notification(caption, HUMANIZED_MESSAGE);
    notification.setDelayMsec(2000);
    notification.show(Page.getCurrent());
  }

  protected void showError(String caption) {
    Notification notification = new Notification(caption, Notification.Type.ERROR_MESSAGE);
    notification.show(Page.getCurrent());
  }

  private void setTitleLabelValue(String value) {
    if (titleLabel != null) {
      titleLabel.setValue(value);
    }
  }

  private Component initBanner() {

    bannerImage = new Label();
    bannerImage.setContentMode(ContentMode.HTML);
    bannerImage.setWidthUndefined();

    bannerLayout = new MVerticalLayout().withMargin(false);
    bannerLayout.add(bannerImage);
    bannerLayout.setComponentAlignment(bannerImage, Alignment.MIDDLE_CENTER);
    bannerLayout.setVisible(false);
    return bannerLayout;
  }

  private String getBannerStyleName(String bannerName) {
    return bannerName.contains("reptiles") ? JPetStoreTheme.BANNER_2 : JPetStoreTheme.BANNER;
  }
}