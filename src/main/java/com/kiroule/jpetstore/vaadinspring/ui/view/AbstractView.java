package com.kiroule.jpetstore.vaadinspring.ui.view;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.CurrentAccount;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

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
    Component banner = createBanner();
    banner.setVisible(false);
    super.addComponent(banner);
  }

  public String getTitleLabelValue() {
    return ViewConfigUtil.getDisplayName(this.getClass());
  }

  protected Label initTitleLabel() {

    titleLabel = new Label("Abstract Title");
    titleLabel.addStyleName(JPetStoreTheme.LABEL_H2);
    titleLabel.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return titleLabel;
  }

  protected void setBannerVisible() {

    Account account = CurrentAccount.get();
    if (account != null && account.isBannerOption() && !isNullOrEmpty(account.getBannerName())) {
      bannerImage.setValue(account.getBannerName());
      bannerLayout.setVisible(true);
    }
  }

  private void setTitleLabelValue(String value) {
    if (titleLabel != null) {
      titleLabel.setValue(value);
    }
  }

  private Component createBanner() {

    bannerImage = new Label();
    bannerImage.setContentMode(ContentMode.HTML);
    bannerImage.setWidthUndefined();

    bannerLayout = new MVerticalLayout().withMargin(false);
    bannerLayout.setStyleName(JPetStoreTheme.BANNER);
    bannerLayout.add(bannerImage);
    bannerLayout.setComponentAlignment(bannerImage, Alignment.MIDDLE_CENTER);
    bannerLayout.setVisible(false);
    return bannerLayout;
  }
}