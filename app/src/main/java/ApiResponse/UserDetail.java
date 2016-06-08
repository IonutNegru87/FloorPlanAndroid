package ApiResponse;

import com.google.gson.annotations.SerializedName;


public class UserDetail {

    @SerializedName("email")
    public String email;

    @SerializedName("id")
    public String id;

    @SerializedName("is_purchase")
    public String is_purchase;

    @SerializedName("lat")
    public String lat;

    @SerializedName("longt")
    public String longt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIs_purchase() {
        return is_purchase;
    }

    public void setIs_purchase(String is_purchase) {
        this.is_purchase = is_purchase;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }
}