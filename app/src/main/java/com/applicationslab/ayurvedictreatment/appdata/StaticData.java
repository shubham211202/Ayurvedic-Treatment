package com.applicationslab.ayurvedictreatment.appdata;

import com.applicationslab.ayurvedictreatment.datamodel.HerbalPlantsData;

import java.util.ArrayList;

/**
 * Created by Shubham Kumar and Sneha Babaladi on 03/03/2026.
 */
public class StaticData {

    public static ArrayList<HerbalPlantsData> herbalPlantsDatas;

    public static ArrayList<HerbalPlantsData> getHerbalPlantsDatas() {
        return herbalPlantsDatas;
    }

    public static void setHerbalPlantsDatas(ArrayList<HerbalPlantsData> herbalPlantsDatas) {
        StaticData.herbalPlantsDatas = herbalPlantsDatas;
    }

}
