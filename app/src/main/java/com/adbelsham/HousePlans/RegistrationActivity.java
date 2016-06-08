package com.adbelsham.HousePlans;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import API.FloorPlanService;
import API.ServiceGenerator;
import ApiResponse.Registration;
import Infrastructure.AppCommon;
import Infrastructure.Utils;
import LocationInfra.GPSTracker;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    @InjectView(R.id.emailEditText)
    EditText emailEditText;
    @InjectView(R.id.passwordEditText)
    EditText passwordEditText;
    @InjectView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;

    GPSTracker gpsTracker;
    String emailID;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.inject(this);
        gpsTracker = new GPSTracker(this);
    }

    public boolean isDataValid() {
        boolean isDataValid = true;
        emailID = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (emailID.length() == 0) {
            isDataValid = false;
            emailEditText.setError("Email is required.");
        } else if (!Utils.isValidEmail(emailID)) {
            isDataValid = false;
            emailEditText.setError("Enter valid email.");
        } else if (password.length() == 0) {
            isDataValid = false;
            passwordEditText.setError("Password is required");
        } else if (confirmPassword.length() == 0) {
            isDataValid = false;
            confirmPasswordEditText.setError("Confirm Password is required");
        } else if (!password.equals(confirmPassword)) {
            isDataValid = false;
            confirmPasswordEditText.setError("Password doesn't match");
        }

        return isDataValid;
    }

    @OnClick(R.id.backArrow)
    public void backClick() {
        loginIntent();
    }

    public void loginIntent() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        loginIntent();
    }

    @OnClick(R.id.registerButton)
    public void registerButtonClick() {
        if (isDataValid()) {

            String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            FloorPlanService fp = ServiceGenerator.createService(FloorPlanService.class);
            Call call = fp.registerUser(emailID, password, "android", android_id, "dev-signup", Double.toString(latitude), Double.toString(longitude));
            call.enqueue(new Callback<Registration>() {
                @Override
                public void onResponse(Response response) {
                    ApiResponse.Registration registrationObj = (Registration) response.body();
                    if (registrationObj.getSuccess().equals("success")) {
                        showSuccessfulDialog("Registration Successfully!");
                    } else {
                        AppCommon.showDialog(RegistrationActivity.this, registrationObj.getMsg());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.v("Success", "" + t.toString());
                }
            });
        }
    }

    public void showSuccessfulDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent homeIntent = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                RegistrationActivity.this.finish();
            }
        });
        builder.show();
    }

}
