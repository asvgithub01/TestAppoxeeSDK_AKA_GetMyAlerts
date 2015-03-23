package com.gigigo.asv.testappoxeesdk.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Models.RequestModel;
import com.gigigo.asv.testappoxeesdk.R;

import java.util.ArrayList;

/**
 * Created by Alberto on 20/02/2015.
 */
public class RequestJsonAdapter extends ArrayAdapter {
    private Activity context;
    public RequestModel[] model;
    public ArrayList<String> SelectedRequest = new ArrayList<String>();



    public RequestJsonAdapter(Activity context, RequestModel[] data) {
        super(context, R.layout.requestjsontemplate, data );
        this.context = context;
        this.model = data;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.requestjsontemplate, null);

        TextView lblRes = (TextView) item.findViewById(R.id.LblRequest);
        CheckBox chkRes = (CheckBox) item.findViewById(R.id.ChkRequests);

        lblRes.setText(model[position].name);
        chkRes.setTag(position);
       // SelectedRequest = new ArrayList<String>();

        chkRes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                try {
                    if (isChecked)
                        SelectedRequest.add((String) model[(int) v.getTag()].name);
                    else
                        SelectedRequest.remove((String) model[(int) v.getTag()].name);

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



