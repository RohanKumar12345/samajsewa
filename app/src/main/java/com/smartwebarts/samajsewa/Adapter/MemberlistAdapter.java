package com.smartwebarts.samajsewa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.samajsewa.Activity.MemberlistFullviewActivity;
import com.smartwebarts.samajsewa.Helper.Contraints;
import com.smartwebarts.samajsewa.Model.ShowmemberModel;
import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.MemberditalRecylviewBinding;
import com.smartwebarts.samajsewa.databinding.MyteamRecylviewBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemberlistAdapter extends RecyclerView.Adapter<MemberlistAdapter.ViewHolder> {
    Context context;
    ArrayList<ShowmemberModel> showmemberModels;

    public MemberlistAdapter(Context context, ArrayList<ShowmemberModel> showmemberModels) {
        this.context = context;
        this.showmemberModels = showmemberModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.memberdital_recylview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.memTitle.setText(showmemberModels.get(position).getName());
        Picasso.get().load(Contraints.photoUrl+showmemberModels.get(position).getProfile_img()).placeholder(R.drawable.logo).into(holder.binding.memImag);

    }

    @Override
    public int getItemCount() {
        return showmemberModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    MemberditalRecylviewBinding  binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=MemberditalRecylviewBinding.bind(itemView);
        }
    }
}
