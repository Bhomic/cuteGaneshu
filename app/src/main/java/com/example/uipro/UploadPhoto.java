package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadPhoto extends AppCompatActivity {


    ProgressBar pb;
    String eventchoosen  = "";

    LinearLayout radioll;


   FirebaseFirestore ff;

    ImageView iv ;
    EditText et ;

    Bitmap bm ;
    String photoname;

   public void tost(String msg)
   {
       Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        pb =  findViewById(R.id.progressBar);

  et  = findViewById(R.id.photoname);
  iv =  findViewById(R.id.memory);

  radioll = findViewById(R.id.radioll);


  ff= FirebaseFirestore.getInstance();
        fillradiobuttons();
    }

    public void uploadphoto(View view) {
   photoname = ""+et.getText();

  if(!photoname.equals("")&& bm!= null && (!eventchoosen.equals("")))
  {
      upload();

  }else
  {
      tost(" plz ennter an image name and image too , and also select the event !" );
  }
    }

    @SuppressLint("ResourceType")
    public void getphoto(View view) {

        Intent i  =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        tost(""+view.getId());
ImageView iv  =  (ImageView)view ;
iv.setId(3000);

        startActivityForResult(i, iv.getId());
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


    public void upload() {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference regevref = mStorageRef.child("" + photoname + ".jpg");

        if (iv != null) {

pb.setVisibility(View.VISIBLE);

            iv.setDrawingCacheEnabled(true);
            iv.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
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



                    ff.collection("gallery").document(""+eventchoosen).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot!= null && documentSnapshot.exists())
                            {

                                Map<String ,Object> hm  = documentSnapshot.getData();

                                String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   newphoto = ""+(hm.size()+1);

                                hm.put(newphoto,photoname);

                                ff.collection("gallery").document(""+eventchoosen).set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pb.setVisibility(View.INVISIBLE);
                                        tost("image uploaded ! ");
                                    }
                                });



                            }else
                            {
                                Map<String,Object> hm =  new HashMap<>();
                                hm.put("1",photoname);

                                ff.collection("gallery").document(""+eventchoosen).set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pb.setVisibility(View.INVISIBLE);
                                        tost("first image uploaded ! ");
                                    }
                                });

                            }
                        }
                    });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.INVISIBLE);
                        tost("image upload failed ! ");
                    }
                });
            }
        }

    }

    public void fillradiobuttons()
    {
        ff.collection("ADMINEVENTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                RadioGroup rg   =  new RadioGroup(getApplicationContext());
                LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rg.setLayoutParams(lp);


                for(DocumentSnapshot d  : queryDocumentSnapshots.getDocuments())
                {
                    String event  = ""+d.getId();
                RadioButton rb  =  new RadioButton(getApplicationContext());
                LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rb.setLayoutParams(lp2);
                rb.setText(""+event);

                rb.setBackgroundColor(Color.parseColor("#fffdd0"));
                rb.setTextColor(Color.parseColor("#034472"));

                rg.addView(rb);
                }


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton rb =  findViewById(checkedId);

                        String choosenevent = ""+rb.getText();

                        eventchoosen =  choosenevent;

                        tost(eventchoosen);
                    }
                });


                radioll.addView(rg);
            }
        });
    }
}
