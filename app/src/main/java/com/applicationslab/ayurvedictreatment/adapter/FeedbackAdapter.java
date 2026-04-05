package com.applicationslab.ayurvedictreatment.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.datamodel.FeedbackData;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Activity mContext;
    ArrayList<FeedbackData> feedbackDatas;

    public FeedbackAdapter(Activity mContext, ArrayList<FeedbackData> feedbackDatas) {
        this.mContext = mContext;
        this.feedbackDatas = feedbackDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_feedback, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtName.setText(feedbackDatas.get(position).getName());
        holder.txtDetails.setText(feedbackDatas.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return feedbackDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDetails = itemView.findViewById(R.id.txtDetails);

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSansRegular.ttf");
            txtName.setTypeface(tf);
            txtDetails.setTypeface(tf);
        }
    }
}