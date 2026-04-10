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

    Button btnHerbalPlants;
    Button btnDiseases;

    ArrayList<HerbalPlantsData> herbalPlantsData;

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

        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");
        btnDiseases.setTypeface(tfRegular, Typeface.BOLD);
        btnHerbalPlants.setTypeface(tfRegular, Typeface.BOLD);
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

        HerbalPlantsData herbalPlantsRow;

        // Neem
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Neem");
        herbalPlantsRow.setScientificName("Azadirachta indica");
        herbalPlantsRow.setDetails("Neem is a fast-growing evergreen tree widely used in Ayurvedic medicine. It has strong antibacterial, antifungal, and anti-inflammatory properties. Neem is commonly used to treat skin diseases, improve immunity, and purify blood.");
        herbalPlantsRow.setUses(">> Treats skin infections\n>> Boosts immunity\n>> Purifies blood");
        herbalPlantsRow.setImageId(R.drawable.neem);
        herbalPlantsData.add(herbalPlantsRow);

        // Aloe Vera
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Aloe Vera");
        herbalPlantsRow.setScientificName("Aloe barbadensis miller");
        herbalPlantsRow.setDetails("Aloe Vera is a succulent plant known for its healing and soothing properties. Its gel is widely used for treating burns, improving digestion, and enhancing skin health.");
        herbalPlantsRow.setUses(">> Heals burns\n>> Improves digestion\n>> Skincare");
        herbalPlantsRow.setImageId(R.drawable.aloe_vera);
        herbalPlantsData.add(herbalPlantsRow);

        // Bitter Gourd
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Bitter Gourd");
        herbalPlantsRow.setScientificName("Momordica charantia");
        herbalPlantsRow.setDetails("Bitter Gourd is a medicinal vegetable known for its ability to control blood sugar levels. It is widely used in traditional medicine for diabetes and digestion.");
        herbalPlantsRow.setUses(">> Controls diabetes\n>> Improves digestion\n>> Detoxifies blood");
        herbalPlantsRow.setImageId(R.drawable.bitter_gourd);
        herbalPlantsData.add(herbalPlantsRow);

        // Garlic
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Garlic");
        herbalPlantsRow.setScientificName("Allium sativum");
        herbalPlantsRow.setDetails("Garlic is a powerful medicinal herb known for boosting immunity and improving heart health. It also helps reduce blood pressure and cholesterol.");
        herbalPlantsRow.setUses(">> Boosts immunity\n>> Controls BP\n>> Heart health");
        herbalPlantsRow.setImageId(R.drawable.garlic);
        herbalPlantsData.add(herbalPlantsRow);

        // Black Cohosh
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Black Cohosh");
        herbalPlantsRow.setScientificName("Actaea racemosa");
        herbalPlantsRow.setDetails("Black Cohosh is used mainly for women's health issues, especially menopause symptoms. It also reduces inflammation and supports hormonal balance.");
        herbalPlantsRow.setUses(">> Hormonal balance\n>> Menopause relief\n>> Anti-inflammatory");
        herbalPlantsRow.setImageId(R.drawable.black_cohos);
        herbalPlantsData.add(herbalPlantsRow);

        // Bitter Leaf
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Bitter Leaf");
        herbalPlantsRow.setScientificName("Vernonia amygdalina");
        herbalPlantsRow.setDetails("Bitter Leaf is widely used for detoxification and treatment of fever and infections. It supports digestion and overall body cleansing.");
        herbalPlantsRow.setUses(">> Treats fever\n>> Detoxifies body\n>> Improves digestion");
        herbalPlantsRow.setImageId(R.drawable.bitter_leaf);
        herbalPlantsData.add(herbalPlantsRow);

        // Amla
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Amla");
        herbalPlantsRow.setScientificName("Phyllanthus emblica");
        herbalPlantsRow.setDetails("Amla is rich in Vitamin C and antioxidants. It helps boost immunity, improve digestion, and enhance hair and skin health.");
        herbalPlantsRow.setUses(">> Boosts immunity\n>> Improves digestion\n>> Hair growth");
        herbalPlantsRow.setImageId(R.drawable.amla);
        herbalPlantsData.add(herbalPlantsRow);

        // Arjun
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Arjun");
        herbalPlantsRow.setScientificName("Terminalia arjuna");
        herbalPlantsRow.setDetails("Arjun tree bark is widely used for heart health. It strengthens heart muscles and helps regulate blood pressure.");
        herbalPlantsRow.setUses(">> Heart health\n>> Controls BP\n>> Strengthens heart");
        herbalPlantsRow.setImageId(R.drawable.arjun);
        herbalPlantsData.add(herbalPlantsRow);

        // Ashok
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Ashok");
        herbalPlantsRow.setScientificName("Saraca asoca");
        herbalPlantsRow.setDetails("Ashok is known for treating women's health issues, especially menstrual disorders. It improves reproductive health.");
        herbalPlantsRow.setUses(">> Menstrual health\n>> Reproductive support");
        herbalPlantsRow.setImageId(R.drawable.herbal_plant);
        herbalPlantsData.add(herbalPlantsRow);

        // Bashok
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Bashok");
        herbalPlantsRow.setScientificName("Adhatoda vasica");
        herbalPlantsRow.setDetails("Bashok is commonly used for respiratory problems like cough, asthma, and mucus-related issues.");
        herbalPlantsRow.setUses(">> Treats cough\n>> Clears mucus\n>> Asthma relief");
        herbalPlantsRow.setImageId(R.drawable.bashok);
        herbalPlantsData.add(herbalPlantsRow);

        // Thankuni Pata
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Thankuni Pata");
        herbalPlantsRow.setScientificName("Centella asiatica");
        herbalPlantsRow.setDetails("Thankuni Pata improves brain function and memory. It also helps in digestion and wound healing.");
        herbalPlantsRow.setUses(">> Improves memory\n>> Digestive health\n>> Wound healing");
        herbalPlantsRow.setImageId(R.drawable.thankuni_pata);
        herbalPlantsData.add(herbalPlantsRow);

        // Swarna Lota
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Swarna Lota");
        herbalPlantsRow.setScientificName("Caesalpinia pulcherrima");
        herbalPlantsRow.setDetails("Swarna Lota is used for treating digestive issues and improving gut health.");
        herbalPlantsRow.setUses(">> Gastric relief\n>> Improves digestion");
        herbalPlantsRow.setImageId(R.drawable.swarna_lata);
        herbalPlantsData.add(herbalPlantsRow);

        // Tith Baegun
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Tith Baegun");
        herbalPlantsRow.setScientificName("Solanum indicum");
        herbalPlantsRow.setDetails("Tith Baegun is used in traditional medicine for fever, cough, and digestive problems.");
        herbalPlantsRow.setUses(">> Fever treatment\n>> Improves appetite\n>> Digestive aid");
        herbalPlantsRow.setImageId(R.drawable.tith_begun);
        herbalPlantsData.add(herbalPlantsRow);

        // Beal
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Beal");
        herbalPlantsRow.setScientificName("Aegle marmelos");
        herbalPlantsRow.setDetails("Beal fruit is known for treating digestive disorders and improving gut health.");
        herbalPlantsRow.setUses(">> Treats ulcers\n>> Improves digestion\n>> Reduces cholesterol");
        herbalPlantsRow.setImageId(R.drawable.beal);
        herbalPlantsData.add(herbalPlantsRow);

        // Tulsi
        herbalPlantsRow = new HerbalPlantsData();
        herbalPlantsRow.setName("Tulsi");
        herbalPlantsRow.setScientificName("Ocimum sanctum");
        herbalPlantsRow.setDetails("Tulsi is a sacred plant in Ayurveda known for boosting immunity, reducing stress, and treating cold and cough.");
        herbalPlantsRow.setUses(">> Boosts immunity\n>> Treats cold\n>> Reduces stress");
        herbalPlantsRow.setImageId(R.drawable.tulsi);
        herbalPlantsData.add(herbalPlantsRow);
    }
}