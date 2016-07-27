package ApiResponse;

import com.google.gson.annotations.SerializedName;


public class PurchaseAppResponse {

    @SerializedName("error")
    public String error;

    @SerializedName("msg")
    public String msg;

    @SerializedName("user-detail")
    public  UserDetail userDetail;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}