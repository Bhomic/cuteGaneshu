package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.hanks.htextview.rainbow.RainbowTextView;



//>>>


import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.HashMap;
import java.util.Map;

//>>
public class CreateUserAccount extends AppCompatActivity {

    String gen;
  TextView rbt  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_account);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rbt = findViewById(R.id.rbt);

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);
        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }
            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });

        oncreate2();


    }

    public void alreadyhaveaccountfun(View view) {
        //rbt.animateText("Login Here");
        //rbt.animate();

        rbt.setText("Login Here");

        startActivity(new Intent(CreateUserAccount.this,UserLogin.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    rbt.setText("Already have Account ? ");
    //rbt.animate();
    }



    //>>

    int pasit =0;

    String srollno ;
    String sphno ;


    RadioGroup gender  ;
    String genstr = "";

    String course = "";


    EditText memail , mpassword , mname ;
    Button  mregister ;
    TextView mlogin ;

    String userid ;


    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button register ;

    PowerSpinnerView dropdown;

    PowerSpinnerView gendersp;

    public void oncreate2()
    {

      //  gender  = findViewById(R.id.gender);

     //   rpro = findViewById(R.id.vprogress);

        db = FirebaseFirestore.getInstance();

        memail = findViewById(R.id.vemail);
        mpassword = findViewById(R.id.vpassword);
        mname  = findViewById(R.id.vfullname);
//       mphno = findViewById(R.id.vphoneno);

        mAuth = FirebaseAuth.getInstance();





        update1();

    }


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }


    public void update1()
    {


        dropdown = findViewById(R.id.course);

        gendersp = findViewById(R.id.gender);
        /*   String[] items = new String[]{"Cs","Ba","Bms","Stats","Geo","Maths"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);


      */
dropdown.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {

    @Override
    public void onItemSelected(int i, String o) {
 tost(o);

        course = o;
    }
});


       gendersp.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {

            @Override
            public void onItemSelected(int i, String o) {
                tost(o);

                gen = o;
            }
        });



    }

    public void Createaccountbtn(View view) {

        try {

            int no = 0 ;

            genstr = gen;

            if (genstr.equals("")) {
                tost("plz enter the gender ");

                no =1;
            }


            if (course.equals("")) {
                tost("plz enter the course ");

                no =1;
            }

            TextView memail = findViewById(R.id.vemail);

            TextView mpassword = findViewById(R.id.vpassword);

            TextView mname = findViewById(R.id.vfullname);

            //  TextView mphno = findViewById(R.id.vphoneno);


            final String email = memail.getText().toString().trim();

            String passwd = mpassword.getText().toString().trim();

            final String fname = mname.getText().toString();

            EditText rnet = findViewById(R.id.rollno);
            EditText phnoet = findViewById(R.id.phoneno);

            srollno = "" + rnet.getText();
            sphno = "" + phnoet.getText();

            //final String phno = mphno.getText().toString();

            FirebaseAuth mAuth = FirebaseAuth.getInstance();


            Toast.makeText(getApplicationContext(), "" + email + " " + passwd, Toast.LENGTH_LONG).show();


            if (email.length() != 0 && passwd.length() != 0 && srollno.length() != 0 && sphno.length() != 0) {


                if (TextUtils.isEmpty(email)) {
                    no   = 1;
                    memail.setError("Email is Required");
                }

                if (TextUtils.isEmpty(passwd)) {
                    no = 1;
                    mpassword.setError("Password is Required");
                }
                if (TextUtils.isEmpty(srollno)) {
                    no = 1;
                    rnet.setError("roll No  is Required");
                }
                if (TextUtils.isEmpty(sphno)) {
                    no  = 1;
                    phnoet.setError("Phone no  is Required");
                }

                if (passwd.length() < 6) {
                    no =  1;
                    mpassword.setError("Password must be atleast 6 characters in length ");
                }

                if( no == 0) {

                    mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //  tost("User created ");

                                // userid  = mAuth.getCurrentUser().getUid();


                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();

                                Map<String, Object> user = new HashMap<>();

                                user.put("fname", fname);
                                //     user.put("phno",phno);
                                user.put("email", email);
                                user.put("gender", genstr);
                                user.put("course", course);

                                user.put("rollno", srollno);
                                user.put("phno", sphno);

                                user.put("allowed", "yes");


                                db2.collection("register")
                                        .document(email).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                tost("firestore data added for email : " + email);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        tost("failed to add data on firestore ");
                                    }
                                });


                                // otp processs for the user


                                tost("only registered till now now otp .. ");

                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            tost("registered successfully now see ur email for verification ");
                                            // memail.setText("");
                                            // mpassword.setText("");
                                        } else {
                                            tost(" registration failed " + task.getException().getMessage());
                                        }
                                    }
                                });


                                //      startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                                //    finish();

                            } else {

                                tost("Error " + task.getException().getMessage());

                            }
                        }
                    });
                }
                else
                {
                    tost("Something went wrong");
                }
            } else {
                tost("Some field is still empty !  ");
            }

        }catch(Exception e)
        {
            tost("error: "+e);
        }

    }


    //>>
}
