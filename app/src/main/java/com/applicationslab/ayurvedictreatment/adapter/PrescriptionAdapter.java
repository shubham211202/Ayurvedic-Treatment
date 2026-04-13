package com.applicationslab.ayurvedictreatment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.database.Prescription;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {

    ArrayList<Prescription> list;

    public PrescriptionAdapter(ArrayList<Prescription> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDisease, txtTreatment;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDisease = itemView.findViewById(R.id.txtDisease);
            txtTreatment = itemView.findViewById(R.id.txtTreatment);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Prescription p = list.get(position);

        holder.txtName.setText("📅 " + p.getDate());
        holder.txtDisease.setText("Disease: " + p.getDisease());
        holder.txtTreatment.setText("Treatment: " + p.getTreatment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}