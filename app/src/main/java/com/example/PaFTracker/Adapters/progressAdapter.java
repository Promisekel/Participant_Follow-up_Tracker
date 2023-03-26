package com.example.PaFTracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PaFTracker.Models.progressModel;
import com.example.PaFTracker.R;

import java.util.ArrayList;

public class progressAdapter extends RecyclerView.Adapter<progressAdapter.HolderProgress> {


    private Context context;
    private ArrayList<progressModel> pmodel;

    public progressAdapter(Context context, ArrayList<progressModel> pmodel) {
        this.context = context;
        this.pmodel = pmodel;
    }


    @NonNull
    @Override
    public HolderProgress onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_item_row,parent,false);
        return new HolderProgress(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProgress holder, int position) {
        progressModel model= pmodel.get(position);
        String pid = model.getPID();
        String passport = model.getPASSPORT();

        holder.pid.setText(pid);


    }


    @Override
    public int getItemCount() {
        return pmodel.size();
    }

    public static class HolderProgress extends RecyclerView.ViewHolder {

        private ImageView profileIv;
        private TextView pid;

        public HolderProgress(@NonNull View itemView) {
            super(itemView);

            profileIv=itemView.findViewById(R.id.profileIv);
            pid=itemView.findViewById(R.id.pid);

        }
    }
}
