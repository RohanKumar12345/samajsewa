package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity {
    ActivityMyProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}