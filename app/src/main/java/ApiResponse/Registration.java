package ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Registration {

    @SerializedName("error")
    public String error;

    @SerializedName("msg")
    public String msg;

    @SerializedName("success")
    public String success;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}