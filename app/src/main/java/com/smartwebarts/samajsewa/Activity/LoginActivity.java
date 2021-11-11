package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.PrefrenceManager;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String remail, rpassword;
    Button contivew, call;

    PrefrenceManager prefrenceManager;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        prefrenceManager = new PrefrenceManager(getApplicationContext());

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.referlpermission_poup, null);
                alertDialog.setView(view);
                contivew = view.findViewById(R.id.contivew);
                call = view.findViewById(R.id.call);
//                adv_image.setImageResource(R.drawable.aadharcard_img);
                alertDialog.setCancelable(false);
                alertDialog.show();

                contivew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), RegtrationActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
                    }
                });
    call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        makeCall("+91 8932090067");
                    }
                });


            }
        });

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotpassowordActivity.class);
                startActivity(intent);
            }
        });


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    registration();
                }

            }
        });

    }

    public boolean validation() {

        remail = binding.email.getText().toString();

        rpassword = binding.Password.getText().toString();


        if (remail.isEmpty()) {
            binding.email.requestFocus();
            binding.email.setError("Enter the email");
        } else if (rpassword.isEmpty()) {
            binding.Password.requestFocus();
            binding.Password.setText("Enter the password");
        } else if (rpassword.length() < 8) {
            binding.Password.requestFocus();
            binding.Password.setError("Enter the 8 Digit  password");
        } else {
            return true;
        }
        return false;

    }


    public void registration() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(LoginActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.login(remail,
                rpassword);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();
                Log.e("login_response", response.body().toString());
                Log.e("login_response", remail + " " +
                        rpassword);
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("User_data");
                        prefrenceManager.setUserid( jsonObject1.getString( "id" ) );

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    //Toast.makeText(RegtrationActivity.this,e.getMessage()+ "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("login_error", t.getMessage());
                loadingBar.dismiss();

            }
        });


    }

    private void makeCall(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

}