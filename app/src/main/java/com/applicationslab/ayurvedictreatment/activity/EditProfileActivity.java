package com.applicationslab.ayurvedictreatment.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.widget.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtName, edtMobile;
    Button btnSave;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtName = findViewById(R.id.edtName);
        edtMobile = findViewById(R.id.edtMobile);
        btnSave = findViewById(R.id.btnSave);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        edtName.setTypeface(tf);
        edtMobile.setTypeface(tf);
        btnSave.setTypeface(tf, Typeface.BOLD);

        loadExistingData();

        btnSave.setOnClickListener(v -> updateProfile());
    }

    // 🔥 LOAD EXISTING DATA
    private void loadExistingData() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        String userId = user.getUid();

        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        edtName.setText(doc.getString("username"));
                        edtMobile.setText(doc.getString("mobile"));
                    }
                });
    }

    // 🔥 UPDATE FIRESTORE
    private void updateProfile() {

        String name = edtName.getText().toString().trim();
        String mobile = edtMobile.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            new CustomToast(this, "Enter name", "", false);
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Updating...");
        dialog.setCancelable(false);
        dialog.show();

        String userId = user.getUid();

        db.collection("users").document(userId)
                .update(
                        "username", name,
                        "mobile", mobile
                )
                .addOnSuccessListener(unused -> {
                    dialog.dismiss();
                    new CustomToast(this, "Profile updated ✅", "", true);
                    finish();
                })
                .addOnFailureListener(e -> {
                    dialog.dismiss();
                    new CustomToast(this, "Error: " + e.getMessage(), "", false);
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}