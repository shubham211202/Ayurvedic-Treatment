package com.applicationslab.ayurvedictreatment.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.adapter.DiseaseAdapter;
import com.applicationslab.ayurvedictreatment.datamodel.DiseaseSearchData;

import java.util.ArrayList;

public class SearchDiseasesActivity extends AppCompatActivity {

    RecyclerView recyclerViewDiseases;
    DiseaseAdapter diseaseAdapter;
    ArrayList<DiseaseSearchData> diseaseSearchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_diseases);

        initView();
        initData();
        setAdapter();
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
            getSupportActionBar().setTitle("Search by Diseases");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewDiseases = findViewById(R.id.recyclerViewDiseases);

        recyclerViewDiseases.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {
        diseaseAdapter = new DiseaseAdapter(this, diseaseSearchData);
        recyclerViewDiseases.setAdapter(diseaseAdapter);
    }

    private void initData() {

        diseaseSearchData = new ArrayList<>();

        addDisease("Acute Fever", 14);
        addDisease("Bronchitis", 14); // fixed spelling
        addDisease("Asthma", 14);
        addDisease("Kidney Stone", 14);
        addDisease("Children Aliment", 14);
        addDiseaseMulti("Skin Disease", 14, 1);
        addDisease("Rheumatic arthritis", 8);
        addDisease("Dysmenorrhoea", 8);
        addDiseaseMulti("Diabetes", 1, 2, 13);
        addDisease("Persistent fever", 2);
        addDiseaseMulti("Heart disorder", 7, 12, 3);
        addDisease("Eczema", 0);
        addDisease("Jaundice", 0);
        addDiseaseMulti("Gastric trouble", 11, 13);
        addDisease("Cholesterol control", 13);
        addDisease("Constipation", 13);
        addDisease("Tuberculosis", 13);
        addDisease("Gangrene", 3);
        addDisease("Fungal infection", 3);
    }

    // Helper methods (clean code)
    private void addDisease(String name, int pos) {
        DiseaseSearchData data = new DiseaseSearchData();
        data.setDiseaseName(name);

        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(pos);

        data.setPlantPositions(positions);
        diseaseSearchData.add(data);
    }

    private void addDiseaseMulti(String name, int... posArray) {
        DiseaseSearchData data = new DiseaseSearchData();
        data.setDiseaseName(name);

        ArrayList<Integer> positions = new ArrayList<>();
        for (int p : posArray) positions.add(p);

        data.setPlantPositions(positions);
        diseaseSearchData.add(data);
    }
}