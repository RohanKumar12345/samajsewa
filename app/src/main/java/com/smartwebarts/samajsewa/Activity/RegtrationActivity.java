package com.smartwebarts.samajsewa.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.PrefrenceManager;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityRegtrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegtrationActivity extends AppCompatActivity {
    ActivityRegtrationBinding binding;

    String[] gender = {"Male", "Female"};
    String gender_type;
    String[] statess = {"Uttar Pradesh"};
    String state_list;
    PrefrenceManager prefrenceManager;
    Uri uri1 = Uri.parse("");
    CircularImageView profile_img;

    String rname, rfathername, rage, remail, rmobile, raddress, rcity, rcountry, rpincode, rqualification, roccupation, rpassword, rrefral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegtrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profile_img = findViewById(R.id.profile_img);
        prefrenceManager = new PrefrenceManager(getApplicationContext());


        final Spinner gend = findViewById(R.id.ganders);
        final Spinner statde = findViewById(R.id.states);

        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validation()) {
                    registration();

                }

            }
        });

        binding.singNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter assa = new ArrayAdapter(RegtrationActivity.this, android.R.layout.simple_spinner_item, statess);
        assa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statde.setAdapter(assa);

        statde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state_list = statde.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter asa = new ArrayAdapter(RegtrationActivity.this, android.R.layout.simple_spinner_item, gender);
        asa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gend.setAdapter(asa);

        gend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_type = gend.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(RegtrationActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(101);
            }
        });

    }

    public boolean validation() {

        rname = binding.name.getText().toString();
        rfathername = binding.fathername.getText().toString();
        rage = binding.age.getText().toString();
        remail = binding.email.getText().toString();
        rmobile = binding.mobile.getText().toString();
        raddress = binding.address.getText().toString();
        rcity = binding.city.getText().toString();
        rpincode = binding.pinCode.getText().toString();
        rqualification = binding.qualifications.getText().toString();
        rpassword = binding.Password.getText().toString();
        rcountry = binding.country.getText().toString();
        roccupation = binding.occupation.getText().toString();
        rrefral = binding.refal.getText().toString();


        if (rname.isEmpty()) {
            binding.name.requestFocus();
            binding.name.setError("Enter the your name");
        } else if (rfathername.isEmpty()) {
            binding.fathername.requestFocus();
            binding.fathername.setError("Enter the your father name");
        } else if (rage.isEmpty()) {
            binding.age.requestFocus();
            binding.age.setError("Enter the your age");
        } else if (rage.length() < 2) {
            binding.age.requestFocus();
            binding.age.setError("Enter the 2 digit number");
        } else if (remail.isEmpty()) {
            binding.email.requestFocus();
            binding.email.setError("Enter the email");
        } else if (rmobile.isEmpty()) {
            binding.mobile.requestFocus();
            binding.mobile.setText("Enter the your  mobile number");
        } else if (rmobile.length() < 10) {
            binding.mobile.requestFocus();
            binding.mobile.setError("Enter the 10  number digit");
        } else if (raddress.isEmpty()) {
            binding.address.requestFocus();
            binding.address.setError("Enter the your address");
        } else if (rcity.isEmpty()) {
            binding.city.requestFocus();
            binding.city.setError("Enter the your city");
        } else if (rcountry.isEmpty()) {
            binding.country.requestFocus();
            binding.country.setError("Enter the country");
        } else if (rpincode.isEmpty()) {
            binding.pinCode.requestFocus();
            binding.pinCode.setError("Enter the your location pin code");
        } else if (rpincode.length() < 6) {
            binding.pinCode.requestFocus();
            binding.pinCode.setError("Enter the 6 digit number");
        } else if (rqualification.isEmpty()) {
            binding.qualifications.requestFocus();
            binding.qualifications.setError("Enter the qualification");
        } else if (roccupation.isEmpty()) {
            binding.occupation.requestFocus();
            binding.occupation.setError("Enter the occupation");
        } else if (rpassword.isEmpty()) {
            binding.Password.requestFocus();
            binding.Password.setText("Enter the password");
        } else if (rpassword.length() < 8) {
            binding.Password.requestFocus();
            binding.Password.setError("Enter the 8 Digit  password");
        } else if (rrefral.isEmpty()) {
            binding.refal.requestFocus();
            binding.refal.setText("Enter the refal number");

        } else if (rrefral.length() < 8) {
            binding.refal.requestFocus();
            binding.refal.setError("Enter the 8 Digit  password");
        } else {
            return true;
        }
        return false;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 101: {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    uri1 = data.getData();
                    Log.e("image", uri1.toString());
                    profile_img.setImageURI(uri1);

                    break;
                }
            }
        }
    }


    public String getImageString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);

            return imageString;

        } else {
            return "N/A";
        }

    }


    public void registration() {


        final android.app.AlertDialog loadingBar = new SpotsDialog(RegtrationActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();

        File file = new File(uri1.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part imagess = MultipartBody.Part.createFormData("image", uri1.getPath(), requestFile);
        Log.e("imagess", imagess.body().toString());
        Log.e("imagess", uri1.getPath());


        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.registration(
                imagess,
                RequestBody.create(MediaType.parse("multipart/form-data"), rname),
                RequestBody.create(MediaType.parse("multipart/form-data"), rfathername),
                RequestBody.create(MediaType.parse("multipart/form-data"), rage),
                RequestBody.create(MediaType.parse("multipart/form-data"), gender_type),
                RequestBody.create(MediaType.parse("multipart/form-data"), remail),
                RequestBody.create(MediaType.parse("multipart/form-data"), rmobile),
                RequestBody.create(MediaType.parse("multipart/form-data"), binding.mobileOps.getText().toString()),
                RequestBody.create(MediaType.parse("multipart/form-data"), raddress),
                RequestBody.create(MediaType.parse("multipart/form-data"), state_list),
                RequestBody.create(MediaType.parse("multipart/form-data"), rcity),
                RequestBody.create(MediaType.parse("multipart/form-data"), rcountry),
                RequestBody.create(MediaType.parse("multipart/form-data"), rpincode),
                RequestBody.create(MediaType.parse("multipart/form-data"), rqualification),
                RequestBody.create(MediaType.parse("multipart/form-data"), roccupation),
                RequestBody.create(MediaType.parse("multipart/form-data"), rpassword),
                RequestBody.create(MediaType.parse("multipart/form-data"), rrefral));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loadingBar.dismiss();


                Log.e("regitrations_response", String.valueOf(imagess) + "" + rname + "" + rfathername + " " +
                        rage + " " + gender_type + " " +
                        remail + " " + rmobile);
                Log.e("regitration_response", response.body().toString());

                //  Toast.makeText(getApplicationContext(),image+ "api", Toast.LENGTH_SHORT).show();

                try {
//
//
//                    Log.e("regitration_response", image,);

                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("User_data");
                        prefrenceManager.setUserid(jsonObject1.getString("id"));
                        prefrenceManager.setphoto(jsonObject1.getString("profile_img"));
                        prefrenceManager.setMobileno(jsonObject1.getString("perma_contact_no"));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("varification", "Regtration");
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegtrationActivity.this, jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegtrationActivity.this, jsonObject.getString("msg") + "error", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    // Toast.makeText(RegtrationActivity.this,e.getMessage()+ "", Toast.LENGTH_SHORT).show();
                    Log.e("regitration_ex", response.body().toString());
                    Log.e("regitration_exr", e.getMessage());
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("regitration_error", String.valueOf(t));

                Toast.makeText(RegtrationActivity.this, t.getMessage() + "error", Toast.LENGTH_SHORT).show();

                loadingBar.dismiss();

            }
        });


    }

}