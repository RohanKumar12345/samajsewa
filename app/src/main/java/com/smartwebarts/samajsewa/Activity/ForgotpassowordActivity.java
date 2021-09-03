package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smartwebarts.samajsewa.databinding.ActivityForgotpassowordBinding;

public class ForgotpassowordActivity extends AppCompatActivity {
    ActivityForgotpassowordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotpassowordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.forgotPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),OtpActivity.class);
                intent.putExtra( "varification","forgotpassword" );
                startActivity(intent);
            }
        });




    }
}