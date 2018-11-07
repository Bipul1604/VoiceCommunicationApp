package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fancysoftwarehouse.voicecommunicationapp.CommunicationData.MainActivity3;
import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.firebase.client.Firebase;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Register extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;
    Spinner spin;
    ArrayList<String> language;
    String text;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_reg);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");


        language = new ArrayList<String>();
        language.add("English");
        language.add("Spanish");

        spin = (Spinner) findViewById(R.id.selctlanguage);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
//        login = (TextView)findViewById(R.id.login);


        final HTextView h2= (HTextView)  findViewById(R.id.h1);
        final HTextView h1= (HTextView)  findViewById(R.id.login);


//registerButton.setTypeface(typeface);



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


                        h2.setAnimateType(HTextViewType.EVAPORATE);
                        h2.animateText("  Just Registered here   ");

                        h1.setTypeface(typeface);


                        h1.setAnimateType(HTextViewType.TYPER);
                        h1.animateText(" Already Registered ? Login Me");

                    }
                });
            }

        };
        new Thread(runnable).start();



        Firebase.setAndroidContext(this);
 
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
 
        registerButton.setOnClickListener(new View.OnClickListener() {
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
                    else if(!user.matches("[A-Za-z0-9]+")){
                            username.setError("only alphabet or number allowed");
                        }
                        else if(user.length()<5){
                                username.setError("at least 5 characters long");
                            }
                            else if(pass.length()<5){
                                password.setError("at least 5 characters long");
                            }
                            else {
                                final ProgressDialog pd = new ProgressDialog(Register.this);
                                pd.setMessage("Loading...");
                                pd.show();
 
                                String url = "https://voicecomunication-6e047.firebaseio.com/Regis.json";
 
                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String s) {
                                        Firebase reference = new Firebase("https://voicecomunication-6e047.firebaseio.com/Regis");
 
                                        if(s.equals("null")) {
                                            reference.child(user).child("password").setValue(pass);
                                            Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            try {
                                                JSONObject obj = new JSONObject(s);
 
                                                if (!obj.has(user)) {
                                                    if(!text.equals("")) {

                                                        reference.child(user).child("password").setValue(pass);
                                                        reference.child(user).child("lang").setValue(text);
                                                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                                    }else{

                                                        Toast.makeText(Register.this, "Please Select Your Language", Toast.LENGTH_SHORT).show();
                                                    }

                                                    }
                                                    else {
                                                    Toast.makeText(Register.this, "username already exists", Toast.LENGTH_LONG).show();
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
                                        System.out.println("" + volleyError );
                                        pd.dismiss();
                                    }
                                });
 
                                RequestQueue rQueue = Volley.newRequestQueue(Register.this);
                                rQueue.add(request);
                            }
            }
        });



//        tts = new TextToSpeech(Register.this, new TextToSpeech.OnInitListener() {
//
//            @Override
//            public void onInit(int status) {
//                // TODO Auto-generated method stub
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = tts.setLanguage(new Locale("es-ES"));
//                    if (result == TextToSpeech.LANG_MISSING_DATA ||
//                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("error", "This Language is not supported");
//                    } else {
//                     //  ConvertTextToSpeech();
//                    }
//                } else
//                    Log.e("error", "Initilization Failed!");
//            }
//        });


    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        text = "Langauge is successfully  converted as Spanish"+"como estas mi nombre es farooq";
        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // insertdata();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(parent.getItemAtPosition(position).toString().equals("English"))
        {
            text="english";
            //   Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

        }
        else{
            text="spain";

            //     Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}