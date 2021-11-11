package com.smartwebarts.samajsewa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.samajsewa.Activity.MemberlistFullviewActivity;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.MyteamRecylviewBinding;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder> {
    Context context;

    public MyTeamAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.myteam_recylview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();
//

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" + "+91 8932090067"));
                context.startActivity(intent);
            }
        });

      holder.binding.meberRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MemberlistFullviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyteamRecylviewBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=MyteamRecylviewBinding.bind(itemView);
        }
    }

    private void makeCall(String number) {

    }

}
