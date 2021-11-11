package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.PrefrenceManager;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splase);


        Log.e("userid",new PrefrenceManager( getApplicationContext() ).getUserid());




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    userVerification();

                }
            }, 3000 );


    }


    public void userVerification() {

        final android.app.AlertDialog loadingBar = new SpotsDialog(SplaseActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.userverification(new PrefrenceManager(getApplicationContext()).getUserid());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("verification_response", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("verification_exp",e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
              Log.e("verification_error",t.getMessage());
                loadingBar.dismiss();
            }
        });
    }
}