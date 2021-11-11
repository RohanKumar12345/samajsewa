package com.smartwebarts.samajsewa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.smartwebarts.samajsewa.Adapter.MemberalldataAdapter;
import com.smartwebarts.samajsewa.Adapter.MemberlistAdapter;
import com.smartwebarts.samajsewa.Helper.GetDataService;
import com.smartwebarts.samajsewa.Helper.RetrofitClintanse;
import com.smartwebarts.samajsewa.Model.ShowmemberModel;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityForgotpassowordBinding;
import com.smartwebarts.samajsewa.databinding.ActivityMemberAllBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAllActivity extends AppCompatActivity {
    ActivityMemberAllBinding binding;
    ArrayList<ShowmemberModel> showmemberModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        showMember();

    }


    public void showMember() {
        final android.app.AlertDialog loadingBar = new SpotsDialog(MemberAllActivity.this, R.style.Custom);
        loadingBar.setCancelable(false);
        loadingBar.show();
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonArray> call = getDataService.showsMembers("operator");
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                loadingBar.dismiss();
                Log.e("showmemb_response", response.body().toString());

                try {
                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ShowmemberModel showmemberModel = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ShowmemberModel.class);
                        showmemberModels.add(showmemberModel);

                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.allmemberRecyl.setLayoutManager(layoutManager);
                    binding.allmemberRecyl.setHasFixedSize(true);
                    MemberlistAdapter adapter = new MemberlistAdapter(getApplicationContext(), showmemberModels);
                    binding.allmemberRecyl.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(MemberAllActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("showmemb_error", t.getMessage());
                loadingBar.dismiss();

            }
        });


    }
}