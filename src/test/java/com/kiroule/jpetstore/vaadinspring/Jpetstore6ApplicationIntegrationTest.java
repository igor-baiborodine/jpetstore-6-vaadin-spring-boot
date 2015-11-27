package com.kiroule.jpetstore.vaadinspring;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.service.LoginService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JPetStore6Application.class)
@WebAppConfiguration
public class Jpetstore6ApplicationIntegrationTest {

  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private CatalogService catalogService;
  @Autowired
  private LoginService loginService;

  @Test
  public void loadContext_shouldLoadApplicaitonContext() {

    assertThat(loginService, notNullValue());
    assertThat(productMapper, notNullValue());
    assertThat(catalogService, notNullValue());
    assertThat(getField(catalogService, "productMapper"), notNullValue());
  }
}
