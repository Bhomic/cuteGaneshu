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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Manageregevents extends AppCompatActivity {


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

                final ImageView poster =  new ImageView(Manageregevents.this);

                poster.setId(etid+3000);

                LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 200);
                poster.setLayoutParams(lp);



                poster.setImageResource(R.drawable.thank);

                poster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i  =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        tost(""+view.getId());

                        startActivityForResult(i, ((ImageView)view).getId());
                    }
                });


                LinearLayout ll =  findViewById(R.id.svll);

                ll.addView(poster);

                evname=  new EditText(Manageregevents.this);
                evname.setId(etid);
                ViewGroup.LayoutParams etlp =  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                evname.setLayoutParams(etlp);
                   evname.setTextColor(Color.parseColor("#fffdd0"));

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

   sendnotif("New Event : "+evnamestr+" created !","Hurry .. Register Early ");
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




    public void getimage(View view) {

        Intent i  =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 500);

        tost("getting image  ");


    }



    @Override
    protected void onActivityResult(int rq  , int rs , Intent data)
    {
        super.onActivityResult(rq, rs, data);

        if(  rs == RESULT_OK && null!=  data)
        {
            Uri selimg = data.getData();

            String[] filepath = {MediaStore.Images.Media.DATA};

            Cursor c  = getContentResolver().query(selimg, filepath, null,null,null);

            c.moveToFirst();

            int ci =  c.getColumnIndex(filepath[0]);


            String picpath =  c.getString(ci);

            c.close();

            if(picpath!= null) {
                ImageView img  = (ImageView)findViewById(rq);


                if(img!= null)
                    img.setImageBitmap(BitmapFactory.decodeFile(picpath));
                else
                    tost(" imageview not found ! ");


                bm =  BitmapFactory.decodeFile(picpath);



            }

        }
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

                            final ImageView poster = new ImageView(Manageregevents.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                            poster.setLayoutParams(lp);

                            poster.setImageResource(R.drawable.events);

                            FirebaseStorage storage = FirebaseStorage.getInstance();



                            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + regevname + ".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    Glide.with(Manageregevents.this)
                                            .load(uri)
                                            .into(poster);

                                }
                            });


                            poster.setId(imgid);
                            ll.addView(poster);

                            ViewGroup.LayoutParams blp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            TextView tv = new TextView(Manageregevents.this);
                            tv.setLayoutParams(blp);
                            tv.setText(regevname);
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            tv.setTextColor(Color.parseColor("#03bbff"));
                            ll.addView(tv);

                            Button b = new Button(getApplicationContext());
                            b.setId(imgid + 100);
                            b.setLayoutParams(blp);
                            b.setText("" + regevname);
                            b.setTextColor(Color.RED);

                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.tr);
                            img.setBounds(0, 0, 50, 50);
                            b.setCompoundDrawables(img, null, null, null);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final String txt = "" + ((TextView) view).getText();

                                    ff.collection("Createdregistrationevents").document(datestr).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                                Map<String, Object> hm = documentSnapshot.getData();

                                                Map<String, Object> dalmap = new HashMap<>();

                                                Iterator it = hm.entrySet().iterator();

                                                while (it.hasNext()) {
                                                    Map.Entry el = (Map.Entry) it.next();

                                                    if (!txt.equals(el.getValue())) {
                                                        dalmap.put("" + el.getKey(), el.getValue());
                                                    }

                                                }

                                                ff.collection("Createdregistrationevents").document(datestr).set(dalmap);

                                                StorageReference photoref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + txt + ".jpg");

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

                            imgid++;

                            ll.addView(b);


                        }


                    }
                }
            });


        }catch(Exception e)
        {
            tost("error : "+e.getMessage());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageregevents);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        datetxt  = findViewById(R.id.datetxt);

        ll  =  findViewById(R.id.svll);


        ff = FirebaseFirestore.getInstance();

        datestr  =  "2020/01/5";
        datestr = this.getIntent().getExtras().getString("date");

        datetxt.setText(""+datestr);

        refreshlist();

    }



    //------------------



    RequestQueue rq;

    String url = "https://fcm.googleapis.com/fcm/send";

    public void sendnotif(String titlestr , String bodystr)
    {

        rq =  Volley.newRequestQueue(Manageregevents.this);
        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });


        if(titlestr.equals("") || bodystr.equals("") )
        {
            tost("title and body of notification not found ! ");
        }
        else {
            JSONObject mainobj = new JSONObject();

            try {

                mainobj.put("to", "/topics/" + "news");
                JSONObject notifobj = new JSONObject();

                notifobj.put("title", ""+titlestr);
                notifobj.put("body", ""+bodystr);

                mainobj.put("notification", notifobj);


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, mainobj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // notif successfully sent

                                tost("notif send resp : " + response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // notif error occurred
                        tost("error: " + error);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        header.put("content-type", "application/json");
                        header.put("authorization", "key=AIzaSyAdM4s52OrcsJxUwL837Ke3Db2QtXHLZaE");
                        return header;
                    }
                };

                rq.add(request);
            } catch (Exception e) {
                tost("" + e.getMessage());
            }
        }

    }
}
