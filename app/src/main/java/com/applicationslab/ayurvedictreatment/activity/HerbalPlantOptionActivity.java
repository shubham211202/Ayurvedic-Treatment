package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.appdata.StaticData;
import com.applicationslab.ayurvedictreatment.datamodel.HerbalPlantsData;

import java.util.ArrayList;

public class HerbalPlantOptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHerbalPlants;
    private Button btnDiseases;

    private ArrayList<HerbalPlantsData> herbalPlantsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbal_plant_option);

        initView();
        prepareData();
        initData();
        setUIClickHandler();
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
            getSupportActionBar().setTitle("Herbal Plants");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnHerbalPlants = findViewById(R.id.btnHerbalPlants);
        btnDiseases = findViewById(R.id.btnDiseases);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        btnHerbalPlants.setTypeface(tf, Typeface.BOLD);
        btnDiseases.setTypeface(tf, Typeface.BOLD);
    }

    private void initData() {
        StaticData.setHerbalPlantsDatas(herbalPlantsData);
    }

    private void setUIClickHandler() {
        btnHerbalPlants.setOnClickListener(this);
        btnDiseases.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnHerbalPlants) {
            startActivity(new Intent(this, SearchHerbalPlantActivity.class));

        } else if (v.getId() == R.id.btnDiseases) {
            startActivity(new Intent(this, SearchDiseasesActivity.class));
        }
    }

    private void prepareData() {
        herbalPlantsData = new ArrayList<>();

        HerbalPlantsData data = new HerbalPlantsData();

        data.setName("Neem");
        data.setScientificName("Azadirachta indica");
        data.setDetails("Neem is a fast-growing medicinal tree.");
        data.setUses("Used for skin diseases and detox.");
        data.setImageId(R.drawable.neem);

        herbalPlantsData.add(data);

        data = new HerbalPlantsData();
        data.setName("Aloe Vera");
        data.setScientificName("");
        data.setDetails("Aloe vera is a succulent plant.");
        data.setUses("Used for skin and digestion.");
        data.setImageId(R.drawable.aloe_vera);

        herbalPlantsData.add(data);
    }
}