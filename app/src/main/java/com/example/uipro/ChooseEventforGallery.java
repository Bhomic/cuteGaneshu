package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class ChooseEventforGallery extends AppCompatActivity {


    FirebaseFirestore  ff;

    LinearLayout ll ;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_eventfor_gallery);

        try {
            ll = findViewById(R.id.ll);

            ff = FirebaseFirestore.getInstance();
            ff.collection("gallery").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                        String event = "" + d.getId();


                        MaterialFancyButton b = new MaterialFancyButton(getApplicationContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, 20, 0, 0);
                        b.setLayoutParams(lp);

                        b.setBackgroundColor(Color.parseColor("#034472"));
                        b.setBorderColor(Color.parseColor("#fffdd0"));
                        b.setBorderWidth(2);
                        b.setText(event);
                        b.setTextColor(Color.parseColor("#fffdd0"));
                        b.setPadding(10, 10, 10, 10);
                        b.setRadius(10);
                        b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
                        ll.addView(b);

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MaterialFancyButton b = (MaterialFancyButton) v;

                                String event = "" + (b.getText());

                                Intent i = new Intent(ChooseEventforGallery.this, Gallery.class);

                                i.putExtra("event", event);

                                startActivity(i);


                            }
                        });

                    }
                }
            });
        }
        catch(Exception e)
        {
           tost(" error : "+e.getMessage());
        }
    }
}
