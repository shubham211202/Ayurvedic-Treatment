package com.applicationslab.ayurvedictreatment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.activity.*;
import com.applicationslab.ayurvedictreatment.datamodel.OptionsData;
import com.applicationslab.ayurvedictreatment.utility.PreferenceUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.MyViewHolder> {

    Activity mContext;
    ArrayList<OptionsData> optionsData;

    public OptionsAdapter(Activity mContext, ArrayList<OptionsData> optionsData) {
        this.mContext = mContext;
        this.optionsData = optionsData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_options, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OptionsData data = optionsData.get(position);

        holder.txtName.setText(data.getName());
        holder.txtDetails.setText(data.getDetails());
        holder.imgViewLogo.setImageResource(data.getImageId());
    }

    @Override
    public int getItemCount() {
        return optionsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgViewLogo;
        TextView txtName, txtDetails;
        RelativeLayout relativeContainer;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgViewLogo = itemView.findViewById(R.id.imgViewLogo);
            txtName = itemView.findViewById(R.id.txtName);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            relativeContainer = itemView.findViewById(R.id.relativeContainer);

            relativeContainer.setOnClickListener(this);

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSansRegular.ttf");
            txtName.setTypeface(tf);
            txtDetails.setTypeface(tf);
        }

        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();

            if (pos == RecyclerView.NO_POSITION) return;

            Intent intent = null;

            switch (pos) {

                case 0:
                    intent = new Intent(mContext, SymptomListActivity.class);
                    break;

                case 1:
                    PreferenceUtil pref1 = new PreferenceUtil(mContext);
                    if (pref1.getUserName().isEmpty()) {
                        intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("target_job", "diagnosis");
                    } else {
                        intent = new Intent(mContext, DiagnosisActivity.class);
                    }
                    break;

                case 2:
                    PreferenceUtil pref2 = new PreferenceUtil(mContext);
                    if (pref2.getUserName().isEmpty()) {
                        intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("target_job", "prescription");
                    } else {
                        intent = new Intent(mContext, PrescriptionRequestActivity.class);
                    }
                    break;

                case 3:
                    intent = new Intent(mContext, HerbalPlantOptionActivity.class);
                    break;

                case 4:
                    intent = new Intent(mContext, BMICalculatorActivity.class);
                    break;

                case 5:
                    if (isGooglePlayServicesAvailable(mContext)) {
                        intent = new Intent(mContext, MapsActivity.class);
                    }
                    break;

                case 6:
                    intent = new Intent(mContext, FeedbackActivity.class);
                    break;

                case 7:
                    intent = new Intent(mContext, AboutAppActivity.class);
                    break;

                case 8:
                    intent = new Intent(mContext, AboutDeveloperActivity.class);
                    break;
            }

            if (intent != null) {
                mContext.startActivity(intent);
            }
        }
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);

        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}