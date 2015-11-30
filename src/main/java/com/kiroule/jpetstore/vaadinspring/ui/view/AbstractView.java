package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Account;
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

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractView extends MVerticalLayout implements View {

  protected Label title;
  protected MVerticalLayout bannerLayout;

  protected Label getTitle() {
    title = new Label("Abstract Title");
    title.addStyleName(JPetStoreTheme.LABEL_H2);
    title.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return title;
  }

  @Override
  public void addComponents(Component... components) {
    addComponents(true, components);
  }

  public void addComponents(boolean addBanner, Component... components) {

    super.addComponents(components);
    if (!addBanner) {
      return;
    }
    Component banner = getBanner();
    if (banner != null) {
      super.addComponent(banner);
    }
  }

  private Component getBanner() {

    Account account = CurrentAccount.get();
    if (account != null && account.isBannerOption() && !isNullOrEmpty(account.getBannerName())) {
      bannerLayout = new MVerticalLayout().withMargin(false);
      bannerLayout.setStyleName(JPetStoreTheme.BANNER);
      Label bannerImage = new Label(account.getBannerName(), ContentMode.HTML);
      bannerImage.setWidthUndefined();
      bannerLayout.add(bannerImage);
      bannerLayout.setComponentAlignment(bannerImage, Alignment.MIDDLE_CENTER);
    }
    return bannerLayout;
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    if (title != null) {
      title.setValue(ViewConfigUtil.getDisplayName(this.getClass()));
    }
  }
}