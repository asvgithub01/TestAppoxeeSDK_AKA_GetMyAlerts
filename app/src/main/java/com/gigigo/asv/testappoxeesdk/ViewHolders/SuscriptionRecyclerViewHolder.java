package com.gigigo.asv.testappoxeesdk.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigigo.asv.testappoxeesdk.R;
import com.gigigo.asv.testappoxeesdk.interfaces.OnMyVHlisterner;

/**
 * Created by Alberto on 11/03/2015.
 */
public class SuscriptionRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView lbl;
    ImageView imgSelect;
    FrameLayout frmLyt;
    RelativeLayout frmLytSelected;
    OnMyVHlisterner listener;

    public SuscriptionRecyclerViewHolder(View itemView) {
        super(itemView);
        lbl = (TextView) itemView.findViewById(R.id.lblCategorie);
        frmLyt = (FrameLayout) itemView.findViewById(R.id.frmBackgroud);
        frmLytSelected = (RelativeLayout) itemView.findViewById(R.id.frmSelected);
        imgSelect = (ImageView) itemView.findViewById(R.id.imgSelect);
    }

    public void setFrmLytSelected(boolean isSelected) {
        if (isSelected)
            this.frmLytSelected.setVisibility(View.VISIBLE);
        else
            this.frmLytSelected.setVisibility(View.GONE);

    }

    public void setFrmLytTag(int idx) {
        this.frmLytSelected.setTag(idx);
    }

    public void setBackground(int res) {
        this.frmLyt.setBackgroundResource(res);
    }

    public void setLabel(String lbl) {
        this.lbl.setText(lbl);
    }
/*el loading se hace a nivel de pagina*/
    public void setSelectAnimation(Animation anim) {
        this.imgSelect.startAnimation(anim);
    }

    public int getFrmLytTag() {
        int rValue = (int) this.frmLytSelected.getTag();
        return rValue;
    }

    public void SetEvents(OnMyVHlisterner l) {
        listener = l;
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = (int) v.getTag();
                listener.OnClickedAllItem(idx);
            }
        });

        this.frmLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = (int) v.getTag();
                listener.OnClickedImageItem(idx);
            }
        });


    }


}
