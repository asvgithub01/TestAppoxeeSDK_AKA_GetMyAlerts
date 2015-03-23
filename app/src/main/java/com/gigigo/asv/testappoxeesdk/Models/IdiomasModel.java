package com.gigigo.asv.testappoxeesdk.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alberto on 24/02/2015.
 */
public class IdiomasModel {

    @SerializedName("idioma")
    public String idioma;
    @SerializedName("idiomaDes")
    public String idiomaDes;
    @SerializedName("urlJson")
    public String urlJson;

    public IdiomasModel() {

        idioma="";
        idiomaDes="";
        urlJson="";
    }

}
