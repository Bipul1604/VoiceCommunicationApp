package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.ArrayList;
import java.util.Iterator;

import static com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.UserDetails.username_language;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private long backPressedTime = 0;
     // used by onBackPressed()
    String val,getUser,getSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        noUsersText.setTypeface(typeface);


        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        final HTextView h2= (HTextView)  findViewById(R.id.h1);


        if(UserDetails.username_language!=null) {
            val = UserDetails.username_language;
            getUser = UserDetails.username;
            getSender = UserDetails.chatWith;
        }


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable(){
                    public void run() {
                        h2.setTypeface(typeface);


                        h2.setAnimateType(HTextViewType.LINE);
                        h2.animateText("  List of Registered Users  ");
                    }
                });
            }

        };
        new Thread(runnable).start();


         String url = "https://voicecomunication-6e047.firebaseio.com/Regis.json";
 
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
 
        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);


         usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);

               // Toast.makeText(Users.this, al.get(position), Toast.LENGTH_SHORT).show();
//              startActivity(new Intent(Users.this, MainActivity.class));
              startActivity(new Intent(Users.this, Chat_acitivity.class));

            }
        });

       // Toast.makeText(this, val, Toast.LENGTH_SHORT).show();
    }
 
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
 
            while(i.hasNext()){
                key = i.next().toString();

                if(username_language.equals("English")) {
                    if (!key.equals(UserDetails.username) &&obj.getJSONObject(key).getString("lang").equals("spain")) {
                        al.add(key);

                    }
                    totalUsers++;

                }
                else if(username_language.equals("Spanish")) {
                    if (!key.equals(UserDetails.username) &&obj.getJSONObject(key).getString("lang").equals("english")) {
                        al.add(key);

                    }
                    totalUsers++;

                }

            }
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
           // usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, al) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");
                    text.setTypeface(typeface);

                    text.setTextColor(Color.WHITE);
                    return view;
                }
            };

            usersList.setAdapter(adapter);

         }
 
        pd.dismiss();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if( UserDetails.username_language==null) {
            UserDetails.username_language = val;
            UserDetails.username = getUser;
            UserDetails.chatWith = getSender;
        }
        //Toast.makeText(this, username_language, Toast.LENGTH_SHORT).show();


    }


    @Override
    protected void onPause() {
        super.onPause();
        if( UserDetails.username_language==null) {
            UserDetails.username_language = val;
            UserDetails.username = getUser;
            UserDetails.chatWith = getSender;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        username_language=val;

    }

    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to logout",
                    Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            finish();
            super.onBackPressed();       // bye
        }

//    if(!username.isEmpty()){
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//    }
//    else{
//        super.onBackPressed();
//
//    }
    }
}