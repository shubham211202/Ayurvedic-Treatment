package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;

public class BmiSuggestionsActivity extends AppCompatActivity {

    private TextView txtDo, txtDoNot, txtDoLabel, txtDoNotLabel;

    private String shouldDo = "";
    private String shouldNotDo = "";
    private int bmiCase = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_suggestions);

        initData();
        setBmiSuggestions();
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
            getSupportActionBar().setTitle("Diet Suggestions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtDo = findViewById(R.id.txtDo);
        txtDoNot = findViewById(R.id.txtDoNot);
        txtDoLabel = findViewById(R.id.txtDoLabel);
        txtDoNotLabel = findViewById(R.id.txtDoNotLabel);

        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtDo.setTypeface(tfRegular);
        txtDoNot.setTypeface(tfRegular);
        txtDoLabel.setTypeface(tfRegular, Typeface.BOLD);
        txtDoNotLabel.setTypeface(tfRegular, Typeface.BOLD);
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            if (intent != null && intent.getExtras() != null) {
                bmiCase = intent.getExtras().getInt("bmi_case", 2);
            }
        } catch (Exception ignored) {
        }
    }

    private void setContentData() {
        txtDo.setText(shouldDo);
        txtDoNot.setText(shouldNotDo);
    }

    private void setBmiSuggestions() {
        switch (bmiCase) {

            case 1:
                shouldDo = ">> Eat more often\n\n" +
                        ">> Drink Milk\n\n" +
                        ">> Try Weight gainer shakes\n\n" +
                        ">> Use Bigger Plates\n\n" +
                        ">> Add cream to your coffee\n\n" +
                        ">> Take Creatine\n\n" +
                        ">> Get Quality Sleep\n\n" +
                        ">> Eat protein first and vegetables last";

                shouldNotDo = ">> Drink water before meals\n\n" +
                        ">> Smoke";
                break;

            case 2:
                shouldDo = ">> Eat whole grain cereals\n\n" +
                        ">> Include salad with meals\n\n" +
                        ">> Eat fruits between meals\n\n" +
                        ">> Use fresh Garlic and Ginger\n\n" +
                        ">> Drink at least 8 glasses of water\n\n" +
                        ">> Do regular exercise";

                shouldNotDo = ">> Excess oil in food\n\n" +
                        ">> Fast food\n\n" +
                        ">> Sweets & pastries\n\n" +
                        ">> Meat products\n\n" +
                        ">> Alcohol";
                break;

            case 3:
                shouldDo = ">> Eat regular meals\n\n" +
                        ">> Eat fruits and vegetables\n\n" +
                        ">> Drink plenty of water\n\n" +
                        ">> Eat high-fiber foods\n\n" +
                        ">> Use smaller plates\n\n" +
                        ">> Control emotional eating\n\n" +
                        ">> Reduce sugar intake";

                shouldNotDo = ">> Skip breakfast\n\n" +
                        ">> Ban foods completely\n\n" +
                        ">> Stock junk food\n\n" +
                        ">> Excess alcohol\n\n" +
                        ">> Starve yourself";
                break;
        }
    }
}