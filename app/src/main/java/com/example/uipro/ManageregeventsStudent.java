package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.pdf.parser.Line;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ManageregeventsStudent extends AppCompatActivity {


    int eventno  =0 ;

    int imgid =  5000;

    int etid  = 1000;


    TextView datetxt ;

    int idit = 10;

    String datestr;


    Bitmap bm ;

    FirebaseFirestore ff;

    EditText evname;

    String evnamestr;

    LinearLayout ll;


    public  void tost(String msg) {

        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();
    }

    public void createv(View v)
    {



        TextView crnev =  findViewById(R.id.createnewevent);


        crnev.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                final ImageView poster =  new ImageView(ManageregeventsStudent.this);

                poster.setId(etid+3000);

                LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 200);
                poster.setLayoutParams(lp);



                poster.setImageResource(R.drawable.events);



                LinearLayout ll =  findViewById(R.id.svll);

                ll.addView(poster);

                evname=  new EditText(ManageregeventsStudent.this);
                evname.setId(etid);
                ViewGroup.LayoutParams etlp =  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                evname.setLayoutParams(etlp);


                ll.addView(evname);



                Button b  =  new Button(getApplicationContext());
                b.setId(etid+100);
                b.setLayoutParams(etlp);
                b.setText(" Create event  ");
                b.setTextColor(Color.parseColor("#03bbff"));

                etid++;


                ll.addView(b);
                b.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v) {


                        @SuppressLint("ResourceType")
                        ImageView imageView   = findViewById(v.getId()+ 3000 - 100);

                        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

                        @SuppressLint("ResourceType") String Eventname  = ""+((EditText)findViewById(v.getId()- 100)).getText();

                        StorageReference regevref =  mStorageRef.child(""+Eventname+".jpg");

                        tost(""+imageView);


                        if(imageView!= null) {


                            imageView.setDrawingCacheEnabled(true);
                            imageView.buildDrawingCache();
                            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            tost("" + data);
                            if (data != null) {
                                UploadTask uploadTask = regevref.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        tost("image uploaded ! ");
                                    }
                                });
                            }

                        }

                        @SuppressLint("ResourceType")
                        EditText et  =  findViewById(v.getId() - 100);

                        evnamestr =  ""+et.getText();

                        if(!evnamestr.equals(""))
                        {


                            tost(""+evnamestr);

                            ff.collection("Createdregistrationevents").document(datestr).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {


                                    Map<String, Object> newev  = new HashMap<>();

                                    int found  = 0;
                                    if(documentSnapshot!= null && documentSnapshot.exists())
                                    {
                                        Map<String, Object> oldev  = documentSnapshot.getData();

                                        Iterator it = oldev.entrySet().iterator();

                                        while(it.hasNext())
                                        {

                                            Map.Entry el = (Map.Entry) it.next();
                                            newev.put("regeventno"+(++eventno), el.getValue());

                                            if((""+evnamestr).equals(""+el.getValue()))
                                            {
                                                found  =  1;
                                            }

                                        }

                                    }


                                    if(found ==0)
                                    {
                                        newev.put("regeventno"+(++eventno),evnamestr);
                                    }else {
                                        tost(" already same name event present ! ");
                                    }

                                    ff.collection("Createdregistrationevents" ).document(""+datestr).set(newev).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            refreshlist();
                                        }
                                    });






                                }
                            });
                        }else
                        {
                            tost("please enter the event name * ^");
                        }
                    }
                });


            }
        });


    }








    public void refreshlist()
    {

        try {


            eventno = 0;

            ll.removeAllViews();

            ff.collection("Createdregistrationevents").document(datestr).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot != null && documentSnapshot.exists()) {


                        Map<String, Object> hm = documentSnapshot.getData();

                        Iterator it = hm.entrySet().iterator();

                        while (it.hasNext()) {
                            Map.Entry el = (Map.Entry) it.next();
                            String regevname = "" + el.getValue();

                            final ImageView poster = new ImageView(ManageregeventsStudent.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                            lp.setMargins(0,40,0,0);
                            poster.setLayoutParams(lp);

                            poster.setImageResource(R.drawable.events);

                            FirebaseStorage storage = FirebaseStorage.getInstance();

                            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + regevname + ".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    Glide.with(ManageregeventsStudent.this)
                                            .load(uri)
                                            .into(poster);

                                }
                            });


                            poster.setId(imgid);
                            ll.addView(poster);

                            ViewGroup.LayoutParams blp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            TextView tv = new TextView(ManageregeventsStudent.this);
                            tv.setLayoutParams(blp);
                            tv.setText(regevname);
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            tv.setTextColor(Color.parseColor("#03bbff"));
                            ll.addView(tv);


                            //>>

                            MaterialFancyButton b2 =  new MaterialFancyButton(getApplicationContext());
                            LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(0,20,0,0);
                            b2.setLayoutParams(lp2);

                            b2.setBackgroundColor(Color.parseColor("#034472"));
                            b2.setBorderColor(Color.parseColor("#fffdd0"));
                            b2.setBorderWidth(2);
                            b2.setText(""+regevname);
                            b2.setTextColor(Color.parseColor("#fffdd0"));
                            b2.setPadding(10,10,10,10);
                            b2.setRadius(10);
                            b2.setFocusBackgroundColor(Color.parseColor("#ffffff"));


                            b2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    MaterialFancyButton b  =  (MaterialFancyButton) v;
                                    String event =  ""+(b.getText());



                                    Intent i = new Intent(ManageregeventsStudent.this, Preregister.class);
                                    i.putExtra("regevname", event);
                                    i.putExtra("date", datestr);
                                    startActivity(i);



                                }
                            });


                            //>>

                        /*

                            Button b = new Button(getApplicationContext());
                            b.setId(imgid + 100);
                            b.setLayoutParams(blp);
                            b.setText("" + regevname);
                            b.setTextColor(Color.parseColor("#03bbff"));

                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.regbtn);
                            img.setBounds(0, 0, 100, 50);
                            b.setCompoundDrawables(img, null, null, null);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final String txt = "" + ((TextView) view).getText();


                                    Intent i = new Intent(ManageregeventsStudent.this, Preregister.class);
                                    i.putExtra("regevname", txt);
                                    i.putExtra("date", datestr);
                                    startActivity(i);



                               ff.collection( "Createdregistrationevents").document(datestr).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot != null && documentSnapshot.exists())
                                        {
                                            Map<String ,Object> hm =  documentSnapshot.getData();

                                            Map<String,Object> dalmap =  new HashMap<>();

                                            Iterator it =  hm.entrySet().iterator();

                                            while (it.hasNext())
                                            {
                                                Map.Entry el =  (Map.Entry)it.next();

                                                if(! txt.equals(el.getValue()))
                                                {
                                                    dalmap.put(""+el.getKey(), el.getValue());
                                                }

                                            }

                                            ff.collection( "Createdregistrationevents").document(datestr).set(dalmap);

                                            StorageReference photoref =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://cuteganesha-a4db4.appspot.com/"+txt+".jpg");

                                            photoref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    tost("image deleted ^ *");

                                                    refreshlist();
                                                }
                                            });
                                        }




                                    }
                                });



                                }
                            });
*/


                            imgid++;

                            ll.addView(b2);


                        }


                    }
                }
            });


        }catch(Exception e)
        {
            tost(" error : "+e.getMessage());

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageregevents_student);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        datetxt  = findViewById(R.id.datetxt);

        ll  =  findViewById(R.id.svll);


        ff = FirebaseFirestore.getInstance();

        datestr  =  "2020/01/5";
        datestr = this.getIntent().getExtras().getString("date");

        datetxt.setText(""+datestr);

        refreshlist();

    }


}

