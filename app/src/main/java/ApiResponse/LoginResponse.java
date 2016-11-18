package ApiResponse;

import com.google.gson.annotations.SerializedName;


public class LoginResponse {

    @SerializedName("success")
    public String success;

    @SerializedName("msg")
    public String msg;

    @SerializedName("user-detail")
    public UserDetail userDetail;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}