package com.adbelsham.HousePlans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import API.FloorPlanService;
import API.ServiceGenerator;
import ApiResponse.LogoutResponse;
import ApiResponse.ResetResponse;
import Infrastructure.AppCommon;
import Infrastructure.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswodActivity extends AppCompatActivity {

    @InjectView(R.id.currentPasswordEditText)
    EditText currentPasswordEditText;

    @InjectView(R.id.newPasswordEditText)
    EditText newPasswordEditText;

    @InjectView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;

    @InjectView(R.id.progressView)
    ProgressBar progressView;

    String currentPassword;
    String newPassword;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwod);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }

    public boolean isDataValid() {
        boolean isDataValid = true;
        currentPassword = currentPasswordEditText.getText().toString().trim();
        newPassword=newPasswordEditText.getText().toString().trim();
        confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (currentPassword.length() == 0) {
            isDataValid = false;
            currentPasswordEditText.setError("Current Password is required.");
        } else if (newPassword.length()==0) {
            isDataValid = false;
            newPasswordEditText.setError("New Password is required.");
        } else if (confirmPassword.length() == 0) {
            isDataValid = false;
            confirmPasswordEditText.setError("Confirm Password is required.");
        }else if(!newPassword.equals(confirmPassword)){
            isDataValid= false;
            confirmPasswordEditText.setError("Confirm Password doesn't match");
        }
        return isDataValid;
    }

    @OnClick(R.id.changeClick)
    public void changePasswordClick(){
        if(isDataValid()){
            changePasswordApi();
        }
    }

    public void changePasswordApi() {
        if (AppCommon.isConnectingToInternet(this)) {
            progressView.setVisibility(View.VISIBLE);
            FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
            Call call = fp.resetPassword(AppCommon.getInstance(this).getUserID(), currentPassword, newPassword);
            call.enqueue(new Callback<ResetResponse>() {
                @Override
                public void onResponse(Response response) {
                    progressView.setVisibility(View.GONE);
                    ResetResponse resetResponse = (ResetResponse) response.body();
                    if (resetResponse.getError().equals("0")) {
                        AppCommon.showDialog(ChangePasswodActivity.this, resetResponse.getMsg());
                    } else {
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    progressView.setVisibility(View.GONE);
                }
            });
        }else{
            progressView.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }
}
