package ApiResponse;

import com.google.gson.annotations.SerializedName;


public class PlanData {

    @SerializedName("address")
    public String address;

    @SerializedName("bathrooms")
    public String bathrooms;

    @SerializedName("bedrooms")
    public String bedrooms;

    @SerializedName("created")
    public String created;

    @SerializedName("fav_ids")
    public String fav_ids;

    @SerializedName("feet")
    public String feet;

    @SerializedName("id")
    public String id;

    @SerializedName("is_favorate")
    public String is_favorate;


    @SerializedName("is_purchase")
    public String is_purchase;

    @SerializedName("latitude")
    public String latitude;


    @SerializedName("longitude")
    public String longitude;

    @SerializedName("meter")
    public String meter;

    @SerializedName("plan_image")
    public String plan_image;

    @SerializedName("plan_pdf")
    public String plan_pdf;

    @SerializedName("plan_price")
    public String plan_price;

    @SerializedName("plan_thumb")
    public String plan_thumb;

    @SerializedName("square")
    public String square;

    @SerializedName("toilets")
    public String toilets;

    @SerializedName("dimention")
    public String dimention;

    public boolean isShownSubscribeButton = false;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFav_ids() {
        return fav_ids;
    }

    public void setFav_ids(String fav_ids) {
        this.fav_ids = fav_ids;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_favorate() {
        return is_favorate;
    }

    public void setIs_favorate(String is_favorate) {
        this.is_favorate = is_favorate;
    }

    public String getIs_purchase() {
        return is_purchase;
    }

    public void setIs_purchase(String is_purchase) {
        this.is_purchase = is_purchase;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getPlan_image() {
        return plan_image;
    }

    public void setPlan_image(String plan_image) {
        this.plan_image = plan_image;
    }

    public String getPlan_pdf() {
        return plan_pdf;
    }

    public void setPlan_pdf(String plan_pdf) {
        this.plan_pdf = plan_pdf;
    }

    public String getPlan_price() {
        return plan_price;
    }

    public void setPlan_price(String plan_price) {
        this.plan_price = plan_price;
    }

    public String getPlan_thumb() {
        return plan_thumb;
    }

    public void setPlan_thumb(String plan_thumb) {
        this.plan_thumb = plan_thumb;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getToilets() {
        return toilets;
    }

    public void setToilets(String toilets) {
        this.toilets = toilets;
    }

    public String getDimention() {
        return dimention;
    }

    public void setDimention(String dimention) {
        this.dimention = dimention;
    }

    public boolean isShownSubscribeButton() {
        return isShownSubscribeButton;
    }

    public void setIsShownSubscribeButton(boolean isShownSubscribeButton) {
        this.isShownSubscribeButton = isShownSubscribeButton;
    }
}