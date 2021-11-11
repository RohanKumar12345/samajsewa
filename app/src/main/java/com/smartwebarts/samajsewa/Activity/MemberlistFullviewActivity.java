package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityMemberlistFullviewBinding;

public class MemberlistFullviewActivity extends AppCompatActivity {
    ActivityMemberlistFullviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberlistFullviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}