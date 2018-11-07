package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton,mic;
    TextView messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    private TextToSpeech tts;
    String text;
    Locale spanish ;
    String lang,message;
    private static final int REQUEST_CODE = 1234;
    String translationResult ;
    public static TextView textView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
 
        layout = (LinearLayout)findViewById(R.id.layout1);
       // sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (TextView)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        mic = (ImageView)findViewById(R.id.mic);
        pd= ProgressDialog.show(Chat.this, "Sending message ", "Loading please wait..", true);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://voicecomunication-6e047.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
 
//        s\\\\endButton.setOnClickListener(new View.OnClickListener() {
//         \\\\    @Override
//         \\\\    public void onClick(View v) {
//       \\\\
//         \\\\                 }
//        }\\\\);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
 
                if(userName.equals(UserDetails.username)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            }
 
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
 
            }
 
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
 
            }
 
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
 
            }
 
            @Override
            public void onCancelled(FirebaseError firebaseError) {
 
            }
        });
    }
 
    public void addMessageBox(String message, int type){
          textView = new TextView(Chat.this);
        textView.setText(message);
                final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");

        textView.setTypeface(typeface);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 20);
        textView.setLayoutParams(lp);






           

         if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
            textView.setTextColor(Color.RED);


               textView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                                   Toast.makeText(getBaseContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                   }
               });



        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
                        textView.setTextColor(Color.BLUE);
//                        textView.setTextSt
                      textView.setGravity(View.FOCUS_LEFT);

        }
 
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    void Translate(String textToBeTranslated, String languagePair) {
        TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(Chat.this);

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






                if(UserDetails.username_language.equals("English")) {
//                    if (!message.equals("")) {
                      Translate(message, "en-es");
                      new Handler().postDelayed(new Runnable() {

                      public void run() {
                               //Do something after 100ms



                              Map<String, String> map = new HashMap<String, String>();
                              map.put("message", message);
                              map.put("user", UserDetails.username);

                              //reference1.push().setValue(map);
                                Map<String, String> map2 = new HashMap<String, String>() ;

                                  map2.put("message", UserDetails.transltedText);
                                  map2.put("user", UserDetails.username);
//                              reference2.push().setValue(map2);

                                            if(UserDetails.transltedText!=null)
                                            {
                                    reference1.push().setValue(map);
                                    reference2.push().setValue(map2); }
                                    else{

                                                Toast.makeText(Chat.this, "Please Speak loudly unable to record your voice", Toast.LENGTH_SHORT).show();
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

                                                      Toast.makeText(Chat.this, "Please Speak loudly unable to record your voice", Toast.LENGTH_SHORT).show();
                                                  }
                                                         }
                                                                }, 5000);

                    }

//                }

            }
        }
    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        //text = tvTranslatedText.getText().toString();
        if (text == null || "".equals(text)) {
            text = "Content not available";
             tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // insertdata();
    }

    void lastnode(){



    }

}
