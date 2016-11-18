package com.adbelsham.HousePlans;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import API.FloorPlanService;
import API.ServiceGenerator;
import ApiResponse.LoginResponse;
import Infrastructure.AppCommon;
import Infrastructure.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.emailEditText)
    EditText emailEditText;
    @InjectView(R.id.passwordEditText)
    EditText passwordEditText;

    @InjectView(R.id.progressView)
    ProgressBar progressView;

    String emailID;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    public boolean isDataValid() {
        boolean isDataValid = true;
        emailID = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        if (emailID.length() == 0) {
            isDataValid = false;
            emailEditText.setError("Email is required.");
        } else if (!Utils.isValidEmail(emailID)) {
            isDataValid = false;
            emailEditText.setError("Enter valid email.");
        } else if (password.length() == 0) {
            isDataValid = false;
            passwordEditText.setError("Password is required");
        }
        return isDataValid;
    }

    @OnClick(R.id.loginClick)
    public void loginClick() {
        if (isDataValid()) {
            if (AppCommon.isConnectingToInternet(this)) {
                progressView.setVisibility(View.VISIBLE);
                String android_id = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
                Call call = fp.loginUser(emailID, password, android_id, "android", "");
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Response response) {
                        progressView.setVisibility(View.GONE);
                        LoginResponse loginResponseObj = (LoginResponse) response.body();
                        if (loginResponseObj.getSuccess().equals("1")) {
                            AppCommon.getInstance(LoginActivity.this).setIsLogin(true);
                            AppCommon.getInstance(LoginActivity.this).setUserID(loginResponseObj.getUserDetail().getId());
                            AppCommon.getInstance(LoginActivity.this).setLatitude(loginResponseObj.getUserDetail().getLat());
                            AppCommon.getInstance(LoginActivity.this).setLongitude(loginResponseObj.getUserDetail().getLongt());
                            showSuccessfulDialog("Login Successfully!");
                        } else {
                            AppCommon.showDialog(LoginActivity.this, loginResponseObj.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        progressView.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            progressView.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    public void showSuccessfulDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                LoginActivity.this.finish();
            }
        });
        builder.show();
    }

    @OnClick(R.id.registerButton)
    public void registerButtonClick() {
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registerIntent);
        this.finish();
    }

    @OnClick(R.id.forgotPasswordBtn)
    public void forgotPasswordClick() {
        Intent registerIntent = new Intent(this, ForgotPasswodActivity.class);
        startActivity(registerIntent);
        this.finish();
    }
}
