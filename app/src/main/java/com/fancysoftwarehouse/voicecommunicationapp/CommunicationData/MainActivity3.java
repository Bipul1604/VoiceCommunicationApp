package com.fancysoftwarehouse.voicecommunicationapp.CommunicationData;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.TranslatorBackgroundTask;
import com.fancysoftwarehouse.voicecommunicationapp.Message_Fragment;
import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.fancysoftwarehouse.voicecommunicationapp.ViewHolder.MainView;
import com.fancysoftwarehouse.voicecommunicationapp.getset.insert;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity3 extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    private TextToSpeech tts;
    static TextView tvTranslatedText;
    EditText etUserText;
    private static final int REQUEST_CODE = 1234;
    Spinner spin;
    Button buTranslate;
    ImageView buSpeak;
    String text;
    DatabaseReference database;
     ListView listView;
    List<insert> arrList;
      Locale spanish ;
      Locale English ; //Speaking English
    FirebaseRecyclerAdapter<insert,MainView> adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<String> langugae;
    String lang,message;
    String id="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        langugae = new ArrayList<String>();
        langugae.add("English");
        langugae.add("Spanish");

        spin = (Spinner) findViewById(R.id.selctlanguage);
        spin.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, langugae);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);



        //
        arrList = new ArrayList<>();

         tvTranslatedText = (TextView) findViewById(R.id.textView);
      //  etUserText = (EditText) findViewById(R.id.etUserText);
        buTranslate = (Button) findViewById(R.id.buTranslate);
     //   listView = (ListView) findViewById(R.id.list);


//


        //adapter = new VoiceList(this, arrList);

        buSpeak = (ImageView) findViewById(R.id.buSpeak);

        database = FirebaseDatabase.getInstance().getReference("Communication");

        buSpeak.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity();

            }
        });
      //  fect();



        buTranslate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToBeTranslated = "";
                textToBeTranslated = etUserText.getText().toString();
               // String languagePair = "en-es"; //English to bengali ("<source_language>-<target_language>")
                //Executing the translation function
                Translate(textToBeTranslated, lang);
                final Locale spanish = new Locale("es", "ES");
                tts = new TextToSpeech(MainActivity3.this, new OnInitListener() {

                    @Override
                    public void onInit(int status) {
                        // TODO Auto-generated method stub
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(spanish);
                            if (result == TextToSpeech.LANG_MISSING_DATA ||
                                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("error", "This Language is not supported");
                            } else {
                                ConvertTextToSpeech();
                            }
                        } else
                            Log.e("error", "Initilization Failed!");
                    }
                });

            }
        });

        if(getIntent() !=null)

            id=getIntent().getStringExtra("email");
    //    Toast.makeText(this, id, Toast.LENGTH_SHORT).show();



        /*You've to create a frame layout or any other layout with id inside your activity layout and then use that id in java*/

        Bundle bundle = new Bundle();
         bundle.putString("id", id );
        Message_Fragment fragment = new Message_Fragment();
        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.container, fragment);
        ft.commit();



//     android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.container, new Message_Fragment());
//        new Message_Fragment().setArguments(bundle);
//        fragmentTransaction.commit();





     }


    void Translate(String textToBeTranslated, String languagePair) {
        TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(this);

        String translationResult = "";

        translationResult = String.valueOf(translatorBackgroundTask.execute(textToBeTranslated, languagePair)); // Returns the translated text as a String
        Log.d("Translation Result", translationResult); // Logs the result in Android Monitor

    }


    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Convey your message please ");
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
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                if(!id.isEmpty())
                {

                    insertdata(id);




                }
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


    void insertdata( String i) {

        //int rating = seekBarRating.getProgress();

//     Date date = new Date();
//     Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
//     SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
//     String stringdate = dt.format(newDate);

         String idd = database.push().getKey();
        insert ins = new insert(String.valueOf(System.currentTimeMillis()), i, message);
        database.child(idd).setValue(ins);
        Toast.makeText(this, "Message is sending", Toast.LENGTH_LONG).show();


        //b
//
//    }
//
    }


    void fetch(String i) {

        database = FirebaseDatabase.getInstance().getReference();

        database.child("Communication").orderByChild("id").equalTo(i).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    insert ins = ds.getValue(insert.class); //here 'Bazar' model class

                    text= ins.getMessage();

                    String textToBeTranslated = "";
                    // String languagePair = "en-es"; //English to Spanish ("<source_language>-<target_language>")
                    //Executing the translation function
                    Translate(textToBeTranslated, lang);
                    tts = new TextToSpeech(MainActivity3.this, new OnInitListener() {

                        @Override
                        public void onInit(int status) {
                            // TODO Auto-generated method stub
                            if (status == TextToSpeech.SUCCESS) {
                                int result = tts.setLanguage(spanish);
                                if (result == TextToSpeech.LANG_MISSING_DATA ||
                                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Log.e("error", "This Language is not supported");
                                } else {
                                    ConvertTextToSpeech();
                                }
                            } else
                                Log.e("error", "Initilization Failed!");
                        }
                    });


//                    final Handler handler = new Handler();
//                    Runnable runnable = new Runnable() {
//                        private long startTime = System.currentTimeMillis();
//                        public void run() {
//                            try {
//                                Thread.sleep(5000);
//                            }
//                            catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            handler.post(new Runnable(){
//                                public void run() {
//
//                                    ConvertTextToSpeech();
//
//
//                                }
//                            });
//                        }
//
//                    };
//                    new Thread(runnable).start();
//


                 }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent,  View view, int position, long l) {


        if(parent.getItemAtPosition(position).toString() .equals("English"))
        {
lang="en-es";
         //   Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

        }
         else{
            lang="en-en";

       //     Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();
         }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

