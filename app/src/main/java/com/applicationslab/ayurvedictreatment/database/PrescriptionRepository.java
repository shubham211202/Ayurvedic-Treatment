package com.applicationslab.ayurvedictreatment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PrescriptionRepository {

    private DatabaseHelper dbHelper;

    public PrescriptionRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 🔥 SAVE DATA
    public void insertPrescription(String name, String disease, String treatment, String date) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("disease", disease);
        values.put("treatment", treatment);
        values.put("date", date);

        db.insert(DatabaseHelper.TABLE_NAME, null, values);

        db.close(); // ✅ GOOD PRACTICE
    }

    // 🔥 DELETE DATA
    public void deletePrescription(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rows = db.delete(
                DatabaseHelper.TABLE_NAME,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close(); // ✅ GOOD PRACTICE

        // (Optional) you can log rows if needed
        // if (rows > 0) → delete successful
    }

    // 🔥 GET DATA
    public ArrayList<Prescription> getAllPrescriptions() {

        ArrayList<Prescription> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " ORDER BY id DESC",
                null
        );

        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String disease = cursor.getString(cursor.getColumnIndexOrThrow("disease"));
                String treatment = cursor.getString(cursor.getColumnIndexOrThrow("treatment"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                Prescription p = new Prescription(name, disease, treatment, date);
                p.setId(id); // 🔥 VERY IMPORTANT

                list.add(p);

            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close(); // ✅ GOOD PRACTICE

        return list;
    }
}