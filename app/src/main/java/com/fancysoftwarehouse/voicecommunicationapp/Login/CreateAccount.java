package com.fancysoftwarehouse.voicecommunicationapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.fancysoftwarehouse.voicecommunicationapp.R;
import com.fancysoftwarehouse.voicecommunicationapp.getset.Registration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmad on 4/17/2018.
 */

public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    FirebaseAuth fbauth ;
      EditText name ,adress,school,sbjuect,schoolbord,email,pass ;
    Spinner spin;

    private ProgressDialog pd;
    List<String> grades;
    String g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
//
//        spin = (Spinner) findViewById(R.id.gradeSpinner);
//        spin.setOnItemSelectedListener(this);
//
//
//
//        grades = new ArrayList<String>();
//        grades.add("A");
//        grades.add("B");
//        grades.add("C");

//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grades);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spin.setAdapter(dataAdapter);


        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

//        HTextView  h1= (HTextView)  findViewById(R.id.h1);
        final HTextView h2= (HTextView)  findViewById(R.id.h2);





      //  final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fnt1.otf");
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
                       // h2.setTypeface(typeface);
//        h1.setAnimateType(HTextViewType.RAINBOW);
//        h1.animateText("Restaurant Order Application");
                        h2.setAnimateType(HTextViewType.LINE);
                        h2.animateText("   Sign up    ");                }
                });
            }

        };
        new Thread(runnable).start();
//
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table=firebaseDatabase.getReference("Teacher");
        fbauth =   FirebaseAuth.getInstance();

        boolean cancel = false;
        View focusView = null;


        final Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    attemptLogin();

               if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError(getString(R.string.error_field_required));

                }
//               else if (uname.getText().toString().contains("")) {
//                    uname.setError("Spaces are not allowed");
//
//                }
                else if (TextUtils.isEmpty(pass.getText().toString())) {
                       pass.setError(getString(R.string.error_field_required));

                }
                else
                {
                    try {



                        reg();
                    } catch (Exception e) {
                        e.printStackTrace( );
                    }

                }




//
//                LinearLayout layout=(LinearLayout) findViewById(R.id.l);
//
//                layout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        h2.setAnimateType(HTextViewType.TYPER);
//                        h2.animateText("Create Your Account Here");
//                    }
//                });



            }
        });

//        slider();



//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }

    private void reg() {
        loading("Authentication Checking  ");

        fbauth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).
                addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            pd.dismiss();

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

//                            final SignupClass SignupClass = new SignupClass(uname.getText().toString().trim(),pass.getText().toString(),email.getText().toString());
                            final Registration registration = new Registration(email.getText().toString(),pass.getText().toString());
                            DatabaseReference pathReference = FirebaseDatabase.getInstance().getReference().child("Registration");
                            pathReference.child(currentFirebaseUser.getUid()).setValue(registration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(CreateAccount.this, "Account successfully created", Toast.LENGTH_SHORT).show();

//                                    Intent intent = new Intent(getApplicationContext(),TracherActivity.class);
//                                    startActivity(intent);
                                }
                            });

                            Toast.makeText(CreateAccount.this, "Account is verified  ", Toast.LENGTH_SHORT).show();



                        }
                        else
                        {
                            pd.dismiss();


                            Toast.makeText(getApplicationContext(), " Authentication Failed  .. Email   not verified  ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }



    private void  loading(String a)
    {

        pd= ProgressDialog.show(CreateAccount.this, a, "Loading please wait..", true);
        pd.setCancelable(true);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        g = parent.getItemAtPosition(position).toString();

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


//    void slider(){
//        final int[] sampleImages = {R.drawable.parsleybgr2, R.drawable.parsleybgr3, R.drawable.parsleyloginbgr};
//
//
//        CarouselView carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//
//        ImageListener imageListener = new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(sampleImages[position]);
//            }
//        };
//        carouselView.setImageListener(imageListener);
//
//
//    }



}
