package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;



//>>

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.HashMap;
import java.util.Map;


//>>

public class CreateSubadmins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subadmins);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

confetti();


oncreate2();

    }

    public void confetti()
    {

        final KonfettiView konfettiView = findViewById(R.id.konfettiView);

        konfettiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                konfettiView.build()
                        .addColors(Color.parseColor("#b49fdc"),Color.parseColor("#c5ebfe"), Color.parseColor("#fefd97"),Color.parseColor("#a5f8ce"),Color.parseColor("#fec9a7"),Color.parseColor("#f197c0"))
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(10, 5000L);
            }
        });
    }



    //>>

    EditText email;
    EditText name ;
    EditText pass;
    String adminstr ="",emailstr ="",coursestr= "",powerstr ="", passstr="",namestr="";

    FirebaseFirestore ff ;

    PowerSpinnerView courses;
    PowerSpinnerView powers;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //>>

    public void oncreate2()
    {
        ff = FirebaseFirestore.getInstance();


        courses  = findViewById(R.id.courses);
        powers =  findViewById(R.id.powers);
        name =  findViewById(R.id.name);
        email  = findViewById(R.id.email);
         pass = findViewById(R.id.pass);

         courses.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {

             @Override
             public void onItemSelected(int i, String s) {
                 coursestr = s;
                 tost(s);
             }
         });

        powers.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {

            @Override
            public void onItemSelected(int i, String s) {
                powerstr = s;
                tost(s);
            }
        });
    }


    public void createsubadminfun(View view) {

        namestr= (""+name.getText()).trim();
        emailstr =(""+email.getText()).trim();
        passstr = (""+pass.getText()).trim();


        if(namestr.equals("")|| emailstr.equals("") || passstr.equals("") || coursestr.equals("") || powerstr.equals(""))
        {
            tost("something is empty ! ");
        }else
        {
            Map<String,Object> hm =  new HashMap<>();

            hm.put("name",namestr);
            hm.put("password",passstr);
            hm.put("email",emailstr);
            hm.put("course",coursestr);
            hm.put("power",powerstr);


            ff.collection("SUBADMINS").document(emailstr).set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    tost("subadmin created");
                }
            });
        }
    }
}
