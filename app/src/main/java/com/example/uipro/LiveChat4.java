package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.pdf.parser.Line;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nullable;

public class LiveChat4 extends AppCompatActivity {

    int imgallowed = 1;

    int lastmsgct  =  0;

    int lastmsgno = 0 ;
    int firstrun = 1;

   AlertDialog cont ;

    Bitmap bm ;

    String myname =  "unknown";
    String myemail ;


    FirebaseAuth fa ;
    Map<String , Object> upchats ;

EditText et ;

ScrollView sv ;

String me = "me";
    FirebaseFirestore ff;
   LinearLayout ll;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_chat4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        FirebaseMessaging.getInstance().subscribeToTopic("FEEDU").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });

        fa =  FirebaseAuth.getInstance();

        ll = findViewById(R.id.ll);

    ff =  FirebaseFirestore.getInstance();

    myemail =  fa.getCurrentUser().getEmail();

    me =  myemail;

    et  =  findViewById(R.id.msg);

    sv =  findViewById(R.id.sv);


    syncchatlistener();


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          godown();
        }
    },6000);
    }

    public void syncchatlistener()
    {
        ff.collection("LIVECHAT").document("chats").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if(documentSnapshot!=  null && documentSnapshot.exists())
                {
                    final Map<String,Object> hm  =  documentSnapshot.getData();

                    ff.collection("LIVECHAT").document("meta").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot!= null && documentSnapshot.exists())
                            {
                                Map<String,Object> hm2 =  documentSnapshot.getData();

                                String counts =  ""+hm2.get("count");

                                int ct =  Integer.parseInt(counts);


                                if(lastmsgct ==  0)
                                {
                                    ll.removeAllViews();
                                }

                                if(firstrun == 1)
                                {
                                    firstrun = 0;


                                }else
                                {
                                    ct++;
                                }

                                for(int i =lastmsgct +1 ;i<=ct;i++)
                                {
                                    String str = ""+hm.get(""+i);

                                    if(!str.equals("")) {


                                        try {
                                            String arr[] = str.split("/");

                                            if(arr.length >=  2) {
                                                String sender = arr[1];
                                                String msg = arr[0];

                                                addmsg(msg, sender, arr);

                                                lastmsgct = i;
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            tost(" ex  : "+e.getMessage());
                                        }
                                        }
                                }

godown();

                            }
                        }
                    });


                }
            }
        });
    }


    public void godown()
    {
        View lastChild = sv.getChildAt(sv.getChildCount() - 1);
        int bottom = lastChild.getBottom() + sv.getPaddingBottom();
        int sy = sv.getScrollY();
        int sh = sv.getHeight();
        int delta = bottom - (sy + sh);

        sv.smoothScrollBy(0, bottom+100);

    }

    public void syncchat2()
    {

        ff.collection("LIVECHAT").document("chats").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot!=  null && documentSnapshot.exists())
                {
                    final Map<String,Object> hm  =  documentSnapshot.getData();

                    ff.collection("LIVECHAT").document("meta").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot!= null && documentSnapshot.exists())
                            {
                    Map<String,Object> hm2 =  documentSnapshot.getData();

                    String counts =  ""+hm2.get("count");

                    int ct =  Integer.parseInt(counts);

                    ll.removeAllViews();

                    for(int i =0 ;i< ct;i++)
                    {
                        String str = ""+hm.get(""+i);

                        String arr[] = str.split("/");

                        String sender =  arr[arr.length-1];
                        String msg = arr[0];

                        addmsg(msg, sender , arr);

                    }
                            }
                        }
                    });
                }
            }
        });
    }


    public void addmsg(String msg , String sender, String arr[])
    {

        try {


            if (sender.equals(me)) {

                if (arr[arr.length - 1].equals("img")) {
                    try {
                        final ImageView iv = new ImageView(LiveChat4.this);
                        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        iv.setLayoutParams(lp1);

                        iv.setImageResource(R.drawable.imgdefault);
                        ll.addView(iv);

                        lp1.setMargins(100, 10, 10, 10);

                        String imgname = arr[0];

                        FirebaseStorage storage = FirebaseStorage.getInstance();


                        StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + imgname + ".jpg");


                        iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Glide.with(LiveChat4.this)
                                        .load(uri)
                                        .into(iv);


                            }
                        });

                    } catch (Exception e) {
                        tost("ex : " + e.getMessage());
                    }

                    godown();


                } else {

                    TextView tv = new TextView(getApplication());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    lp.setMargins(200, 10, 10, 10);

                    tv.setLayoutParams(lp);
                    tv.setText("" + msg);
                    //           tv.setBackgroundColor(Color.parseColor("#03bbff"));
                    tv.setTextColor(Color.WHITE);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    ll.addView(tv);

                    tv.setBackgroundResource(R.drawable.mymsgbg);

                    tv.setPadding(10, 0, 10, 0);


                    godown();
                }


            } else {
                if (!msg.equals("") && !sender.equals("")) {


                    if (arr[arr.length - 1].equals("img")) {
                        try {
                            final ImageView iv = new ImageView(LiveChat4.this);
                            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            iv.setLayoutParams(lp1);

                            lp1.setMargins(10, 10, 100, 10);

                            iv.setImageResource(R.drawable.imgdefault);
                            ll.addView(iv);


                            String imgname = arr[0];

                            FirebaseStorage storage = FirebaseStorage.getInstance();


                            StorageReference iconRef = storage.getReferenceFromUrl("gs://feedu-ef2eb.appspot.com/" + imgname + ".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Glide.with(LiveChat4.this)
                                            .load(uri)
                                            .into(iv);

                                }
                            });

                        } catch (Exception e) {
                            tost("ex : " + e.getMessage());
                        }


                        TextView tv2 = new TextView(getApplicationContext());
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp2.setMargins(10, 0, 300, 10);
                        tv2.setLayoutParams(lp2);
                        tv2.setText(arr[1]);

                        ll.addView(tv2);
                        tv2.setBackgroundColor(Color.TRANSPARENT);
                        tv2.setTextColor(Color.CYAN);

                        tv2.setTextSize(8);

                        godown();
                    } else {

                        TextView tv = new TextView(getApplication());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(10, 10, 100, 2);
                        tv.setLayoutParams(lp);
                        tv.setText("" + msg);
//            tv.setBackgroundColor(Color.parseColor("#ffffff"));
                        tv.setTextColor(Color.parseColor("#03bbff"));
                        tv.setPadding(10, 0, 10, 0);

                        tv.setBackgroundResource(R.drawable.hermsgbg);
                        ll.addView(tv);


                        TextView tv2 = new TextView(getApplicationContext());
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp2.setMargins(10, 0, 300, 10);
                        tv2.setLayoutParams(lp2);
                        tv2.setText(sender);

                        ll.addView(tv2);
                        tv2.setBackgroundColor(Color.TRANSPARENT);
                        tv2.setTextColor(Color.CYAN);

                        tv2.setTextSize(8);

                        godown();
                    }
                }
            }

        }catch(Exception e)
        {
            tost(" ex  : "+e.getMessage());
        }
    }

    public  void syncchat()
    {

        ff.collection("LIVECHAT").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                ll.removeAllViews();

                for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments())
                {

                    String id  = d.getId();

                    Map<String , Object> hm =  d.getData();

                    String sender = ""+hm.get("sender");

                    tost( ""+hm.get("msg"));

                    if(!id.equals("meta")) {
                        if (sender.equals(me)) {
                            TextView tv = new TextView(getApplication());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(100, 10, 10, 10);
                            tv.setLayoutParams(lp);
                            tv.setText("" + hm.get("msg"));
                            tv.setBackgroundColor(Color.parseColor("#03bbff"));
                            tv.setTextColor(Color.WHITE);
                            ll.addView(tv);

                            tv.setPadding(10,0,10,0);


                        } else {
                            TextView tv = new TextView(getApplication());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(10, 10, 100, 2);
                            tv.setLayoutParams(lp);
                            tv.setText("" + hm.get("msg"));
                            tv.setBackgroundColor(Color.parseColor("#ffffff"));
                            tv.setTextColor(Color.parseColor("#03bbff"));
                            tv.setPadding(10,0,10,0);
                            ll.addView(tv);


                            TextView tv2  =  new TextView(getApplicationContext());
                            LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(10,0,300,10);
                            tv2.setLayoutParams(lp2);
                            tv2.setText(sender);
                            ll.addView(tv2);
                            tv2.setBackgroundColor(Color.TRANSPARENT);
                            tv2.setTextColor(Color.CYAN);
                        }
                    }
                }


                View lastChild = sv.getChildAt(sv.getChildCount() - 1);
                int bottom = lastChild.getBottom() + sv.getPaddingBottom();
                int sy = sv.getScrollY();
                int sh = sv.getHeight();
                int delta = bottom - (sy + sh);

                sv.smoothScrollBy(0, bottom);
            }
        });

     /*
      ff.collection("livechat").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
          @Override
          public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

              for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments())
              {
                  String sender = ""+d.getId();

                  if(sender.equals(me))
                  {
                      TextView tv  =  new TextView(getApplication());
                      LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                      lp.setMargins(400,10,10,10);
                      tv.setLayoutParams(lp);
                      tv.setText(""+d.get("msg"));
                  }else
                  {
                      TextView tv  =  new TextView(getApplication());
                      LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                      lp.setMargins(10,10,10,400);
                      tv.setLayoutParams(lp);
                      tv.setText(""+d.get("msg"));

                  }
              }
          }
      });


      */
    }

    public void send(final View view)
    {
        view.setBackgroundResource(R.drawable.ico2);

        ff.collection("LIVECHAT").document("meta").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot!= null && documentSnapshot.exists())
                {
                    Map<String,Object> cnt =  documentSnapshot.getData();

               int ct = Integer.parseInt(""+cnt.get("count"));


                    final String  msg = ""+et.getText();

                    ct ++;


                    final int finalCt = ct;

                    ff.collection("LIVECHAT").document("chats").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot!=  null && documentSnapshot.exists())
                            {
                                upchats = documentSnapshot.getData();
                                upchats.put(""+finalCt,msg+"/"+me);

                                ff.collection("LIVECHAT").document("chats").set(upchats).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.setBackgroundResource(R.drawable.ico);

                                        Map<String , Object> upct =  new HashMap<>();
                                        upct.put("count",finalCt);

                                        ff.collection("LIVECHAT").document("meta").set(upct);
                                        et.setText("");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        view.setBackgroundResource(R.drawable.ico);

                                    }
                                });

                            }else
                            {
                                upchats=  new HashMap<>();

                                upchats.put(""+finalCt,msg+"/"+me);

                                ff.collection("LIVECHAT").document("chats").set(upchats).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.setBackgroundResource(R.drawable.ico);

                                        Map<String , Object> upct =  new HashMap<>();
                                        upct.put("count",finalCt);

                                        ff.collection("LIVECHAT").document("meta").set(upct);
                                        et.setText("");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        view.setBackgroundResource(R.drawable.ico);

                                    }
                                });
                            }
                        }
                    });



                }else
                {
                    Map<String,Object>  st= new HashMap<>();
                    st.put("count",""+0);

                    ff.collection("LIVECHAT").document("meta").set(st);
                }
            }
        });

    }

    public void dd(View view)
    {
        godown();
    }






    @SuppressLint("ResourceType")
    public void sendimg(View view) {

        try {

            AlertDialog.Builder db = new AlertDialog.Builder(LiveChat4.this);


            LayoutInflater li = LayoutInflater.from(LiveChat4.this);

            final View sendimg = li.inflate(R.layout.sendimg, null);

            ImageView iv = sendimg.findViewById(R.id.img);

            iv.setId(3000);

imgallowed =  1;

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(imgallowed ==  1) {

imgallowed = 0;
                       Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                       tost("" + iv.getId());

                       startActivityForResult(i, iv.getId());

                   }
                }
            });

            iv.setImageResource(R.drawable.imgdefault);

            DialogInterface.OnClickListener oc = new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            sendimage();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            db.setView(sendimg);

            db.setPositiveButton("Send", oc).setNegativeButton("Cancel", oc);
            AlertDialog ad = db.create();

            cont = ad;

            ad.show();
        }catch (Exception e)
        {
            tost(""+e.getMessage());
        }
    }


public void sendimage()
{
    try {
        EditText caption = cont.findViewById(R.id.caption);

        String captionstr = "" + caption.getText();

        Toast.makeText(cont.getContext(), "captionstr : " + captionstr + " bm : " + bm, Toast.LENGTH_LONG).show();

        if (captionstr.equals("") || bm == null) {
            Toast.makeText(cont.getContext(), "Please enter the image & also select the image ", Toast.LENGTH_LONG).show();
        } else {
            String msg = "" + captionstr + "/" + myemail + "/img";

            tost(msg);

            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference regevref = mStorageRef.child("" + captionstr + ".jpg");

            if (bm != null) {

                Bitmap bitmap = bm;
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

                            // registration of a new message which includes an image and then further synhronizing of the list


                            register(msg);


                        }
                    });

                }

            }


            // upload work
        }
    }catch(Exception e)
    {
        tost(""+e.getMessage());
    }
}

    @Override
    protected void onActivityResult(int rq  , int rs , Intent data)
    {



            super.onActivityResult(rq, rs, data);

            if (rs == RESULT_OK && null != data) {
                Uri selimg = data.getData();

                String[] filepath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selimg, filepath, null, null, null);

                c.moveToFirst();

                int ci = c.getColumnIndex(filepath[0]);


                String picpath = c.getString(ci);

                c.close();

                if (picpath != null) {
                    ImageView img = (ImageView) cont.findViewById(rq);


                    if (img != null)
                        img.setImageBitmap(BitmapFactory.decodeFile(picpath));

                    else
                        tost(" imageview not found ! ");


                    bm = BitmapFactory.decodeFile(picpath);

                }

            }

    }

    public void register(String msg)
    {
        Button view  =  findViewById(R.id.imgbtn);

        view.setBackgroundResource(R.drawable.imghighlight);

        ff.collection("LIVECHAT").document("meta").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot!= null && documentSnapshot.exists())
                {
                    Map<String,Object> cnt =  documentSnapshot.getData();

                    int ct = Integer.parseInt(""+cnt.get("count"));


                //    final String  msg = ""+et.getText();

                    ct ++;


                    final int finalCt = ct;

                    ff.collection("LIVECHAT").document("chats").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot!=  null && documentSnapshot.exists())
                            {
                                upchats = documentSnapshot.getData();
                                upchats.put(""+finalCt,msg);

                                ff.collection("LIVECHAT").document("chats").set(upchats).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.setBackgroundResource(R.drawable.imgdefault);

                                        Map<String , Object> upct =  new HashMap<>();
                                        upct.put("count",finalCt);

                                        ff.collection("LIVECHAT").document("meta").set(upct);
                                        et.setText("");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        view.setBackgroundResource(R.drawable.imgdefault);

                                    }
                                });

                            }else
                            {
                                upchats=  new HashMap<>();

                                upchats.put(""+finalCt,msg);

                                ff.collection("LIVECHAT").document("chats").set(upchats).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        view.setBackgroundResource(R.drawable.imgdefault);

                                        Map<String , Object> upct =  new HashMap<>();
                                        upct.put("count",finalCt);

                                        ff.collection("LIVECHAT").document("meta").set(upct);
                                        et.setText("");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        view.setBackgroundResource(R.drawable.imgdefault);

                                    }
                                });
                            }
                        }
                    });



                }else
                {
                    Map<String,Object>  st= new HashMap<>();
                    st.put("count",""+0);

                    ff.collection("LIVECHAT").document("meta").set(st);
                }
            }
        });
    }

}
