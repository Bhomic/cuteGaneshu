package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.compiler.PluginProtos;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.parser.Line;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class Createregevents extends AppCompatActivity {

    EditText evname;

    String evnamestr;
    AlertDialog.Builder alertadd;

    String eventsetted ;

    Map<String,Object> eventssetted ;
   // ImageView adiv  ;

    Calendar cal ;

    String dateval ;
    EditText  evnameet  ;

  //  ImageView imageView;

    FirebaseFirestore ff ;

    Bitmap bm  ;

    StorageReference mStorageRef;
    List<EventDay> events;
    CalendarView calendarView;

    StorageReference regevref  ;


    LayoutInflater factory ;
    View view ;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();
    }


    public void fillscrollview()
    {

        LinearLayout ll  = view.findViewById(R.id.svll);

        ImageView iv  =  new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        iv.setLayoutParams(lp);

        iv.setImageResource(R.drawable.events);


        ll.addView(iv);

        TextView tv2  =  new TextView(getApplicationContext());
        LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv2.setLayoutParams(lp2);

        ll.addView(tv2);

        alertadd.create().show();


    }



    public void dayclicklistener()
    {



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar clickedDayCalendar = eventDay.getCalendar();
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                final String datestr =  dateFormat.format(clickedDayCalendar.getTime());


                Intent i =  new Intent(Createregevents.this, Manageregevents.class);
                i.putExtra("date",datestr);

                startActivity(i);

                /*
                LayoutInflater li =  LayoutInflater.from(Createregevents.this);

                final View  popup =  li.inflate(R.layout.alertimage,null);


                TextView crnev =  popup.findViewById(R.id.createnewevent);


                ff.collection("Createdregistrationevents").document(datestr).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                    }
                });


                crnev.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v)
                    {

                        final ImageView poster =  new ImageView(Createregevents.this);
                        LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 200);
                        poster.setLayoutParams(lp);

                        poster.setImageResource(R.drawable.thank);

                        poster.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i  =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(i, poster.getId());
                            }
                        });

                        LinearLayout ll =  popup.findViewById(R.id.svll);

                        ll.addView(poster);

                        evname=  new EditText(Createregevents.this);
                        //im.showSoftInput( evname, 0);
                        ViewGroup.LayoutParams etlp =  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        evname.setLayoutParams(etlp);

                       evname.setFocusable(true);
                       evname.setFocusableInTouchMode(true);

                        ll.addView(evname);






                        Button b  =  new Button(getApplicationContext());
                        b.setLayoutParams(etlp);
                        b.setText("Create event  ");
                        b.setTextColor(Color.WHITE);

                        ll.addView(b);
                        b.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v) {


                                evnamestr =  ""+evname.getText();
                                if(!evnamestr.equals(""))
                                {


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
                                               newev.put(""+el.getKey(), el.getValue());

                                               if((""+evnamestr).equals(""+el.getValue()))
                                               {
                                                   found  =  1;
                                               }

                                           }

                                           if(found ==0)
                                           {
                                               newev.put("regevname",evnamestr);
                                           }else {
                                               tost(" already same name event present ! ");
                                           }

                                           ff.collection("Createdregistrationevents" ).document(""+datestr).set(newev);
                                       }
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


                AlertDialog.Builder ad =  new AlertDialog.Builder(Createregevents.this);


                ad.setView(popup).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog  =  ad.create();

                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                dialog.show();
*/
            }

        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createregevents);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        alertadd = new AlertDialog.Builder(Createregevents.this);

        factory = LayoutInflater.from(Createregevents.this);

        view = factory.inflate(R.layout.alertimage, null);
        //adiv  = findViewById(R.id.adiv);

        mStorageRef = FirebaseStorage.getInstance().getReference();

      //  imageView = (ImageView) findViewById(R.id.imgv);


        ff  = FirebaseFirestore.getInstance();



        //  fillimages();

        // evnameet =  findViewById(R.id.crregeventname);



        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.events));

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);



        updatecal();



        dayclicklistener();
        /*
        calendarView.setOnDayClickListener(new OnDayClickListener() {

            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                clickedDayCalendar.getTime();

             //   tost(""+clickedDayCalendar);

                final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                final DateFormat dateFormat_year = new SimpleDateFormat("yyyy");
                final DateFormat dateFormat_month = new SimpleDateFormat("MM");
                final DateFormat dateFormat_day = new SimpleDateFormat("dd");
                 cal = clickedDayCalendar;

                tost(dateFormat.format(cal.getTime()));

                dateval = dateFormat.format(cal.getTime());


                LayoutInflater factory = LayoutInflater.from(Createregevents.this);
                final View view = factory.inflate(R.layout.alertimage, null);


                evnameet =  (EditText)view.findViewById(R.id.aevnameet);

                final ImageView iv2 =  (ImageView )view.findViewById(R.id.adiv);
                iv2.setImageBitmap(bm);

                tost(""+iv2);


                final ImageView image = new ImageView(Createregevents.this);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);

                image.setLayoutParams(lp);

                image.setImageBitmap(bm);

                image.getLayoutParams().height = 100;

                image.requestLayout();

                 eventsetted  = "_";

                ff.collection("CRREGEVENTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                        {
                            Map <String , Object>  hm  =  d.getData();

                            String dt  =  ""+hm.get("date");

                            if((""+dateval).equals(dt))
                            {
                                eventsetted =  ""+hm.get("eventname");
                            }
                        }


                      //----------------------------


                        if(!(""+eventsetted).equals("_"))
                        {


                            StorageReference islandRef = mStorageRef.child(""+eventsetted+".jpg");

                            tost("the img ref : "+islandRef);

                            FirebaseStorage storage = FirebaseStorage.getInstance();

                            StorageReference iconRef = storage.getReferenceFromUrl("gs://cuteganesha-a4db4.appspot.com/"+eventsetted+".jpg");


                            iconRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    Glide.with(Createregevents.this)
                                            .load(uri)
                                            .into(iv2);

                                }
                            });

                        }


                        TextView tv  = (TextView)view.findViewById(R.id.eventname);
                        tv.setText("Event : "+eventsetted);


                    alertadd.setView(view).setNeutralButton("Remove event ! ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {


                                ff.collection("CRREGEVENTS").document(""+eventsetted).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        updatecal();

                                        tost("event deleted ! " );
                                    }
                                });


                            }
                        }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tost("yes");

                                updatecal();

                                //   events.add(new EventDay(cal, R.drawable.thank));

                                //      calendarView.setEvents(events);

                                //  createregevent();

                                //=============================



                                final String eventname =  ""+evnameet.getText();

                                if(! eventname.equals("")) {

                                    Map<String, Object> hm = new HashMap<>();
                                    hm.put("eventname", eventname);
                                    hm.put("pic", "" + eventname + ".jpg");
                                    hm.put("date", dateval);
                                    ff.collection("CRREGEVENTS").document(eventname).set(hm);


                                    regevref =  mStorageRef.child(""+eventname+".jpg");

                                    imageView.setDrawingCacheEnabled(true);
                                    imageView.buildDrawingCache();
                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();

                                    tost(""+data);
                                    if(data !=  null) {
                                        UploadTask uploadTask = regevref.putBytes(data);
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            }
                                        });
                                    }




                                }else
                                {
                                    tost("please enter the name of event: ");
                                }

                                updatecal();

                                //=============================

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tost("no");
                            }
                        }).show();









                      //----------------------------


                    }
                });





                // System.out.println(dateFormat_year.format(cal.getTime()));
               // System.out.println(dateFormat_month.format(cal.getTime()));
               // System.out.println(dateFormat_day.format(cal.getTime()));
            }
        });

        */

        /*


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
            }
        });


        List<Calendar> selectedDates = calendarView.getSelectedDates();
        Calendar selectedDate = calendarView.getFirstSelectedDate();


        Calendar calen = Calendar.getInstance();
        calen.set(2019, 7, 5);

        try {
            calendarView.setDate(calendar);
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            calendarView.setMinimumDate(min);
            calendarView.setMaximumDate(max);
            List<Calendar> calendars = new ArrayList<>();
            calendarView.setDisabledDays(calendars);
        }
        catch (Exception e)
        {
            tost(""+e.getMessage());
        }


        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
        tost("prev page ");
            }
        });calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
        tost("next page ");
            }
        });



 */
    }


    public void createregeventsubmit(View view) {


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
                ImageView img  = view.findViewById(rq);


                if(img!= null)
                    img.setImageBitmap(BitmapFactory.decodeFile(picpath));

                bm =  BitmapFactory.decodeFile(picpath);



            }

        }
    }

    public void getimage(View view) {

        Intent i  =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 500);

        tost("getting image  ");


    }


    public void createregevent()
    {


        final String eventname =  ""+evnameet.getText()+".jpg";

        if(!eventname.equals("")) {
            StorageReference regevrefimg = mStorageRef.child(eventname);


      /*      imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = regevref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    Map<String, Object> hm   =  new HashMap<>();
                    hm.put("eventname", eventname);
                    hm.put("pic",""+eventname+".jpg");
                    hm.put("date",dateval);
                    ff.collection("CRREGEVENTS").document(eventname).set(hm);
                }
            });

        */
        }else

        {
            tost("please enter the event name : ");
        }
    }



    public void fillimages()
    {

        ff.collection("CRREGEVENTS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                events =  new ArrayList<>();

                Calendar calender;

                for(DocumentSnapshot  d: queryDocumentSnapshots.getDocuments())
                {
                    Map<String , Object > hm  =  d.getData();

                    String date =  ""+hm.get("date");

                    try {


                        String stringDate = date;
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        Date dt = formatter.parse(stringDate);
                        calender = Calendar.getInstance();
                        calender.setTime(dt);


                        /*
                        StorageReference islandRef = mStorageRef.child(""+hm.get("pic"));


                       Glide.with(Createregevents.this)
                                .load(islandRef)
                                .into(imageView);


               */
/*                        final long ONE_MEGABYTE = 1024 * 1024;

                        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                // Data for "images/island.jpg" is returns, use this as needed


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });


                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
*/

                        events.add(new EventDay(calender,R.drawable.events));


                    }
                    catch(Exception ex)
                    {
                        tost("error : "+ex.getMessage());



                    }
                }


            }
        });
    }



    public void updatecal()
    {


        try {

            ff.collection("Createdregistrationevents").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    events =  new ArrayList<>();

                    for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                    {

                        final String date = ""+d.getId();
                        ;                                    if(d!=null  && d.exists()) {

                        ff.collection("Createdregistrationevents").document(""+date).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Map<String, Object> hm = documentSnapshot.getData();

                                tost("+ d>> "+documentSnapshot.getData());



                                int sz  =0 ;
                                if(hm!= null)
                                    sz = hm.size();

                                tost("size : "+sz );

                                try {

                                    String stringDate = date;
                                    stringDate = stringDate.replace('-', '/');
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                                    Date dt = formatter.parse(stringDate);
                                    final Calendar calender = Calendar.getInstance();
                                    calender.setTime(dt);

                                    if (sz == 1) {
                                        events.add(new EventDay(calender, R.drawable.singledot));
                                    } else if(sz > 1){
                                        events.add(new EventDay(calender, R.drawable.multidot));
                                    }

                                }
                                catch(Exception e)
                                {
                                    tost(" err : "+e.getMessage());
                                }


                                calendarView.setEvents(events);

                            }
                        });
                    }
                    }


                    //  calendarView.setEvents(events);
                }
            });


        }catch (Exception ex )
        {
            tost(""+ ex.getMessage());
        }

    }


    public void findev(String date)
    {


    }


    @Override
    protected void onResume() {
        super.onResume();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        updatecal();
    }
}
