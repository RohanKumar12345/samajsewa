package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.smartwebarts.samajsewa.Adapter.MyTeamAdapter;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityMyTeamBinding;

public class MyTeamActivity extends AppCompatActivity {

    ActivityMyTeamBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        binding.mytamRecy.setLayoutManager(layoutManager);
        binding.mytamRecy.setHasFixedSize(true);
        MyTeamAdapter adapter=new MyTeamAdapter(getApplicationContext());
        binding.mytamRecy.setAdapter(adapter);

    }
}