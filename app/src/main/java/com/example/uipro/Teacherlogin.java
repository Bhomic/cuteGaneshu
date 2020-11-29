package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;


//>>

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.DocumentViewChange;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//>>
public class Teacherlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

oncreate2();


    }

    //>>

    int  suballow  =0;

    EditText em , ps;

    Button tlgn;

    boolean allowed = false;


    String ids = "";


    String datas = "";

    int a =0;

    FirebaseFirestore ff ;

    private String TAG ="Tg";


    //>>

    public void oncreate2()
    {
        ff=  FirebaseFirestore.getInstance();


        em  = findViewById(R.id.temail);
        ps = findViewById(R.id.tpassword);

        ps.setTransformationMethod(new PasswordTransformationMethod());

        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a%2==0)
                {
                    ps.setTransformationMethod(null);
                }else
                {
                    ps.setTransformationMethod(new PasswordTransformationMethod());

                }
                a++;
            }
        });

    }




    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void teacherloginfun(View view) {

        allowed  = false;

        final String email = (""+em.getText()).trim();
        final  String psw = (""+ps.getText()).trim();


        if(email.equals(""))
        {
            em.setError("email must not be empty !" );
        }

        if(psw.equals(""))
        {
            ps.setError("Password mst not be empty !" );
        }
        else {

            ff.collection("SUPERADMIN").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {

                        String cemail = "" + d.get("email");
                        String cpassword = "" + d.get("password");

                        //  tost("comparing : "+cemail+" and "+cpassword+ " width "+email+ " and "+psw );

                        if (cemail.equals(email.trim()) && cpassword.equals(psw)) {
                            allowed = true;
                        }

                    }


                    if (allowed == true) {
                        startActivity(new Intent(getApplicationContext(), TeacherDasboard.class));
                    } else {

                        // trying to match with sub admins ...

                        ff.collection("SUBADMINS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

tost("found subadmin list");
                                int found2  = 0;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments())
                                {
                                    String subadmin  = ""+d.getId();

                                    Map<String ,Object>  hm  =  d.getData();
                                    Iterator it  = hm.entrySet().iterator();

                                    subadmin = ""+hm.get("email");
                                    String password = ""+hm.get("password");

                                    if(subadmin.equals(email) && password.equals(psw))
                                    {
                                        found2=1;
                                    }
                                 //   tost(d.getId());
                                }

                                if(found2 == 0 )
                                {
                                    tost("subadmin also not found ");
                                }else
                                {
                                    startActivity(new Intent(getApplicationContext(),SubAdmin.class));

                                    tost("found as subadmin");
                                }
                            }
                        });



                        tost("try again");
                    }

                }
            });

        }


    }

}
