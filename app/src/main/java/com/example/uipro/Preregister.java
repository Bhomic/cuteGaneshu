package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Preregister extends AppCompatActivity {


    ImageView poster ;



    String scoursename ="";
    String sgender = "";
    String srollno = "";
    String sphoneno ="";
    String sfname = "";



    String rollno = "";
    String name = "";

    String regcourse = "";


    Spinner coursespinner  ;
    LinearLayout availeventsll;

    EditText nameet  ;
    EditText rollnoet ;  ;


    FirebaseAuth fa ;

    TextView tv1 ;


    FirebaseFirestore ff;

    public void tost(String msg){
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preregister);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        try {


            poster = findViewById(R.id.posteriv);


            tv1 = findViewById(R.id.status);

            String ev = Preregister.this.getIntent().getExtras().getString("regevname");

            String date = Preregister.this.getIntent().getExtras().getString("date");


            if (ev.equals("")) {
                ev = "notselected";
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + ev + ".jpg");


            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Preregister.this)
                            .load(uri)
                            .into(poster);

                }
            });


            ff = FirebaseFirestore.getInstance();

            fa = FirebaseAuth.getInstance();
/*
    availeventsll = findViewById(R.id.regeventsll);

    nameet =  findViewById(R.id.regname);
        rollnoet =  findViewById(R.id.regrollno);
        coursespinner =  findViewById(R.id.coursespinner);


        String[] items = new String[]{"Cs","Ba","Bms","Stats","Geo","Maths"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        coursespinner.setAdapter(adapter);


        coursespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tv =  (TextView)view;
                String str = ""+ tv.getText();

                tost(str);

                regcourse = str;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

*/


            final String ev2 = ev;
            final String date2 = date;

            ff.collection("ALREADYREGISTERED").document("" + date).collection("EVENTS").document("" + ev).collection("PARTICIPANTS").document("" + fa.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        tv1.setText("already registered for " + ev2 + " on " + date2);
                    } else {
                        tv1.setText("Register now for " + ev2 + " on " + date2);
                    }
                }
            });

        }catch(Exception e)
        {
            tost(" error : "+e.getMessage());
        }
    }

    public void regsubmit(View view) {

      /*  rollno = ""+ rollnoet.getText();
        name = ""+ nameet.getText();

    tost(""+rollno+" name : "+name +" course : "+regcourse);



*/
        tost("email : "+fa.getCurrentUser().getEmail());

        final String curremail = ""+fa.getCurrentUser().getEmail();


        ff.collection("register").document(""+curremail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Map <String, Object> hm = documentSnapshot.getData();

                sfname = ""+hm.get("fname");
                srollno = ""+hm.get("rollno");
                scoursename = ""+hm.get("course");
                sgender = ""+hm.get("gender");
                sphoneno = ""+hm.get("phno");


                tost("data : "+sfname+srollno+scoursename+sgender+sphoneno );




                String ev  = Preregister.this.getIntent().getExtras().getString("regevname");

                final String date =  Preregister.this.getIntent().getExtras().getString("date");



                if(ev.equals(""))
                {
                    ev = "notselected";
                }




                String  email  = curremail;


                Map<String, Object > hm2 =  new HashMap<>();
                hm2.put("name : ", sfname);
                hm2.put("rollno", srollno);
                hm2.put("course", scoursename);
                hm2.put("gender",sgender);
                hm2.put("phno",sphoneno);

                final String finalEv = ev;
                ff.collection("ALREADYREGISTERED").document(""+date).collection("EVENTS").document(""+ev).collection("PARTICIPANTS").document(""+fa.getCurrentUser().getEmail()).set(hm2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tost("thanks for registration ! ");
                        poster.setImageResource(R.drawable.o);

                        tv1.setText("Registration Done ^ * Event : "+ finalEv +" dated : "+date);
                    }
                });


            }
        });




    }
}
