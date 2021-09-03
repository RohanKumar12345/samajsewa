package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.smartwebarts.samajsewa.databinding.ActivityOtpBinding;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    String otp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        otp=getIntent().getStringExtra("varification");




        new CountDownTimer( 30000, 1000 ) {
            public void onTick(long millisUntilFinished) {
                binding.resendOtp.setText( "" + millisUntilFinished / 1000 + " sec" );

            }

            public void onFinish() {
                binding.resendOtp.setText( "resend otp" );
            }
        }.start();


        binding.resendOtp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resendOtp(); api call
                new CountDownTimer( 30000, 1000 ) {
                    public void onTick(long millisUntilFinished) {
                        binding.resendOtp.setText( millisUntilFinished / 1000 + " sec" );

                    }

                    public void onFinish() {
                        binding.resendOtp.setText( "resend otp" );
                    }
                }.start();

            }
        } );

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp.equals("forgotpassword")){
                    Intent intent=new Intent(getApplicationContext(),ChangepasswordActivity.class);
                    startActivity(intent);
                    finish();

                }else if (otp.equals("Regtration")){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}