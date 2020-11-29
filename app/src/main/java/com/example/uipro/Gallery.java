package com.example.uipro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jsibbold.zoomage.AutoResetMode;
import com.jsibbold.zoomage.ZoomageView;

import org.apache.poi.hssf.record.IterationRecord;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import quatja.com.vorolay.VoronoiView;

public class Gallery extends AppCompatActivity {

    String event =  "notfound";


    int imit = 0;
    int arit  = 0;

    int ids[];
    int idit = 1000;

    LinearLayout ll ;

   String images [];


    VoronoiView voronoiView;

    LayoutInflater layoutInflater;

    FirebaseFirestore ff;

    View imgv;

    LayoutInflater infl ;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

     private Random rand = new Random();

     public int randomColor() {
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r,g,b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       ll =  findViewById(R.id.ll);

       event = this.getIntent().getExtras().getString("event");

        ff =  FirebaseFirestore.getInstance();

        infl =  LayoutInflater.from(Gallery.this);

        //imgv  =  infl.inflate(R.layout.vvitem,null);

        layoutInflater = getLayoutInflater();

      //voronoiView = (VoronoiView) findViewById(R.id.vv);

       // View view = layoutInflater.inflate(R.layout.vvitem, null, false);
        //oronoiView.addView(view);

        //ImageView frame = view.findViewById(R.id.img);

        //frame.setBackgroundResource(R.drawable.brazil);

// makenewvorno();

           // fillimagesinframe();

/*
        for (int i = 0; i < 15; i++) {
            View view = layoutInflater.inflate(R.layout.vvitem, null, false);
            voronoiView.addView(view);

            ImageView layout = view.findViewById(R.id.img);

            int randomColor = randomColor();
            layout.setImageResource(R.drawable.brazil);
        }
*/

    }

    public void upload(View view)
    {
         startActivity(new Intent(Gallery.this,UploadPhoto.class));
    }


    int fit  =0;

    public void makenewvorno()
    {

     //   ll.removeAllViews();
     //   imit  = 0;

        ff.collection("gallery").document(event).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot!= null && documentSnapshot.exists())
                {

                    Map<String,Object> hm = documentSnapshot.getData();

                    if(! (""+hm).equals("{}") ) {


                        tost("hm: "+hm);





                        final VoronoiView vv = new VoronoiView(getApplicationContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        vv.setLayoutParams(lp);


      /*         //     View view = layoutInflater.inflate(R.layout.vvitem, null, false);

                //    vv.addView(view);

                //    vv.setBorderColor(Color.CYAN);
                //    vv.setBorderWidth(2);

                //    final ImageView frame = view.findViewById(R.id.img);

                 //   frame.setId(idit);
                 //   ids[arit++] = frame.getId();

                  //  frame.setBackgroundResource(R.drawable.hermsgbg);
*/



                        //...........................................

                        images = new String[hm.size()];

                        Iterator it = hm.entrySet().iterator();

                        while (it.hasNext()) {

                            Map.Entry el = (Map.Entry) it.next();

                            String photoname = "" + el.getValue();


                            View view = layoutInflater.inflate(R.layout.vvitem, null, false);
                            vv.addView(view);

                            vv.setBorderColor(Color.BLACK);
                            vv.setBorderWidth(5);

                            TextView tv = view.findViewById(R.id.text);
                            tv.setText("" + photoname);


                            final ImageView frame = view.findViewById(R.id.img);

                            frame.setId(idit++);
                            //  ids[arit++] = frame.getId();

                            frame.setBackgroundResource(R.drawable.hermsgbg);


                            images[imit++] = photoname;

                            FirebaseStorage storage = FirebaseStorage.getInstance();


                            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + photoname + ".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    Glide.with(getApplicationContext())
                                            .load(uri)
                                            .into(frame);


                                }

                            });
                        }


                        ll.addView(vv);


                        vv.setOnRegionClickListener(new VoronoiView.OnRegionClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Toast.makeText(Gallery.this, "position: " + position, Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder ad = new AlertDialog.Builder(Gallery.this);

                                final ImageView iv = new ImageView(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                iv.setLayoutParams(lp);
                                iv.setImageResource(R.drawable.brazil);
                                //ad.setView(iv);


                                final ZoomageView zv  = new ZoomageView(getApplicationContext());
                                zv.setLayoutParams(lp);
                                zv.setImageResource(R.drawable.brazil);
                                zv.setZoomable(true);
                                zv.setAnimateOnReset(true);
                                zv.setAutoCenter(true);
                                zv.setRestrictBounds(false);
                                zv.setAutoResetMode(AutoResetMode.UNDER);

                                ad.setView(zv);

                                //>>

                                FirebaseStorage storage = FirebaseStorage.getInstance();


                                StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + images[position] + ".jpg");


                                iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(zv);

                                    }
                                });

                                //>>
                                AlertDialog adlg = ad.create();
                                adlg.show();


                            }
                        });


                    }

                }
            }
        });
    }

    public void fillimagesinframe()
    {
        try {

            ff.collection("gallery").document("event1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        Map<String, Object> hm = documentSnapshot.getData();


                        tost(""+hm);



                        images = new String[hm.size()];


                        int imit = 0;

                        Iterator it = hm.entrySet().iterator();


                        while (it.hasNext())
                        {

                            Map.Entry el = (Map.Entry) it.next();

                            String photoname = "" + el.getValue();

                            images[0] = photoname;

                            //>>

                            FirebaseStorage storage = FirebaseStorage.getInstance();


                            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + photoname + ".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    View view = layoutInflater.inflate(R.layout.vvitem, null, false);
                                    voronoiView.addView(view);

                                    ImageView frame = view.findViewById(R.id.img);


                                    Glide.with(Gallery.this)
                                            .load(uri)
                                            .into(frame);

                                }
                            });

                            //>>
                        }


                        voronoiView.setOnRegionClickListener(new VoronoiView.OnRegionClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Toast.makeText(Gallery.this, "position: " + position, Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder ad = new AlertDialog.Builder(Gallery.this);

                                final ImageView iv = new ImageView(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                iv.setLayoutParams(lp);
                                iv.setImageResource(R.drawable.brazil);
                                ad.setView(iv);


                                //>>

                                FirebaseStorage storage = FirebaseStorage.getInstance();


                                StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + images[0] + ".jpg");


                                iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        View view = layoutInflater.inflate(R.layout.vvitem, null, false);
                                        voronoiView.addView(view);

                                        ImageView frame = view.findViewById(R.id.img);


                                        Glide.with(Gallery.this)
                                                .load(uri)
                                                .into(iv);

                                    }
                                });

                                //>>
                                AlertDialog adlg = ad.create();
                                adlg.show();


                            }
                        });




                    }
                }
            });


            //--------------------------


        }catch (Exception e)
        {
            tost(""+e.getMessage());

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
imit = 0;
ll.removeAllViews();

    makenewvorno();
    }
}
