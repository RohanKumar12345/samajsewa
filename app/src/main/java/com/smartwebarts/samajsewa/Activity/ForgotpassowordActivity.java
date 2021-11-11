package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityForgotpassowordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotpassowordActivity extends AppCompatActivity {
    ActivityForgotpassowordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotpassowordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.forgotPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgotPassword();

            }
        });


    }

    public void forgotPassword() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(ForgotpassowordActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.forgotPassword(binding.email.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("forgot_response", response.body().toString());

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {

                        Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                        intent.putExtra("otp",jsonObject.getString("otp"));
                        intent.putExtra("email",jsonObject.getString("Email"));
                        startActivity(intent);

                        Toast.makeText(ForgotpassowordActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ForgotpassowordActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                 Toast.makeText(ForgotpassowordActivity.this,e.getMessage()+ "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("forgot_error", t.getMessage());
                loadingBar.dismiss();

            }
        });


    }

}