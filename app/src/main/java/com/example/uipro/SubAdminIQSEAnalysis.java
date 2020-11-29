package com.example.uipro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.rilixtech.materialfancybutton.MaterialFancyButton;


//>>


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Iterator;
import java.util.Map;


//>>

public class SubAdminIQSEAnalysis extends AppCompatActivity {

    LinearLayout ll ;
    GyroscopeObserver gyroscopeObserver;


    public void tost(String msg)
    {
        Toast.makeText(SubAdminIQSEAnalysis.this, ""+msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_iqseanalysis);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ll= findViewById(R.id.eventll);

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        PanoramaImageView panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        addbuttons();


        oncreate2();
    }



    String events[]={
            "Canvas","Cloud","Hadoop","Virtual Labs"
    };




    public void addbuttons()
    {
        ll.removeAllViews();

        for(int i =0;i< events.length;i++)
        {
            MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
            LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,20,0,0);
            b.setLayoutParams(lp);

            b.setBackgroundColor(Color.parseColor("#034472"));
            b.setBorderColor(Color.parseColor("#fffdd0"));
            b.setBorderWidth(2);
            b.setText(events[i]);
            b.setTextColor(Color.parseColor("#fffdd0"));
            b.setPadding(10,10,10,10);
            b.setRadius(10);
            b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
            ll.addView(b);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialFancyButton b  =  (MaterialFancyButton) v;
                    String event =  ""+(b.getText());


                    Intent i =  new Intent(getApplicationContext(),SubAdminPieCart.class);
                    i.putExtra("evname",event);
                    startActivity(i);

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }


//>>

    FirebaseFirestore ff ;

    LinearLayout llwr;


    //>>


    public  void oncreate2()
    {


        ff = FirebaseFirestore.getInstance();

        ff.collection("ADMINEVENTSTILLNOW").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                int size =   queryDocumentSnapshots.size();

                events =  new String[size];

                int it  = 0;
                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments())
                {
                    String eventname = ""+d.getId();

                    events[it++]=  eventname;

                }

                addbuttons();
            }
        });



    }
}