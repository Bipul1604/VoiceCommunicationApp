package com.fancysoftwarehouse.voicecommunicationapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.UserDetails;
import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.TranslatorBackgroundTask;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.ItemClick;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.MainView;
import com.fancysoftwarehouse.voicecommunicationapp.getset.Chat;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class Message_Fragment extends Fragment {

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

    public Message_Fragment(  ) {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)  view.findViewById(R.id.recyclerView);

        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance().getReference("messages").child(UserDetails.username+"_"+UserDetails.chatWith);
        TextView tv=(TextView)  view.findViewById(R.id.textView);
        messageArea=(EditText)  view.findViewById(R.id.messageArea);
        final Button btn=(Button)  view.findViewById(R.id.btn);



        reference1 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              btn.setOnClickListener(new View.OnClickListener() {

                  @Override
                  public void onClick(View v) {
                      startVoiceRecognitionActivity();
                  }
              });



            }
        });
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadlist();
//            }
//        });


        loadlist();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        Toast.makeText(getContext(), user, Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.activity_rycycler , container, false);


    }

    private void loadlist() {

        adapter = new FirebaseRecyclerAdapter<Chat, MainView>(Chat.class, R.layout.list_obj, MainView.class, database) {

            @Override
            protected void populateViewHolder(final MainView viewHolder, final Chat model, final int position) {


//                if(model.getUser().equals(UserDetails.username) ) {
                    viewHolder.uname.setText("User Email " + model.getUser() + "Message  " + model.getMessage());
//                    viewHolder.uname.setTextColor(Color.BLUE);
//                    viewHolder.uname.setGravity(View.FOCUS_RIGHT);
//
//                    if(model.getUser().equals(UserDetails.chatWith)){
//
//                        viewHolder.uname.setText("User Email " + model.getUser() + "Message  " + model.getMessage());
//                        viewHolder.uname.setTextColor(Color.GREEN);
//                        viewHolder.uname.setGravity(View.FOCUS_LEFT);
//                    }
//                }
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
                        Toast.makeText(getContext(),model.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });




            }


        };
        recyclerView.setAdapter(adapter);
    }


    void Translate(String textToBeTranslated, String languagePair) {
        TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(getContext());

        translationResult = "";

        translationResult = String.valueOf(translatorBackgroundTask.execute(textToBeTranslated, languagePair)); // Returns the translated text as a String
        Log.d("Translation Result", translationResult); // Logs the result in Android Monitor

    }


    private void startVoiceRecognitionActivity() {

        String l;
        if(UserDetails.username_language.equals("Spain")){
            l="es-ES";
        }
        else{
            l="en-US";

        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Convey your message please ");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, l);

        startActivityForResult(intent, REQUEST_CODE);

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


             //   UserDetails.username_language="English";



                if(UserDetails.username_language.equals("English")) {
//                    if (!message.equals("")) {
                    Translate(message, "en-es");
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            //Do something after 100ms



                            Map<String, String> map = new HashMap<String, String>();
                            map.put("message", message);
                            map.put("user", UserDetails.username);
                          //  map.put("date", UserDetails.username);

                            //reference1.push().setValue(map);
                            Map<String, String> map2 = new HashMap<String, String>() ;

                            map2.put("message", UserDetails.transltedText);
                            map2.put("user", UserDetails.username);
//                              reference2.push().setValue(map2);

                            if(UserDetails.transltedText!=null)
                            {
                                reference1.push().setValue(map);
                                reference2.push().setValue(map2);
                            }
                            else{

                                Toast.makeText(getContext(), "Please Speak loudly unable to record your voice", Toast.LENGTH_SHORT).show();
                            }




                        }
                    }, 3000);


                }
                else if(UserDetails.username_language.equals("Spain")){
                    Translate(message, "es-en");



                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            //Do something after 100ms



                            Map<String, String> map = new HashMap<String, String>();
                            map.put("message", message);
                            map.put("user", UserDetails.username);

                            //    reference1.push().setValue(map);


                            Map<String, String> map2 = new HashMap<String, String>() ;

                            map2.put("message", UserDetails.transltedText);
                            map2.put("user", UserDetails.username);
                            //  reference2.push().setValue(map2);







                            // Map<String, String> map = new HashMap<String, String>();
                            //map.put("message", UserDetails.transltedText);
                            //map.put("user", UserDetails.username);

                            if(UserDetails.transltedText!=null)
                            {
                                reference1.push().setValue(map);
                                reference2.push().setValue(map2); }
                            else{

                                Toast.makeText(getContext(), "Please Speak loudly unable to record your voice", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 5000);

                }

//                }

            }
        }
    }



}
