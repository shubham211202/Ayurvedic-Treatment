package com.applicationslab.ayurvedictreatment.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applicationslab.ayurvedictreatment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName, txtEmail, txtMobile;
    Button btnLogin, btnSignup, btnLogout, btnEditProfile;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 🔥 NEW UI REFERENCES
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        txtName.setTypeface(tf, Typeface.BOLD);
        txtEmail.setTypeface(tf);
        txtMobile.setTypeface(tf);

        btnLogin.setTypeface(tf, Typeface.BOLD);
        btnSignup.setTypeface(tf, Typeface.BOLD);
        btnLogout.setTypeface(tf, Typeface.BOLD);
        btnEditProfile.setTypeface(tf, Typeface.BOLD);

        // 🔥 BUTTON ACTIONS
        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        btnSignup.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            updateUI();
        });

        btnEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
        });

        updateUI();
    }

    // 🔥 MAIN UI LOGIC
    private void updateUI() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            // ❌ NOT LOGGED IN
            txtName.setVisibility(View.GONE);
            txtEmail.setVisibility(View.GONE);
            txtMobile.setVisibility(View.GONE);
            btnEditProfile.setVisibility(View.GONE);

            btnLogin.setVisibility(View.VISIBLE);
            btnSignup.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);

        } else {
            // ✅ LOGGED IN
            txtName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);
            txtMobile.setVisibility(View.VISIBLE);
            btnEditProfile.setVisibility(View.VISIBLE);

            btnLogin.setVisibility(View.GONE);
            btnSignup.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);

            loadUserData(); // 🔥 FETCH FROM FIRESTORE
        }
    }

    // 🔥 FETCH USER DATA FROM FIRESTORE
    private void loadUserData() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        String userId = user.getUid();

        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()) {

                        String name = documentSnapshot.getString("username");
                        String email = documentSnapshot.getString("email");
                        String mobile = documentSnapshot.getString("mobile"); // may be null

                        txtName.setText("👤 " + (name != null ? name : "N/A"));
                        txtEmail.setText("📧 " + (email != null ? email : "N/A"));
                        txtMobile.setText("📱 " + (mobile != null ? mobile : "Not added"));
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
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