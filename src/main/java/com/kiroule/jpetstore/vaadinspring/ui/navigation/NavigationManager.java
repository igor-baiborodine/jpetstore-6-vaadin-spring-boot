package com.kiroule.jpetstore.vaadinspring.ui.navigation;

import com.kiroule.jpetstore.vaadinspring.ui.view.HomeView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.internal.Conventions;
import com.vaadin.spring.navigator.SpringNavigator;

import org.springframework.stereotype.Component;

@Component
@UIScope
public class NavigationManager extends SpringNavigator {

  public String getViewId(Class<? extends View> viewClass) {
    SpringView springView = viewClass.getAnnotation(SpringView.class);
    if (springView == null) {
      throw new IllegalArgumentException("The target class must be a @SpringView");
    }
    return Conventions.deriveMappingForView(viewClass, springView);
  }

  public void navigateTo(Class<? extends View> targetView) {
    String viewId = getViewId(targetView);
    navigateTo(viewId);
  }

  public void navigateToDefaultView() {
    navigateTo(HomeView.class);
  }
}
