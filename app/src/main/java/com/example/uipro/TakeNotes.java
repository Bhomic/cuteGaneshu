package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class TakeNotes extends AppCompatActivity {


    DrawNoteDatabase dndb;
    LinearLayout drawll;


    LinearLayout ll;

NoteDatabase ndb  ;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_notes);

        ll =  findViewById(R.id.ll);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        dndb=  new DrawNoteDatabase(TakeNotes.this);

        ndb =  new NoteDatabase(TakeNotes.this);

drawll = findViewById(R.id.drawnotell);

getdrawnotes();

        getnotes();
    }


    public void getnotes()
    {
        Cursor c = ndb.getallnotes();

        if(c.getCount()>0 ) {


            c.moveToFirst();

            if (c.getCount() > 0) {
                do {
                    String noteid = c.getString(0);

                    MaterialFancyButton b = new MaterialFancyButton(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 20, 0, 0);
                    b.setLayoutParams(lp);

                    b.setBackgroundColor(Color.parseColor("#034472"));
                    b.setBorderColor(Color.parseColor("#fffdd0"));
                    b.setBorderWidth(2);
                    b.setText(noteid);
                    b.setTextColor(Color.parseColor("#fffdd0"));
                    b.setPadding(10, 10, 10, 10);
                    b.setRadius(10);
                    b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
                    ll.addView(b);

                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialFancyButton b = (MaterialFancyButton) v;

                            String notename = "" + b.getText();

                            Intent i = new Intent(TakeNotes.this, Createnote.class);

                            i.putExtra("task", "edit");
                            i.putExtra("noteid", notename);

                            startActivity(i);
                        }
                    });

                } while (c.moveToNext());
            }
        }
    }

    public void createnewnotefun(View view) {


        Intent i =  new Intent(TakeNotes.this,Createnote.class);

        i.putExtra("task","new");
        i.putExtra("noteid","novalue");
        startActivity(i);

    }

    public void getdrawnotes()
    {
queryalldraw();
    }

    public void queryalldraw()
    {
        Cursor c= dndb.getallimgs();

if(c.getCount()>0) {


    c.moveToFirst();

    String str = "";
    do {
        str += "" + c.getString(0);
        str += "" + c.getBlob(1);


        String noteid = c.getString(0);

        MaterialFancyButton b = new MaterialFancyButton(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        b.setLayoutParams(lp);

        b.setBackgroundColor(Color.parseColor("#fffdd0"));
        b.setBorderColor(Color.parseColor("#ffffff"));
        b.setBorderWidth(2);
        b.setText(noteid);
        b.setTextColor(Color.parseColor("#034472"));
        b.setPadding(10, 10, 10, 10);
        b.setRadius(10);
        b.setFocusBackgroundColor(Color.parseColor("#034472"));


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialFancyButton b = (MaterialFancyButton) v;

                String notename = "" + b.getText();

                Intent i = new Intent(TakeNotes.this, DrawNoteView.class);

                i.putExtra("task", "view");
                i.putExtra("noteid", notename);

                startActivity(i);
            }
        });
        drawll.addView(b);

    }
    while (c.moveToNext());

    tost(str);
}
    }

    public void createdrawnotefun(View view) {

        Intent i =  new Intent(TakeNotes.this,DrawNote.class );

        startActivity(i);
    }


    public void refreshlist()
    {
        ll.removeAllViews();
        drawll.removeAllViews();

        getnotes();
        queryalldraw();
    }

    @Override
    protected void onResume() {
        super.onResume();

    refreshlist();
    }
}
