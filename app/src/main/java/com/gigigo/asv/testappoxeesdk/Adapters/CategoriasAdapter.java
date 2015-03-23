package com.gigigo.asv.testappoxeesdk.Adapters;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appoxee.Appoxee;
import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Models.CategoriasModel;
import com.gigigo.asv.testappoxeesdk.R;

import java.util.ArrayList;

/**
 * Created by Alberto on 20/02/2015.
 */
public class CategoriasAdapter extends ArrayAdapter {
    private Activity context;
    public CategoriasModel[] model;
     SetTagsAsync setTagsAsync = new SetTagsAsync();

    public CategoriasAdapter(Activity context, CategoriasModel[] data) {
        super(context, R.layout.categoriaslsttemplate, data);
        this.context = context;
        this.model = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.categoriaslsttemplate, null);

        TextView lblCat = (TextView) item.findViewById(R.id.LblCat);
        TextView lblDes = (TextView) item.findViewById(R.id.DesCat);
        Switch swcCat = (Switch) item.findViewById(R.id.SwcCat);
        //asv lo ponemos en español(_ES) xq nos sale del cimbrel, ni more ni less
        lblCat.setText(model[position].CategoriaLbl);
        lblDes.setText(model[position].CategoriaDes);
        swcCat.setChecked(model[position].CategoriaON);
        swcCat.setTag(position);


        swcCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                try {
                    String lblES = (String) model[(int) v.getTag()].CategoriaLbl;
                    Toast.makeText(v.getContext(), lblES, Toast.LENGTH_LONG).show();
                    model[(int) v.getTag()].CategoriaON = isChecked;
                    setTagsAsync = new SetTagsAsync();
                    //asv ojito que el doble clik repeta!!
                    if (setTagsAsync.isReady)
                        setTagsAsync.execute(model);


                } catch (Exception e) {
                    Utils.Debug("******************************************************");
                    Utils.Debug(e.toString());
                    Utils.Debug("******************************************************");
                }
            }
        });

        return (item);
    }

}

class SetTagsAsync extends AsyncTask<CategoriasModel, Void, ListView> {
    ArrayList<String> addTags;
    ArrayList<String> delTags;
    public boolean isReady = true;

    @Override
    protected ListView doInBackground(CategoriasModel... params) {
        addTags = new ArrayList<String>();
        delTags = new ArrayList<String>();
        isReady = false;
        for (int i = 0; i < params.length - 1; i++) {
            System.out.println(params[i].CategoriaLbl);
            if (params[i].CategoriaON)
                addTags.add(0, params[i].CategoriaTAG);
            else
                delTags.add(0, params[i].CategoriaTAG);
        }

        if (delTags.size() > 0)
            Appoxee.removeTagsFromDevice(delTags);
        if (addTags.size() > 0)
            Appoxee.addTagsToDevice(addTags);
        return null;
    }


    @Override
    protected void onPostExecute(ListView result) {

        isReady = true;

    }

}
