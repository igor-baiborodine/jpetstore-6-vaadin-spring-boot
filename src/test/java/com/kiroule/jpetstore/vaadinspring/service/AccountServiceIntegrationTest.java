package com.kiroule.jpetstore.vaadinspring.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.kiroule.jpetstore.vaadinspring.JPetStore6Application;
import com.kiroule.jpetstore.vaadinspring.domain.Account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JPetStore6Application.class)
@WebAppConfiguration
public class AccountServiceIntegrationTest {

  @Autowired
  private AccountService accountService;

  @Test
  public void insertAccount_booleanValuesForListOptionAndBannerOption_shouldPersistBooleanValuesAsCorrespondingIntegerValues() throws Exception {

    String username = "insert-user";
    Account account = createAccount(username);
    account.setBannerOption(true);
    account.setListOption(true);

    accountService.insertAccount(account);

    Account insertedAccount = accountService.getAccount(username);
    assertThat(insertedAccount.isBannerOption(), is(true));
    assertThat(insertedAccount.isListOption(), is(true));
  }

  @Test
  public void updateAccount_booleanValuesForListOptionAndBannerOption_shouldPersistBooleanValuesAsCorrespondingIntegerValues() throws Exception {

    String username = "update-user";
    Account account = createAccount(username);
    account.setBannerOption(true);
    account.setListOption(true);
    accountService.insertAccount(account);

    account.setListOption(false);
    account.setBannerOption(false);
    accountService.updateAccount(account);

    Account updatedAccount = accountService.getAccount(username);
    assertThat(updatedAccount.isBannerOption(), is(false));
    assertThat(updatedAccount.isListOption(), is(false));
  }

  private Account createAccount(String username) {

    Account account = new Account();
    account.setUsername(username);
    account.setPassword("pizza");
    account.setEmail("pfry@planet-express.earth");
    account.setFirstName("Philip");
    account.setLastName("Fry");
    account.setStatus("OK");
    account.setAddress1("901 San Antonio Road");
    account.setAddress2("MS UCUP02-206");
    account.setCity("Palo Alto");
    account.setState("CA");
    account.setZip("94303");
    account.setCountry("USA");
    account.setPhone("555-555-5555");
    account.setFavouriteCategoryId("CATS");
    account.setLanguagePreference("english");
    return account;
  }
}