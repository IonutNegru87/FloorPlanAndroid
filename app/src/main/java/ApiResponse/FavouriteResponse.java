package ApiResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piyushgupta on 6/15/16.
 */
public class FavouriteResponse {

    @SerializedName("error")
    public String error;

    @SerializedName("msg")
    public String msg;

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
}
