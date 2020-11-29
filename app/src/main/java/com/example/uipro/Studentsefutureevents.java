package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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


public class Studentsefutureevents extends AppCompatActivity {

    Map<String ,Object> hm;

    int itit =0;

    int allreg  =0;
    String eventsetted ;

    Map<String,Object> eventssetted ;
    //ImageView adiv  ;

    Calendar cal ;

    String dateval ;

    ImageView imageView;

    FirebaseFirestore ff ;

    Bitmap bm  ;

    StorageReference mStorageRef;
    List<EventDay> events;
    CalendarView calendarView;

    StorageReference regevref  ;
    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsefutureevents);

        //adiv  = findViewById(R.id.adiv);

        mStorageRef = FirebaseStorage.getInstance().getReference();

    //    imageView = (ImageView) findViewById(R.id.imgv);


        ff  = FirebaseFirestore.getInstance();



        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.events));

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);



        updatecal();

        calendarView.setOnDayClickListener(new OnDayClickListener() {

            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar clickedDayCalendar = eventDay.getCalendar();
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                final String datestr =  dateFormat.format(clickedDayCalendar.getTime());


                Intent i =  new Intent(Studentsefutureevents.this, ManageregeventsStudent.class);
                i.putExtra("date",datestr);

                startActivity(i);


            }
        });


    }









    public void updatecal()
    {

        FirebaseAuth fa  =  FirebaseAuth.getInstance();
        final String myemail =  ""+fa.getCurrentUser().getEmail();
        try {

            ff.collection("Createdregistrationevents").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    events =  new ArrayList<>();

                    for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                    {

                        final String date = ""+d.getId();

                        if(d!=null  && d.exists()) {

                            ff.collection("Createdregistrationevents").document(""+date).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    hm = documentSnapshot.getData();

                                    Iterator it =  hm.entrySet().iterator();


                                    allreg = 1;

                                    itit =0 ;
                                    while(it.hasNext())
                                    {
                                        if(itit ==0)
                                        {
                                            allreg = 1;
                                        }
                                        itit++;
                                        Map.Entry el = (Map.Entry)it.next();

                                        final String eventname = ""+el.getValue();

                                        ff.collection("ALREADYREGISTERED").document(""+date).collection("EVENTS").document(""+eventname).collection("PARTICIPANTS").document(""+myemail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {


                                                if(documentSnapshot!= null && documentSnapshot.exists())
                                                {

                                                }else
                                                {
                                                    allreg =0;
                                                }

                                                tost("allreg : "+allreg);

                                                try {

                                                    String stringDate = date;
                                                    stringDate = stringDate.replace('-', '/');
                                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                                                    Date dt = formatter.parse(stringDate);
                                                    final Calendar calender = Calendar.getInstance();
                                                    calender.setTime(dt);


                                                    if(itit == hm.size()) {
                                                        if (allreg == 0) {
                                                            events.add(new EventDay(calender, R.drawable.picka));
                                                        } else {
                                                            events.add(new EventDay(calender, R.drawable.done));
                                                        }

                                                        tost("event : "+eventname+" allreg "+allreg );

                                                        allreg = 1;
                                                    }

                                                    calendarView.setEvents(events);

                                                }
                                                catch(Exception e)
                                                {
                                                    tost(" err : "+e.getMessage());
                                                }


                                            }



                                        });

                                    }





                                }
                            });
                        }
                    }



                }
            });


        }catch (Exception ex )
        {
            tost(""+ ex.getMessage());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        updatecal();
    }
}
