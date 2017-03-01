package com.kiroule.jpetstore.vaadinspring;

import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.service.LoginService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Jpetstore6ApplicationIntegrationTest {

  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private CatalogService catalogService;
  @Autowired
  private LoginService loginService;

  @Test
  public void loadContext_shouldLoadApplicationContext() {

    assertThat(loginService, notNullValue());
    assertThat(productMapper, notNullValue());
    assertThat(catalogService, notNullValue());
    assertThat(getField(catalogService, "productMapper"), notNullValue());
  }
}
