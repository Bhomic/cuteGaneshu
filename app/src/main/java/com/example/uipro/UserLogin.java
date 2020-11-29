package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.waynell.library.DropAnimationView;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;


public class UserLogin extends AppCompatActivity {

//>>
    int  v =0;
    Button mloginbtn;

    EditText mlgnemail, mlgnpasswd;

    TextView mcreate;

    FirebaseAuth mAuth;

    //>>

    TextView rtv  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        rtv  = findViewById(R.id.rtv);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    fallingleaves();


    oncreate2();

//>>

        //>>

    }


    public void fallingleaves()
    {



        DropAnimationView view = (DropAnimationView) findViewById(R.id.drop_animation_view);
        view.setDrawables(R.drawable.leaf_1,
                R.drawable.leaf_2,
                R.drawable.leaf_3,
                R.drawable.leaf_4,
                R.drawable.leaf_5,
                R.drawable.leaf_6);

        view.startAnimation();




    }



    public  void createaccountfun(View v)
    {
        TextView rtv  =  (TextView)v;

        rtv.setText("Lets create an Account ");
//        rtv.animate();


        startActivity(new Intent(getApplicationContext(),CreateUserAccount.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        rtv.setText("Create Account");
  //      rtv.animate();


    }


    public void oncreate2() {

        mAuth = FirebaseAuth.getInstance();


        mlgnemail = findViewById(R.id.lemail);
        mlgnpasswd = findViewById(R.id.lpassword);

        mlgnpasswd.setTransformationMethod(new PasswordTransformationMethod());

        mlgnpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v % 2 == 0) {
                    mlgnpasswd.setTransformationMethod(null);
                } else {
                    mlgnpasswd.setTransformationMethod(new PasswordTransformationMethod());
                }
                v++;
            }
        });


        if (mAuth.getCurrentUser() != null) {


           if( mAuth.getCurrentUser().isEmailVerified())
           {

String email = ""+mAuth.getCurrentUser().getEmail();


               FirebaseFirestore ff =  FirebaseFirestore.getInstance();

               ff.collection("register").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {

                       if (documentSnapshot != null && documentSnapshot.exists())
                       {

                           Map<String, Object> hm = documentSnapshot.getData();

                           String allowed  =  ""+hm.get("allowed");

                           if(allowed.equals("yes"))
                           {

                               startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                               finish();
                           }
                       }
                   }
               });
           }
        }
    }
        public void tost(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }
        public void loginfun(View view) {

        final   String email = mlgnemail.getText().toString().trim();
        final   String passwd = mlgnpasswd.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            mlgnemail.setError("Email is Required");
        }

        if (TextUtils.isEmpty(passwd)) {
            mlgnpasswd.setError("Password is Reuired");
        }

        if (passwd.length() < 6) {
            mlgnpasswd.setError("Password must be atleast 6 characters in length ");
        }


      //  findViewById(R.id.lprogress).setVisibility(View.VISIBLE);


        if (mlgnemail.length() != 0 && mlgnpasswd.length() != 0) {
            mAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                       // the user is registered now the otp verification is required..


                        if (mAuth.getCurrentUser().isEmailVerified())
                        {

                            FirebaseFirestore ff =  FirebaseFirestore.getInstance();

                            ff.collection("register").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    if (documentSnapshot != null && documentSnapshot.exists())
                                    {

                                        Map<String, Object> hm = documentSnapshot.getData();

                                        String allowed  =  ""+hm.get("allowed");

                                        if(allowed.equals("yes"))
                                        {

                                            Intent i = new Intent(getApplicationContext(), StudentDashboard.class);


                                          //  i.putExtra("imail",email);
                                          //  i.putExtra("ipasswd",passwd);

                                            startActivity(i);
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    tost("no email found in database ");
                                }
                            });

                        }
                        else
                        {
                            tost(" email not verified through otp");
                        }


                        //tost("Logged In  ");




                    } else {
                        tost("Error Signing In : " + task.getException().getMessage());

                     //   findViewById(R.id.lprogress).setVisibility(View.INVISIBLE);

                    }

                }
            });
        }
    }



    }









