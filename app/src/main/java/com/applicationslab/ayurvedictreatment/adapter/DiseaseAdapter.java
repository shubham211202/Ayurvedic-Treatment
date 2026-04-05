package com.applicationslab.ayurvedictreatment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.activity.SuggestedHerbalPlantActivity;
import com.applicationslab.ayurvedictreatment.datamodel.DiseaseSearchData;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {

    Activity mContext;
    ArrayList<DiseaseSearchData> diseaseSearchData;

    public DiseaseAdapter(Activity mContext, ArrayList<DiseaseSearchData> diseaseSearchData) {
        this.mContext = mContext;
        this.diseaseSearchData = diseaseSearchData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_diseases, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtName.setText(diseaseSearchData.get(position).getDiseaseName());
    }

    @Override
    public int getItemCount() {
        return diseaseSearchData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtName;
        RelativeLayout relativeContainer;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            relativeContainer = itemView.findViewById(R.id.relativeContainer);

            relativeContainer.setOnClickListener(this);

            Typeface tfRegular = Typeface.createFromAsset(
                    mContext.getAssets(), "fonts/OpenSansRegular.ttf");
            txtName.setTypeface(tfRegular);
        }

        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();

            // safety check
            if (pos == RecyclerView.NO_POSITION) return;

            if (v.getId() == R.id.relativeContainer) {
                Intent intent = new Intent(mContext, SuggestedHerbalPlantActivity.class);
                intent.putExtra("title", diseaseSearchData.get(pos).getDiseaseName());
                intent.putIntegerArrayListExtra("position",
                        diseaseSearchData.get(pos).getPlantPositions());

                mContext.startActivity(intent);
            }
        }
    }
}