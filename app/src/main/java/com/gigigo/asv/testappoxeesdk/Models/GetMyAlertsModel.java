package com.gigigo.asv.testappoxeesdk.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alberto on 24/02/2015.
 */
public class GetMyAlertsModel {
    @SerializedName("version")
    public String VersionApp;
    @SerializedName("idiomas")
    public IdiomasModel[] Idiomas;


    public GetMyAlertsModel() {

        VersionApp = "";
        Idiomas = new IdiomasModel[]{};

        return;

    }

}
