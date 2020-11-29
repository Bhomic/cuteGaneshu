package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import org.w3c.dom.Document;

import java.util.Map;

public class SelectEvenToSeeQR extends AppCompatActivity {

    LinearLayout  ll ;

    FirebaseFirestore ff ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_even_to_see_qr);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ll =  findViewById(R.id.ll);


    ff =  FirebaseFirestore.getInstance();

    ff.collection("ADMINEVENTSTILLNOW").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            for(DocumentSnapshot d :  queryDocumentSnapshots)
            {
                String eventname = ""+d.getId();


                Map<String,Object> hm =  d.getData();
                String pass =  ""+hm.get("qrpass");


                Mybutton mb =  new Mybutton(getApplicationContext());
                LinearLayout.LayoutParams lp0 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp0.setMargins(0,20,0,0);
                mb.setLayoutParams(lp0);
                mb.qrpass = pass;
                mb.setText(d.getId());

                mb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mybutton btn =  (Mybutton) v ;
                        String ps  = btn.qrpass;

                        Intent i=  new Intent(SelectEvenToSeeQR.this,Generateqrcode.class);
                        i.putExtra("qrpass",ps);
                        startActivity(i);
                    }
                });

                ll.addView(mb);




             /*

                MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
                LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,20,0,0);
                b.setLayoutParams(lp);

                b.setBackgroundColor(Color.parseColor("#03bbff"));
                b.setBorderColor(Color.parseColor("#ffffff"));
                b.setBorderWidth(2);
                b.setText(""+d.getId());
                b.setTextColor(Color.parseColor("#ffffff"));
                b.setPadding(10,10,10,10);
                b.setRadius(10);
                b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
                ll.addView(b);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MaterialFancyButton b = (MaterialFancyButton) v;
                        String event = "" + (b.getText());


                        // all downloading work for te event :

                        Intent i=  new Intent(SelectEvenToSeeQR.this,Generateqrcode.class);
                        i.putExtra("qrpass",event);
                        startActivity(i);

                    }
                });


              */
            }
        }
    });

    }
}
