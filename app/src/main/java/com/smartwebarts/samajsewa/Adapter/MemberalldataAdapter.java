package com.smartwebarts.samajsewa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.samajsewa.R;

public class MemberalldataAdapter extends RecyclerView.Adapter<MemberalldataAdapter.ViewHoder> {
    Context context;

    public MemberalldataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.memberdital_recylview,null);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
