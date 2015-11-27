package com.kiroule.jpetstore.vaadinspring.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
public class Banner implements Serializable {

  private String favouriteCategoryId;
  private String bannerName;

  public String getFavouriteCategoryId() {
    return favouriteCategoryId;
  }

  public void setFavouriteCategoryId(String favouriteCategoryId) {
    this.favouriteCategoryId = favouriteCategoryId;
  }

  public String getBannerName() {
    return bannerName;
  }

  public void setBannerName(String bannerName) {
    this.bannerName = bannerName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Banner banner = (Banner) o;
    return Objects.equal(favouriteCategoryId, banner.favouriteCategoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(favouriteCategoryId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("favouriteCategoryId", favouriteCategoryId)
        .add("bannerName", bannerName)
        .toString();
  }
}
