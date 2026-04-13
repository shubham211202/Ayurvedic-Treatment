package com.applicationslab.ayurvedictreatment.database;

public class Prescription {

    int id; // 🔥 ADD THIS

    String name, disease, treatment, date;

    // 🔥 NEW CONSTRUCTOR (WITH DATE)
    public Prescription(String name, String disease, String treatment, String date) {
        this.name = name;
        this.disease = disease;
        this.treatment = treatment;
        this.date = date;
    }

    // 🔥 OPTIONAL OLD CONSTRUCTOR
    public Prescription(String name, String disease, String treatment) {
        this.name = name;
        this.disease = disease;
        this.treatment = treatment;
    }

    // 🔥 GETTERS
    public int getId() { return id; } // ✅ ADD THIS
    public String getName() { return name; }
    public String getDisease() { return disease; }
    public String getTreatment() { return treatment; }
    public String getDate() { return date; }

    // 🔥 SETTERS
    public void setId(int id) { this.id = id; } // ✅ ADD THIS
    public void setDate(String date) { this.date = date; }
}