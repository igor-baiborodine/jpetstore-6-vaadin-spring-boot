package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = AuthRequiredView.VIEW_NAME)
@ViewConfig(displayName = "Authentication Required")
public class AuthRequiredView extends AbstractView {

  private static final long serialVersionUID = 173042647576386001L;

  public static final String VIEW_NAME = "auth-required";

  private Label messageLabel;

  @PostConstruct
  void init() {

    addComponents(initTitleLabel(), initMessageLabel());
    setSizeFull();
    expand(messageLabel);
    setSizeFull();
  }

  private Label initMessageLabel() {

    messageLabel = new Label("Please sign in.");
    messageLabel.setStyleName(JPetStoreTheme.MEDIUM_LABEL);
    return messageLabel;
  }
}