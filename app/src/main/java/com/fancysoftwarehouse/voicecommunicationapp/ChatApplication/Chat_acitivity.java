package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.ItemClick;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.MainView;
import com.fancysoftwarehouse.voicecommunicationapp.getset.Chat;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */

public class Chat_acitivity extends AppCompatActivity  {
    FirebaseRecyclerAdapter<Chat, MainView> adapter;
    private static final int REQUEST_CODE = 1234;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference database ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String user ;
    LinearLayout layout;
    ImageView sendButton,mic;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    private TextToSpeech tts;
    String text;
    Locale spanish ;
    String lang,message;
    String translationResult ;
    Locale language;
    ProgressDialog pd;
    LinearLayout linearLayout;
    String user_name, chat_with ,lng;

    String val,getUser,getSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rycycler);
        recyclerView=(RecyclerView)   findViewById(R.id.recyclerView);
        linearLayout=(LinearLayout)   findViewById(R.id.layout1);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance().getReference("messages").child(UserDetails.username+"_"+UserDetails.chatWith);
        TextView tv=(TextView)  findViewById(R.id.textView);
        messageArea=(EditText)   findViewById(R.id.messageArea);
        final Button btn=(Button)  findViewById(R.id.btn);

        final HTextView h2= (HTextView)  findViewById(R.id.h1);
         final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");

        if (!Internet_Check.getInstance(getApplicationContext()).isOnline()) {

          //  Toast.makeText(getApplicationContext(), "Ooops! No WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make( linearLayout, "Ooops! No WiFi/Mobile Networks Connected!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


//registerButton.setTypeface(typeface);

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("uname", UserDetails.username); // Storing string
//        editor.putString("with", UserDetails.chatWith); // Storing string
//        editor.putString("lang", UserDetails.username_language); // Storing string
//        editor.commit();




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
                        //h2.animateText(UserDetails.username_language+"  Mode");
                       h2.animateText(UserDetails.username_language+"  Mode");



                    }
                });
            }

        };
        new Thread(runnable).start();



//        UserDetails.username="farooq1";
//        UserDetails.chatWith="hafizumair";

        if(UserDetails.username_language.equals("English")) {
            Locale language = new Locale("en", "US");

        }
        else
            {
                language = new Locale("es", "ES");

        }


        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        Firebase.setAndroidContext(this);

        reference1 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

     loadlist();
if(UserDetails.username_language!=null) {
    val = UserDetails.username_language;
    getUser = UserDetails.username;
    getSender = UserDetails.chatWith;
}
    }
    private void loadlist() {


        adapter = new FirebaseRecyclerAdapter<Chat, MainView>(Chat.class, R.layout.list_obj, MainView.class, database) {

            @Override
            protected void populateViewHolder(final MainView viewHolder, final Chat model, final int position) {

                final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");

                 viewHolder.uname.setTypeface(typeface);

                if(model.getUser().equals(UserDetails.username) ) {
                   // viewHolder.uname.setText("You --:"+ model.getUser() + "Message  " + model.getMessage());
//                    viewHolder.uname.setText("You --:" + model.getUser()  + "--: " + model.getMessage());
                    viewHolder.uname.setText("  You  :");

                    viewHolder.uname.setTextColor(Color.BLACK);
                 //   viewHolder.uname.setGravity(View.FOCUS_RIGHT);
                     viewHolder.date.setText(model.getDate());
                    viewHolder.date.setTextColor(Color.BLACK);
                    viewHolder.message.setTextColor(Color.BLACK);



                }
                else
                {

                    viewHolder.uname.setText("  User  : " + model.getUser() );
                    viewHolder.uname.setTextColor(Color.WHITE);
                     viewHolder.date.setText(model.getDate());


                    //  viewHolder.uname.setGravity(View.FOCUS_LEFT);
                  //  viewHolder.layout.setGravity(View.FOCUS_RIGHT);
                     viewHolder.layout.setBackgroundColor(Color.DKGRAY);
                     viewHolder.date.setTextColor(Color.WHITE);
                     viewHolder.message.setTextColor(Color.WHITE);


                }
                //viewHolder.message.setText("   " + model.getMessage());
//                viewHolder.sbjuect.setText("Teacher Subject   :   "   + model.getSbjuect());
//                viewHolder.grade.setText("Teacher Grade   :   "   + model.getGrade());


                viewHolder.setItemClick(new ItemClick() {
                    @Override
                    public void onClick(View v, int Position, boolean isLongClick) {

                        if(viewHolder.message.getText().toString().contains("Play")){
                            viewHolder.message.setText("Pause");
                         //   viewHolder.message.setBackground(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                            viewHolder.message.setBackground(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                            tts = new TextToSpeech(Chat_acitivity.this, new TextToSpeech.OnInitListener() {

                                @Override
                                public void onInit(int status) {
                                    // TODO Auto-generated method stub
                                    if (status == TextToSpeech.SUCCESS) {
                                        int result = tts.setLanguage(language);
                                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Log.e("error", "This Language is not supported");
                                        } else {
                                            ConvertTextToSpeech(model.getMessage());
                                        }
                                    } else
                                        Log.e("error", "Initilization Failed!");
                                }
                            });

                        }
                        else{

                            viewHolder.message.setText("Play");
                            viewHolder.message.setBackground(getResources().getDrawable(R.drawable.ic_play_circle_filled_black_24dp));


                            tts.stop();
                        }

//                        Intent  i = new Intent(MainActivity.this,MainActivity3.class);
//                        i.putExtra("email",adapter.getRef(position).getKey());
//                        //  fetch(adapter.getRef(position).getKey());
//                        startActivity(i);
                     //   Toast.makeText(getApplicationContext(),model.getMessage() , Toast.LENGTH_SHORT).show();
                       // ConvertTextToSpeech(model.getMessage());


                      //  final Locale spanish = new Locale("es", "ES");

                    }
                });




            }


        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

if( UserDetails.username_language==null) {
    UserDetails.username_language = val;
    UserDetails.username = getUser;
    UserDetails.chatWith = getSender;
}
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   Toast.makeText(this, "Pasue", Toast.LENGTH_SHORT).show();
        if( UserDetails.username_language==null) {
            UserDetails.username_language = val;
            UserDetails.username = getUser;
            UserDetails.chatWith = getSender;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    //    Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

    }

    void Translate(String textToBeTranslated, String languagePair) {
        TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(Chat_acitivity.this);

        translationResult = "";

        translationResult = String.valueOf(translatorBackgroundTask.execute(textToBeTranslated, languagePair)); // Returns the translated text as a String
        Log.d("Translation Result", translationResult); // Logs the result in Android Monitor

    }


    private void startVoiceRecognitionActivity() {

        String l;
        if(UserDetails.username_language.equals("Spanish")){
            l="es-ES";
        }
        else{
            l="en-US";

        }

        try{

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Convey your message please ");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, l);


        startActivityForResult(intent, REQUEST_CODE);

        } catch(ActivityNotFoundException e) {
            Intent your_browser_intent = new Intent(Intent.ACTION_VIEW,

                    Uri.parse("https://market.android.com/details?id=com.google.android.googlequicksearchbox"));
            startActivity(your_browser_intent);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                //pull all of the matches
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String topResult = matches.get(0);



                //  EditText AutoText = (EditText) findViewById(R.id.etUserText);
                // AutoText.setText(topResult);
                message=topResult;
//                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                //   messageArea.setText(message);
                //  String messageText = messageArea.getText().toString();

                if(message.equals(""))
                {
                    Toast.makeText(this, "Speak Again", Toast.LENGTH_SHORT).show();
                }else {
//                    DateFormat format = new SimpleDateFormat("YYYY-MM-d:hh_mm a");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //String date = format.format(new Date());

                    long d = System.currentTimeMillis();
                    final String date = format.format(new Date(d));

                    pd= ProgressDialog.show(Chat_acitivity.this, "Sending message ", "Loading please wait..", true);
                    pd.setCancelable(true);
                    if(UserDetails.username_language.equals("English")) {
//                    if (!message.equals("")) {
                        Translate(message, "en-es");
                        new Handler().postDelayed(new Runnable() {

                            public void run() {
                                //Do something after 100ms


                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("message", message);
                                map.put("user", UserDetails.username);
                                map.put("date", date);

                                //reference1.push().setValue(map);
                                Map<String, Object> map2 = new HashMap<String, Object>() ;

                                map2.put("message", UserDetails.transltedText);
                                map2.put("user", UserDetails.username);
                                map2.put("date", date);

//                              reference2.push().setValue(map2);
//                                Toast.makeText(Chat_acitivity.this,  UserDetails.transltedText, Toast.LENGTH_SHORT).show();
                                if(UserDetails.transltedText!=null)
                                {

                                    Toast.makeText(Chat_acitivity.this, "Message sent succesfully", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    reference1.push().setValue(map);
                                    reference2.push().setValue(map2);//
                                     }
                                else{

                                Toast.makeText(Chat_acitivity.this, "Error in translation", Toast.LENGTH_SHORT).show();
                                }




                            }
                        }, 3000);


                    }
                    else if(UserDetails.username_language.equals("Spanish")){
                        Translate(message, "es-en");



                        new Handler().postDelayed(new Runnable() {

                            public void run() {
                                //Do something after 100ms



                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("message", message);
                                map.put("user", UserDetails.username);
                                map.put("date",date);

                                //    reference1.push().setValue(map);


                                Map<String, Object> map2 = new HashMap<String, Object>() ;

                                map2.put("message", UserDetails.transltedText);
                                map2.put("user", UserDetails.username);
                                map2.put("date",date);

                                //  reference2.push().setValue(map2);
//                                Toast.makeText(Chat_acitivity.this, UserDetails.transltedText, Toast.LENGTH_SHORT).show();
                                if(UserDetails.transltedText!=null)
                                {
//
 Toast.makeText(Chat_acitivity.this, "Mensaje enviado con éxito", Toast.LENGTH_SHORT).show();

                                    pd.dismiss();
                                    reference1.push().setValue(map);
                                    reference2.push().setValue(map2);
                                }
                                else{
                                    pd.dismiss();


                                    Toast.makeText(Chat_acitivity.this, "Error en la traducción", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 5000);

                    }
                    // Ending the message section
                }



//                }

            }
        }
    }


    private void ConvertTextToSpeech(String s) {
        // TODO Auto-generated method stub
         text = s.toString();

        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // insertdata();
    }
//    @Override
//    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//    }
}

