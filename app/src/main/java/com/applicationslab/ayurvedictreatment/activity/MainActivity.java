package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.adapter.OptionsAdapter;
import com.applicationslab.ayurvedictreatment.datamodel.OptionsData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOptions;
    private OptionsAdapter optionsAdapter;
    private ArrayList<OptionsData> optionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Safe Firebase Initialization
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(this);
        if (firebaseApps.isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        initView();
        initData();
        setAdapter();
        handleBackPressed();
    }

    private void handleBackPressed() {

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.dialog_warning, null);
                builder.setView(view);

                final AlertDialog dialog = builder.create();
                dialog.show();

                TextView txtMessage = view.findViewById(R.id.txtMessage);
                TextView txtAccept = view.findViewById(R.id.txtAccept);
                TextView txtCancel = view.findViewById(R.id.txtCancel);

                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

                txtMessage.setTypeface(tf);
                txtAccept.setTypeface(tf, Typeface.BOLD);
                txtCancel.setTypeface(tf, Typeface.BOLD);

                txtMessage.setText("Are you sure you want to exit now?");
                txtAccept.setText("EXIT");
                txtCancel.setText("CANCEL");

                txtCancel.setOnClickListener(v -> dialog.dismiss());

                txtAccept.setOnClickListener(v -> {
                    dialog.dismiss();
                    finish();
                });
            }
        });
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ayurvedic Treatment");
        }

        recyclerViewOptions = findViewById(R.id.recyclerViewOptions);
        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {
        optionsAdapter = new OptionsAdapter(this, optionsData);
        recyclerViewOptions.setAdapter(optionsAdapter);
    }

    private void initData() {

        optionsData = new ArrayList<>();

        OptionsData row;

        row = new OptionsData();
        row.setName("Symptom Checker");
        row.setDetails("Check symptoms of common diseases");
        row.setImageId(R.drawable.symptom);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("Diagnosis");
        row.setDetails("Diagnose using symptoms");
        row.setImageId(R.drawable.diagnosis);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("Prescription Request");
        row.setDetails("Request prescription from doctor");
        row.setImageId(R.drawable.prescription);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("Herbal Plant");
        row.setDetails("Herbal plants info");
        row.setImageId(R.drawable.herbal_plant);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("BMI Calculator");
        row.setDetails("Calculate BMI");
        row.setImageId(R.drawable.bmi);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("Ayurvedic Hospitals");
        row.setDetails("Find nearby hospitals");
        row.setImageId(R.drawable.maps);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("Feedback");
        row.setDetails("Give feedback");
        row.setImageId(R.drawable.feedback);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("About AT App");
        row.setDetails("About the app");
        row.setImageId(R.drawable.about_app);
        optionsData.add(row);

        row = new OptionsData();
        row.setName("About Developer");
        row.setDetails("About developer");
        row.setImageId(R.drawable.about_developer);
        optionsData.add(row);
    }

    // 🔥 SHOW PROFILE ICON
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 🔥 PROFILE DROPDOWN
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_profile) {

            // 🔥 THIS IS THE FIX (RIGHT SIDE ANCHOR)
            View anchor = findViewById(R.id.action_profile);

            PopupMenu popup = new PopupMenu(this, anchor);
            popup.getMenuInflater().inflate(R.menu.profile_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(menuItem -> {

                int id = menuItem.getItemId();

                if (id == R.id.menu_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));

                } else if (id == R.id.menu_saved) {
                    startActivity(new Intent(this, SavedPrescriptionsActivity.class));

                } else if (id == R.id.menu_logout) {

                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }

                return true;
            });

            popup.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}