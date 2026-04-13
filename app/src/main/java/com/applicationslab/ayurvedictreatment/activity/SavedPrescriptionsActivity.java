package com.applicationslab.ayurvedictreatment.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.database.Prescription;
import com.applicationslab.ayurvedictreatment.database.PrescriptionRepository;
import com.applicationslab.ayurvedictreatment.adapter.PrescriptionAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SavedPrescriptionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PrescriptionAdapter adapter;
    private ArrayList<Prescription> list;
    private PrescriptionRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_prescriptions);

        // Toolbar setup
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Saved Prescriptions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Init repo
        repo = new PrescriptionRepository(this);

        // Fetch data
        list = repo.getAllPrescriptions();

        if (list == null) {
            list = new ArrayList<>();
        }

        // Adapter
        adapter = new PrescriptionAdapter(list);
        recyclerView.setAdapter(adapter);

        // 🔥 SWIPE WITH UNDO
        ItemTouchHelper.SimpleCallback swipeCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getAdapterPosition();
                        Prescription deletedItem = list.get(position);

                        // 🔥 REMOVE FROM UI FIRST
                        list.remove(position);
                        adapter.notifyItemRemoved(position);

                        // 🔥 SHOW SNACKBAR (10 sec)
                        Snackbar snackbar = Snackbar.make(recyclerView,
                                "Deleted 🗑️", Snackbar.LENGTH_LONG);

                        snackbar.setDuration(10000); // 🔥 10 seconds

                        snackbar.setAction("UNDO", v -> {
                            // 🔥 RESTORE ITEM
                            list.add(position, deletedItem);
                            adapter.notifyItemInserted(position);
                        });

                        snackbar.addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {

                                // 🔥 IF NOT UNDO → DELETE FROM DB
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                    repo.deletePrescription(deletedItem.getId());
                                }
                            }
                        });

                        snackbar.show();
                    }
                };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }

    // 🔥 BACK BUTTON
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}