package com.gigigo.asv.testappoxeesdk.Models;

import com.gigigo.asv.testappoxeesdk.R;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alberto on 20/02/2015.
 * <p/>
 * <p/>
 * ANALIZAR SI CON LA FORMULA DE CREAR UN CONSTRUCTOR DE ESTE PALO(con los 5 campos)
 * CON VARIABLES PUBLIC y final(se setean en el constructor por lo q es similar a public readonly)
 * ASí evitamos hacer geter y seter q es lo q por rendimiento es malo, yeah?
 * <p/>
 * no MOLA Q EL MODEL NO TENGA SU PROPIO NAMESPACE O SU CARPETUCHI, LO DEJO A LO JINCHO PARA DESPUES PASARLO A mvvm
 */
public class CategoriasModel {
    @SerializedName("CategoriaLbl")
    public final String CategoriaLbl;
    @SerializedName("CategoriaDes")
    public final String CategoriaDes;
    @SerializedName("CategoriaTAG")
    public final String CategoriaTAG;
    @SerializedName("CategoriaON") //asv fuck uu
    public boolean CategoriaON;

    public CategoriasModel(String lbl, String des, String TAGKey) {
        CategoriaLbl = lbl;
        CategoriaDes = des;
        CategoriaTAG = TAGKey;
        CategoriaON = false;
    }

    public CategoriasModel(String lbl, String des, String TAGKey, boolean ison) {
        CategoriaLbl = lbl;
        CategoriaDes = des;
        CategoriaTAG = TAGKey;
        CategoriaON = ison;
    }

    public int CategoriaBackground() {

        //region
        if (this.CategoriaTAG != null &&
                this.CategoriaTAG != "") {

            switch (this.CategoriaTAG) {
                case "NOTICIAS":
                    return R.drawable.noticias;
                case "DEPORTE":
                    return R.drawable.deporte;
                case "FUTBOL":
                    return R.drawable.futbol;
                case "ECONOMIA":
                    return R.drawable.economia;
                case "VIRAL":
                    return R.drawable.viral;
                case "BOLSA":
                    return R.drawable.bolsa;
                case "HOROSCOPO":
                    return R.drawable.horoscopo;
                case "CITAS":
                    return R.drawable.citas;
                case "BUSINESS":
                    return R.drawable.business;
                case "SALUD":
                    return R.drawable.salud;
                case "VIDA":
                    return R.drawable.vida;
                case "DISRUPTIVE":
                    return R.drawable.disruptive;
                default:
                    return R.drawable.disruptive;
            }

        }
        //endregion
        return -1;
    }


}

