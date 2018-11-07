package com.fancysoftwarehouse.voicecommunicationapp.CommunicationData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

 import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.ItemClick;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.MainView;
import com.fancysoftwarehouse.voicecommunicationapp.getset.Chat;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    FirebaseRecyclerAdapter<Chat,MainView> adapter;
    FirebaseListAdapter<Chat> adapter2;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;
    Firebase reference1, reference2;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayout l=(LinearLayout) findViewById(R.id.container);
        l.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              }
        });

//        bundle.putString("user", UserDetails.chatWith );
//        Message_Fragment fragment = new Message_Fragment();
//        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
////        fragment.setArguments(bundle);
//        ft.add(R.id.container, fragment);
//        ft.commit();


    }

    private void loadlist() {

        adapter = new FirebaseRecyclerAdapter<Chat, MainView>(Chat.class, R.layout.list_obj, MainView.class, database) {

            @Override
            protected void populateViewHolder(final MainView viewHolder, final Chat model, final int position) {


                viewHolder.uname.setText("User Email " + model.getUser()   + "Message  "+  model.getMessage());
                //viewHolder.message.setText("   " + model.getMessage());
//                viewHolder.sbjuect.setText("Teacher Subject   :   "   + model.getSbjuect());
//                viewHolder.grade.setText("Teacher Grade   :   "   + model.getGrade());


                viewHolder.setItemClick(new ItemClick() {
                    @Override
                    public void onClick(View v, int Position, boolean isLongClick) {


//                        Intent  i = new Intent(MainActivity.this,MainActivity3.class);
//                        i.putExtra("email",adapter.getRef(position).getKey());
//                        //  fetch(adapter.getRef(position).getKey());
//                        startActivity(i);
                        Toast.makeText(MainActivity.this,model.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });




            }


        };
        recyclerView.setAdapter(adapter);
    }


}

