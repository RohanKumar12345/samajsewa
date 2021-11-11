package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smartwebarts.samajsewa.databinding.ActivityMobileveriBinding;


public class MobileveriActivity extends AppCompatActivity {
    ActivityMobileveriBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMobileveriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mobileVerifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                intent.putExtra("varification", "mobileverification");
                startActivity(intent);
                finish();
            }
        });

    }
}