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
import com.smartwebarts.samajsewa.Helper.PrefrenceManager;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityChangepasswordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangepasswordActivity extends AppCompatActivity {
    ActivityChangepasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangepasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }


    public void changePassword() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(ChangepasswordActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        //new PrefrenceManager(getApplicationContext()).getmobilno()
        Call<JsonObject> call = getDataService.updateForgetPassword("8707091593", binding.newPassword.getText().toString(), binding.confirmPassword.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("forgot_response", response.body().toString());

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ChangepasswordActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ChangepasswordActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(ChangepasswordActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
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