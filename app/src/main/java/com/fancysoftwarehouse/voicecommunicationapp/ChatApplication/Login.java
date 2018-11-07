package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.firebase.client.Firebase;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.json.JSONException;
import org.json.JSONObject;
 
public class Login extends AppCompatActivity {
    TextView register;
    EditText username, password;
    Button loginButton;
    String user, pass;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");

       // register = (TextView)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        final HTextView h1= (HTextView)  findViewById(R.id.login);


        final HTextView h2= (HTextView)  findViewById(R.id.h1);






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
                        h2.animateText("   Sign up here   ");

                        h1.setTypeface(typeface);
                        h1.setAnimateType(HTextViewType.TYPER);
                        h1.animateText(" Not a member? Sign up");


                    }
                });
            }

        };
        new Thread(runnable).start();



        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        Firebase.setAndroidContext(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
 
                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{
                    String url = "https://voicecomunication-6e047.firebaseio.com/Regis.json";
                    final ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Loading...");
                    pd.show();
 
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);
 
                                    if(!obj.has(user)){
                                        Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)&& obj.getJSONObject(user).getString("lang").equals("english") ){
                                        UserDetails.username = user;
                                        UserDetails.password = pass;
                                        UserDetails.username_language = "English";
                                        Toast.makeText(Login.this,"Login Successfully" , Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Login.this, Users.class));
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)&& obj.getJSONObject(user).getString("lang").equals("spain") ){
                                        UserDetails.username = user;
                                        UserDetails.password = pass;
                                        UserDetails.username_language = "Spanish";
                                        Toast.makeText(Login.this, "Login Successfully" , Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, Users.class));
                                    }
                                    else {
                                        Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
 
                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });
 
                    RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                    rQueue.add(request);
                }
 
            }
        });
    }
}



