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

    TextView txtScientificLabel;
    TextView txtDescriptionLabel;
    TextView txtUsesLabel;
    TextView txtScientificName;
    TextView txtDescription;
    TextView txtUses;
    ImageView imgViewHerbalPlant;

    String title = "";
    String scientificName = "";
    String description = "";
    String uses = "";
    int imgId = R.drawable.herbal_plant;

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

        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtScientificName.setTypeface(tfRegular, Typeface.ITALIC);
        txtDescription.setTypeface(tfRegular);
        txtUses.setTypeface(tfRegular);
        txtScientificLabel.setTypeface(tfRegular, Typeface.BOLD);
        txtDescriptionLabel.setTypeface(tfRegular, Typeface.BOLD);
        txtUsesLabel.setTypeface(tfRegular, Typeface.BOLD);
    }

    private void initData() {
        try {
            Intent intent = getIntent();

            if (intent != null && intent.getExtras() != null) {
                title = intent.getStringExtra("title");
                scientificName = intent.getStringExtra("scientific_name");
                description = intent.getStringExtra("description");
                uses = intent.getStringExtra("uses");
                imgId = intent.getIntExtra("img_id", R.drawable.herbal_plant);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setContentData() {
        txtDescription.setText(description);
        txtUses.setText(uses);
        imgViewHerbalPlant.setImageResource(imgId);

        if (scientificName != null && !scientificName.equalsIgnoreCase("")) {
            txtScientificName.setText(scientificName);
        } else {
            txtScientificLabel.setVisibility(View.GONE);
            txtScientificName.setVisibility(View.GONE);
        }
    }
}