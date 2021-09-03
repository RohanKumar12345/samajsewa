package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityRegtrationBinding;

public class RegtrationActivity extends AppCompatActivity {
    ActivityRegtrationBinding binding;

    String[] gender= { "Male","Female"};
    String gender_type;
    String[] statess={"Uttar Pradesh"};
    String state_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegtrationBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );



        final Spinner gend = findViewById(R.id.ganders);
        final Spinner statde = findViewById(R.id.states);

        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),OtpActivity.class);
                intent.putExtra( "varification","Regtration" );
                startActivity(intent);
                finish();
            }
        });

        binding.singNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ArrayAdapter asa = new ArrayAdapter(RegtrationActivity.this, android.R.layout.simple_spinner_item, gender );
        asa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        gend.setAdapter(asa);

        gend.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_type=gend.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


      ArrayAdapter assa = new ArrayAdapter(RegtrationActivity.this, android.R.layout.simple_spinner_item, statess );
        asa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        statde.setAdapter(assa);

        statde.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state_list=statde.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );




    }
}