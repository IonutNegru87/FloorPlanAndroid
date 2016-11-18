package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import API.FloorPlanService;
import API.ServiceGenerator;
import ApiResponse.ForgetPasswordResponse;
import ApiResponse.ResetResponse;
import Infrastructure.AppCommon;
import Infrastructure.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswodActivity extends AppCompatActivity {

    @InjectView(R.id.emailEditText)
    EditText emailEditText;

    @InjectView(R.id.progressView)
    ProgressBar progressView;

    String emailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwod);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.backArrow)
    public void backBtnClick() {
        this.finish();
    }

    public boolean isDataValid() {
        boolean isDataValid = true;
        emailID = emailEditText.getText().toString().trim();
        if (emailID.length() == 0) {
            isDataValid = false;
            emailEditText.setError("Email id is required.");
        } else if (!Utils.isValidEmail(emailID)) {
            isDataValid = false;
            emailEditText.setError("Enter valid email.");
        }
        return isDataValid;
    }

    @OnClick(R.id.changeClick)
    public void forgotPasswordClick() {
        if (isDataValid()) {
            forgotPasswordAPi();
        }
    }

    public void forgotPasswordAPi() {
        if (AppCommon.isConnectingToInternet(this)) {
            progressView.setVisibility(View.VISIBLE);
            FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
            Call call = fp.forgotPassword(emailID);
            call.enqueue(new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Response response) {
                    progressView.setVisibility(View.GONE);
                    ForgetPasswordResponse forgetResponse = (ForgetPasswordResponse) response.body();
                    if (forgetResponse.getError().equals("0")) {
                        AppCommon.showDialog(ForgotPasswodActivity.this, forgetResponse.getMsg());
                    } else {
                        AppCommon.showDialog(ForgotPasswodActivity.this, forgetResponse.getMsg());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    progressView.setVisibility(View.GONE);
                }
            });
        } else {
            progressView.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }
}
