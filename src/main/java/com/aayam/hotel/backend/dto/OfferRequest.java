package com.aayam.hotel.backend.dto;

public class OfferRequest {
    private String discountCode;
    private Integer percentage;
    private String validTill;
    private String bannerUrl;

    // Getters and Setters
    public String getDiscountCode() { return discountCode; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
    public Integer getPercentage() { return percentage; }
    public void setPercentage(Integer percentage) { this.percentage = percentage; }
    public String getValidTill() { return validTill; }
    public void setValidTill(String validTill) { this.validTill = validTill; }
    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }
}