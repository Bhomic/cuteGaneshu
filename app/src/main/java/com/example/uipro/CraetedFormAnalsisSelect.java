package com.example.uipro;

// to select the dynamic form for analysis....

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class CraetedFormAnalsisSelect extends AppCompatActivity {


    FirebaseFirestore ff;

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craeted_form_analsis_select);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ll= findViewById(R.id.createdformselectll);

        ff =  FirebaseFirestore.getInstance();

        ff.collection("DYNFORMREZ").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                {


                    MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
                    LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,20,0,0);
                    b.setLayoutParams(lp);

                    b.setBackgroundColor(Color.parseColor("#034472"));
                    b.setBorderColor(Color.parseColor("#fffdd0"));
                    b.setBorderWidth(2);
                    b.setText(""+d.getId());
                    b.setTextColor(Color.parseColor("#fffdd0"));
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

                            Intent i=  new Intent(CraetedFormAnalsisSelect.this,WhichDynamicFormForAnalysis.class);
                            i.putExtra("formname",event);
                            startActivity(i);

                        }
                    });



                }

            }
        });

    }
}
