package com.fancysoftwarehouse.voicecommunicationapp.CommunicationData;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.fancysoftwarehouse.voicecommunicationapp.getset.insert;

import java.util.List;

public class VoiceList extends ArrayAdapter<insert> {
    private Activity context;
    private List<insert> arrList;

    public VoiceList(Activity context, List<insert>arrList){
        super(context, R.layout.list_obj,arrList);
        this.context=context;
        this.arrList=arrList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View ListViewItem= inflater.inflate(R.layout.list_obj,null,true);
     //   TextView textviewdate =(TextView) ListViewItem.findViewById(R.id.textviewdate);
//        TextView textviewcost=(TextView) ListViewItem.findViewById(R.id.textviewcost);
//        TextView texviewname=(TextView) ListViewItem.findViewById(R.id.textviewname1);

        insert ins = arrList.get(position);

      //  textviewdate.setText(ins.getId());
//        textviewcost.setText(bazar.getCost());
//        texviewname.setText(bazar.getName());
        return ListViewItem;
    }
}