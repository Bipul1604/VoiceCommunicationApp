package com.fancysoftwarehouse.voicecommunicationapp.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


 import com.fancysoftwarehouse.voicecommunicationapp.R;

/**
 * Created by Ahmad on 4/20/2018.
 */

public class MainView extends RecyclerView.ViewHolder implements View.OnClickListener{
//public TextView name;
public TextView uname,message,date ,sbjuect,grade;
public LinearLayout layout;
//public Image img;
 public ImageView img;
public ItemClick itemClick;


    public MainView(View itemView) {

        super(itemView);

      uname=(TextView) itemView.findViewById(R.id.uname);
      layout=(LinearLayout) itemView.findViewById(R.id.l);
     // img=(ImageView) itemView.findViewById(R.id.message);
        message=(TextView) itemView.findViewById(R.id.message);
        date=(TextView) itemView.findViewById(R.id.date);
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
