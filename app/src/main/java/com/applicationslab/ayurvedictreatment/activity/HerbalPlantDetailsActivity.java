package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class HerbalPlantDetailsActivity extends AppCompatActivity {

    private TextView txtScientificLabel, txtDescriptionLabel, txtUsesLabel;
    private TextView txtScientificName, txtDescription, txtUses;
    private ImageView imgViewHerbalPlant;

    private String title = "";
    private String scientificName = "";
    private String description = "";
    private String uses = "";
    private int imgId = R.drawable.herbal_plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbal_plant_details);

        initData();
        initView();
        setContentData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtScientificLabel = findViewById(R.id.txtScientificLabel);
        txtDescriptionLabel = findViewById(R.id.txtDescriptionLabel);
        txtUsesLabel = findViewById(R.id.txtUsesLabel);

        txtScientificName = findViewById(R.id.txtScientificName);
        txtDescription = findViewById(R.id.txtDescription);
        txtUses = findViewById(R.id.txtUses);

        imgViewHerbalPlant = findViewById(R.id.imgViewHerbalPlant);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtScientificName.setTypeface(tf, Typeface.ITALIC);
        txtDescription.setTypeface(tf);
        txtUses.setTypeface(tf);

        txtScientificLabel.setTypeface(tf, Typeface.BOLD);
        txtDescriptionLabel.setTypeface(tf, Typeface.BOLD);
        txtUsesLabel.setTypeface(tf, Typeface.BOLD);
    }

    private void initData() {
        try {
            Intent intent = getIntent();

            if (intent != null && intent.getExtras() != null) {
                title = intent.getExtras().getString("title", "");
                scientificName = intent.getExtras().getString("scientific_name", "");
                description = intent.getExtras().getString("description", "");
                uses = intent.getExtras().getString("uses", "");
                imgId = intent.getExtras().getInt("img_id", R.drawable.herbal_plant);
            }

        } catch (Exception ignored) {
        }
    }

    private void setContentData() {

        txtDescription.setText(description);
        txtUses.setText(uses);
        imgViewHerbalPlant.setImageResource(imgId);

        if (!scientificName.isEmpty()) {
            txtScientificName.setText(scientificName);
        } else {
            txtScientificLabel.setVisibility(View.GONE);
            txtScientificName.setVisibility(View.GONE);
        }
    }
}