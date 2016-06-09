package ApiResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SearchPlanResponse {

    @SerializedName("success")
    public String success;

    @SerializedName("data")
    public ArrayList<PlanData> planDataArrayList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<PlanData> getPlanDataArrayList() {
        return planDataArrayList;
    }

    public void setPlanDataArrayList(ArrayList<PlanData> planDataArrayList) {
        this.planDataArrayList = planDataArrayList;
    }
}