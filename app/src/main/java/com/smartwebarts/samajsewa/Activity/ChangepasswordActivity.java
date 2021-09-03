package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityChangepasswordBinding;

public class ChangepasswordActivity extends AppCompatActivity {
    ActivityChangepasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangepasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}