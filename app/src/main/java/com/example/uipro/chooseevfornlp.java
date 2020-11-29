package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.materialfancybutton.MaterialFancyButton;


public class chooseevfornlp extends AppCompatActivity {


    FirebaseFirestore ff;


    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseevfornlp);


        ll = findViewById(R.id.ll);



        ff= FirebaseFirestore.getInstance();

        getevents();
    }

public void getevents()
{
    ff.collection("NLPETEV").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments())
                    {
                        String evname = ""+d.getId();

                        // creating a fancy button ...


                        MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
                        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0,20,0,0);
                        b.setLayoutParams(lp);

                        b.setBackgroundColor(Color.parseColor("#fffdd0"));
                        b.setBorderColor(Color.parseColor("#ffffff"));
                        b.setBorderWidth(2);
                        b.setText(evname);
                        b.setTextColor(Color.parseColor("#034472"));
                        b.setPadding(10,10,10,10);
                        b.setRadius(10);
                        b.setFocusBackgroundColor(Color.parseColor("#034472"));


                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MaterialFancyButton b = (MaterialFancyButton) v;

                                String evname = ""+b.getText();

                                Intent i =  new Intent(chooseevfornlp.this,NLPAnalysis.class);

                                i.putExtra("evname",evname);

                                startActivity(i);
                            }
                        });
                        ll.addView(b);


                    }
                }
            });
}

}