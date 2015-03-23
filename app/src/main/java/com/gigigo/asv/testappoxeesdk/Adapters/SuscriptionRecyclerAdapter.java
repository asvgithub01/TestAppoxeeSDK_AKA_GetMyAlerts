package com.gigigo.asv.testappoxeesdk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gigigo.asv.testappoxeesdk.Models.CategoriasModel;
import com.gigigo.asv.testappoxeesdk.R;
import com.gigigo.asv.testappoxeesdk.ViewHolders.SuscriptionRecyclerViewHolder;
import com.gigigo.asv.testappoxeesdk.interfaces.OnMyVHlisterner;

import java.util.Random;

/**
 * Created by Alberto on 11/03/2015.
 */


public class SuscriptionRecyclerAdapter extends RecyclerView.Adapter<SuscriptionRecyclerViewHolder> {
    private Context mContext;
    public CategoriasModel[] mModel;
    public View.OnClickListener onClickListener;
    //   public View.OnClickListener onClickListenerIMAGE;
    public OnMyVHlisterner listener;

    // SetTagsAsync setTagsAsync = new SetTagsAsync();
    public void setOnMyVHlisterner(OnMyVHlisterner l) {
        listener = l;
    }


    public SuscriptionRecyclerAdapter(Context context, CategoriasModel[] data) {
        this.mModel = data;
        this.mContext = context;
    }

    public void setData(CategoriasModel[] data) {
        this.mModel = data;
        //   super.notifyAll();
        super.notifyDataSetChanged();
        // super.notifyItemChanged(data.length-1);
    }


    @Override
    public SuscriptionRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.item_recyclersus, viewGroup, false);

        return new SuscriptionRecyclerViewHolder(item);
    }

    int lasti = -1;

    @Override
    public void onBindViewHolder(final SuscriptionRecyclerViewHolder customViewHolder, int i) {

    //region animacion
        Random rnd = new Random();
        int res = 0;

        while (res == lasti || res==0) res = rnd.nextInt(5);

        switch (res) {
            case 1:
                res = R.anim.top2down;
                break;
            case 2:
                res = R.anim.down2top;
                break;
            case 3:
                res = R.anim.left2rigth;
                break;
            case 4:
                res = R.anim.rigth2left;
                break;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, res);
        customViewHolder.itemView.startAnimation(animation);
        lasti = res;
    //endregion

        customViewHolder.setLabel(mModel[i].CategoriaLbl);
        customViewHolder.setBackground(mModel[i].CategoriaBackground());
        customViewHolder.setFrmLytSelected(mModel[i].CategoriaON);
        customViewHolder.setFrmLytTag(i);

        customViewHolder.itemView.setTag(i);
        if (onClickListener != null)
            customViewHolder.itemView.setOnClickListener(onClickListener);

        if (listener != null) {
            customViewHolder.SetEvents(new OnMyVHlisterner() {
                @Override
                public void OnClickedAllItem(int idx) {
                    listener.OnClickedAllItem(idx);
                }

                @Override
                public void OnClickedImageItem(int idx) {
                    listener.OnClickedImageItem(idx);
                }
            });


        }

//        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int idx =(int) v.getTag();
//                mModel[idx].CategoriaON=!mModel[idx].CategoriaON;
//                SuscriptionRecyclerAdapter.super.notifyItemChanged(idx);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mModel.length;
    }
}
