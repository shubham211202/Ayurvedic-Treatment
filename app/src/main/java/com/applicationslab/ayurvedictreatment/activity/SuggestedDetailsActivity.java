package com.applicationslab.ayurvedictreatment.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.appdata.StaticData;
import com.applicationslab.ayurvedictreatment.datamodel.HerbalPlantsData;

import java.util.ArrayList;

public class SuggestedDetailsActivity extends AppCompatActivity {

    LinearLayout linearContainer;

    String title = "";
    ArrayList<Integer> plantPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_details);

        initData();
        initView();
        setAllContentData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        try {
            title = getIntent().getStringExtra("title");
            plantPositions = (ArrayList<Integer>) getIntent().getSerializableExtra("position");
        } catch (Exception ignored) {}
    }

    private void initView() {
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        linearContainer = findViewById(R.id.linearContainer);
    }

    private void setAllContentData() {

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");
        ArrayList<HerbalPlantsData> herbalPlantsData = StaticData.getHerbalPlantsDatas();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < plantPositions.size(); i++) {

            View view = inflater.inflate(R.layout.row_plant_details, linearContainer, false);

            TextView txtName = view.findViewById(R.id.txtName);
            TextView txtScientificLabel = view.findViewById(R.id.txtScientificLabel);
            TextView txtDescriptionLabel = view.findViewById(R.id.txtDescriptionLabel);
            TextView txtUsesLabel = view.findViewById(R.id.txtUsesLabel);
            TextView txtScientificName = view.findViewById(R.id.txtScientificName);
            TextView txtDescription = view.findViewById(R.id.txtDescription);
            TextView txtUses = view.findViewById(R.id.txtUses);
            ImageView img = view.findViewById(R.id.imgViewHerbalPlant);

            txtName.setTypeface(tf, Typeface.BOLD);
            txtScientificName.setTypeface(tf, Typeface.ITALIC);
            txtDescription.setTypeface(tf);
            txtUses.setTypeface(tf);
            txtScientificLabel.setTypeface(tf, Typeface.BOLD);
            txtDescriptionLabel.setTypeface(tf, Typeface.BOLD);
            txtUsesLabel.setTypeface(tf, Typeface.BOLD);

            int position = plantPositions.get(i);
            HerbalPlantsData data = herbalPlantsData.get(position);

            txtName.setText(data.getName());
            txtDescription.setText(data.getDetails());
            txtUses.setText(data.getUses());
            img.setImageResource(data.getImageId());

            if (data.getScientificName() != null && !data.getScientificName().isEmpty()) {
                txtScientificName.setText(data.getScientificName());
            } else {
                txtScientificLabel.setVisibility(View.GONE);
                txtScientificName.setVisibility(View.GONE);
            }

            linearContainer.addView(view);
        }
    }
}