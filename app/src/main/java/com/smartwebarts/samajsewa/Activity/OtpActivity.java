package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityOtpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    String otp = "";
    String resendotp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otp = getIntent().getStringExtra("varification");


        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.resendOtp.setText("" + millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
                binding.resendOtp.setText("resend otp");
            }
        }.start();

        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resendOtp(); api call
                resendOtp();
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        binding.resendOtp.setText(millisUntilFinished / 1000 + " sec");
                    }
                    public void onFinish() {
                        binding.resendOtp.setText("resend otp");
                    }
                }.start();

            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getIntent().getStringExtra("otp").equals(binding.otpView.getOTP())) {
                    Intent intent = new Intent(getApplicationContext(), ChangepasswordActivity.class);
                    startActivity(intent);
                    finish();

                } else if (resendotp.equals(binding.otpView.getOTP())) {
                    Intent intent = new Intent(getApplicationContext(), ChangepasswordActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Chek the email and input otp", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void resendOtp() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(OtpActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.forgotPassword(getIntent().getStringExtra("email"));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("resend_response", response.body().toString());

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        resendotp= jsonObject.getString("otp");


                        Toast.makeText(OtpActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(OtpActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(OtpActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("resend_error", t.getMessage());
                loadingBar.dismiss();

            }
        });


    }

}