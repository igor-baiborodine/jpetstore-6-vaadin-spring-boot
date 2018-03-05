/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kiroule.jpetstore.vaadinspring.domain;

import com.google.common.base.MoreObjects;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Igor Baiborodine
 *
 */
public class BillingDetails extends OrderDetails {

  private static final long serialVersionUID = 4974454158308328436L;

  // Payment method
  @NotEmpty
  private String cardType;
  @NotEmpty
  private String cardNumber;
  @NotEmpty
  private String expiryDate;

  public BillingDetails() {}

  public BillingDetails(Account account) {
    super(account);
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cardType", cardType)
        .add("cardNumber", cardNumber)
        .add("expiryDate", expiryDate)
        .toString();
  }
}