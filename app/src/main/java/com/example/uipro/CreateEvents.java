package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//>>


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nullable;

//>>
public class CreateEvents extends AppCompatActivity {

    String dt;

    String tm;


    RequestQueue rq;

    String url = "https://fcm.googleapis.com/fcm/send";

  long timetoupload  =0 ;
    //>>
int DAY;
int MONTH;
int YEAR;


    int hour  , minut ; long datetime ;

    //>>


    RainbowTextView rbt, rbtp;

TimePicker  tp ;
    MaterialCalendarView mc  ;
    public void tost(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rq =  Volley.newRequestQueue(this);

rbt=  findViewById(R.id.rbt);

rbtp = findViewById(R.id.rbtime);
tp =  findViewById(R.id.tp);

        mc = findViewById(R.id.mc);

        mc.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tost("+date "+date);
                SimpleDateFormat sf  =  new SimpleDateFormat("MM/dd/yyyy");
                dt = ""+sf.format(date.getDate());


                datetime = (date.getDate()).getTime();

                DAY = (date.getDate()).getDate();
                MONTH = (date.getDate()).getMonth();
                YEAR = (date.getDate()).getYear();


                tost(dt);
                rbt.setText(dt);

            }
        });


        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                tost(""+hourOfDay+":"+minute);
                rbtp.setText(""+hourOfDay+":"+minute);

                hour =  hourOfDay ;
                minut  = minute;

tm = ""+hourOfDay+":"+minute;

            }
        });


oncreate2();


    }


    public void createeventfun(View view) {
        MaterialCalendarView datePicker = mc;
        TimePicker timePicker = tp;

try {


    CalendarDay d = mc.getSelectedDate();


    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_MONTH, DAY);
    cal.set(Calendar.MONTH, MONTH);
    cal.set(Calendar.YEAR, (YEAR+1900));
    cal.set(Calendar.HOUR, hour);
    cal.set(Calendar.MINUTE, minut);


    long millis = cal.getTime().getTime();

    timetoupload = millis/60000 ;

    tost("millis: " + millis);

    Date currentTime = Calendar.getInstance().getTime();
    long now = currentTime.getTime();

    tost("now : " + now);

    long diff = millis - now;

    tost("datetime: "+datetime+"now : "+now +" set : "+millis+ " diff: " + ((diff/60000)-719));

  //  tost("h: "+hour+" m"+ minut+" day : "+DAY+" month : "+MONTH+"year : "+(YEAR+1900)) ;
    //tost("datetime : "+datetime+" h:"+hour+" min : "+minut);

    tost(""+(((timetoupload - (now/60000))-720)));



//>>>



        EditText et = findViewById(R.id.nameevent);
EditText ps  = findViewById(R.id.qrpass);

final String eventstr =  (""+et.getText());
final String pass = ""+ps.getText();

        final Map<String,Object> hm = new HashMap<>();

        hm.put("date",dt);
        hm.put("time",tm);
        hm.put("minutes",(timetoupload- 720));
        hm.put("qrpass",pass);



        tost("event name : "+eventstr);

        if(eventstr.equals("") || pass.equals(""))
        {
            tost("please enter an event name  and the qr password ");
        }else {
            ff.collection("ADMINEVENTS").document(eventstr).set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    tost(" event : "+eventstr+" created successfullly ! ");


                    sendnotif(eventstr, dt );

                    Intent i =  new Intent(CreateEvents.this, Generateqrcode.class);
                    i.putExtra("qrpass",pass);
                    startActivity(i);
                }
            });

            ff.collection("ADMINEVENTSTILLNOW").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    int foundtillnow = 0;
                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                        String ev = "" + d.getId();

                        if (ev.equals(eventstr)) {
                            foundtillnow = 1;
                        }

                    }


                    if (foundtillnow == 0) {
                        ff.document("ADMINEVENTSTILLNOW/" + eventstr).set(hm);
                    }
                }
            });
        }
//>>

}catch (Exception e)
{
    tost(""+e.getMessage());
}

    }


    //>>


    FirebaseFirestore ff  ;

    LinearLayout livelist ;
    LinearLayout eventstillnow;

    public void oncreate2()
    {
        ff  = FirebaseFirestore.getInstance();

        livelist =  findViewById(R.id.livelist);
        eventstillnow =  findViewById(R.id.eventstillnow);

/*
        et1  = findViewById(R.id.et1);

        picker = findViewById(R.id.pickdate);

        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                dateshow.setText(""+getCurrentDate());
            }
        });


        sublive = findViewById(R.id.sublive);
        subtillnow = findViewById(R.id.subtillnow);

        setdate = findViewById(R.id.setdate);

        dateView = (TextView) findViewById(R.id.dateshow);

        dateshow = (TextView)findViewById(R.id.dateshow);




 */
        filllivelist();

        filltillnow();



    }


    public void filllivelist()
    {

        final LinearLayout lllive =  findViewById(R.id.livelist);


        ff.collection("ADMINEVENTS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e!=null)
                {
                    tost("error "+e);
                    return;
                }else
                {


                    lllive.removeAllViews();

                    for(DocumentSnapshot d  : queryDocumentSnapshots.getDocuments())
                    {


                        final String liveevent  = ""+d.getId();



                        TextView tv =  new TextView(getApplicationContext());
                        LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(2,5,2,5);
                        tv.setLayoutParams(lp);
                        tv.setText(liveevent);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv.setTextSize(20);
                        tv.setBackgroundColor(Color.parseColor("#03bbff"));
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                TextView t  = (TextView)view ;
                                final String  evn= ""+t.getText();

                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //Yes button clicked

                                                FirebaseFirestore ff  = FirebaseFirestore.getInstance();

                                                ff.collection("ADMINEVENTS").document(evn).delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        tost("deleted : "+evn);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        tost("failed to delete : "+evn);
                                                    }
                                                });
                                                break;


                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                tost("deletion cancelled ");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvents.this);
                                builder.setMessage("Are you sure to delete < "+evn+" >from the live events ?").setPositiveButton("Yes", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener).show();
                            }
                        });


                        lllive.addView(tv);


                    }

                }



            }
        });







    }




    public void filltillnow()
    {

        final LinearLayout lltw =  findViewById(R.id.eventstillnow);

        ff.collection("ADMINEVENTSTILLNOW").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    tost("error " + e);
                    return;
                } else {
                    lltw.removeAllViews();


                    for(DocumentSnapshot d  : queryDocumentSnapshots.getDocuments())
                    {
                        String ev  = ""+d.getId();

                        Map<String,Object> hm = d.getData();
                        final String date  =  ""+hm.get("date");
                        final String time = ""+hm.get("time");

                        TextView tv = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(2,5,2,5);
                        tv.setLayoutParams(lp);
                        tv.setText(ev);
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setTextSize(20);
                        tv.setBackgroundColor(Color.parseColor("#0099cc"));
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);



                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView t  = (TextView)v ;
                                final String  evn= ""+t.getText();


                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvents.this);
                                builder.setMessage("Event : "+evn+"\n Date : "+date+"\n Time : "+time).setIcon(getDrawable(R.drawable.butterfly)).show();


                            }
                        });

                        lltw.addView(tv);
                    }
                }
            }
        });



    }




    //>>




    public void sendnotif(String eventname ,String date)
    {

        String titlestr = "";
        String bodystr  = "";

        titlestr = "New Event : "+eventname+" available ! ";
        bodystr = "Register now . Last Date : "+date;

        if(titlestr.equals("") || bodystr.equals("") )
        {
            tost(" plz enter the title and body of notification  ");
            ;        }
        else {
            JSONObject mainobj = new JSONObject();

            try {

                mainobj.put("to", "/topics/" + "NEWEVENT");
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
