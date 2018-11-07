package com.fancysoftwarehouse.voicecommunicationapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancysoftwarehouse.voicecommunicationapp.R;

/**
 * Created by Ahmad on 4/20/2018.
 */

public class RegistrationView extends RecyclerView.ViewHolder implements View.OnClickListener{
//public TextView name;
public TextView name ,sbjuect,grade;
public ImageView img;
public ItemClick itemClick;


    public RegistrationView(View itemView) {

        super(itemView);

      //name=(TextView) itemView.findViewById(R.id.id);
//        sbjuect=(TextView) itemView.findViewById(R.id.tv2);
//        grade=(TextView) itemView.findViewById(R.id.tv3);
//    img=(ImageView) itemView.findViewById(R.id.menu_img);



    itemView.setOnClickListener(this);

    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View view) {
      itemClick.onClick(view,getAdapterPosition(),false);

    }


}
