package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.MessageQueue;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.parser.Line;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//IQSE form
public class Dynamicform extends AppCompatActivity {

    String DT;

    boolean allowed  = true;

    boolean qrdone =  false;

    boolean codescannerset  = false;

    String pass = "sakshimamisbest";


    int ALREADYSUBMIT  = 0;

    String eventdate ;

    String usergender ;


    FirebaseAuth fauth ;

    int totalel =0;

    String course ;
    String emailid ;

    String evname =  "";

    int lastrb = 0;
    int lastet = 0;

    int ctel =0;

    Map<String , Object> rbvals;
    Map<String , Object> etvals;
    // declaration section...

    int eventid = -1;

    int rbno =  3000;
    int etno =  2000;

    FirebaseFirestore ff;
    LinearLayout dynll;


    Map<String,Integer> rids;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamicform);

        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


            course = "cs";

            emailid = "dummy@gmail.com";


            dynll = findViewById(R.id.dynll);

            ff = FirebaseFirestore.getInstance();

            fauth = FirebaseAuth.getInstance();

            if (fauth.getCurrentUser() != null) {
                emailid = fauth.getCurrentUser().getEmail();
//emailid = "bhomickaushik174@gmail.com";                                // to be removed !.
            }

            etvals = new HashMap<>();

            rbvals = new HashMap<>();

            rids = new HashMap<>();

            addevents();


           // getvalues();

        }catch(Exception e)
        {
            tost(" oncreate error : "+e.getMessage());
        }
    }


    public void addevents()
    {


        ff.collection("ADMINEVENTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("ResourceType")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                if(queryDocumentSnapshots!= null )
                {
                    TextView tv = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams lp0  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp0.setMargins(5,5,5,5);
                    tv.setLayoutParams(lp0);

                    tv.setText("Select the event : ");
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    CardView cv2 = new CardView(getApplicationContext());
                    CardView.LayoutParams clp2 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                    cv2.setLayoutParams(clp2);
                    clp2.setMargins(10,50,10,10);

                    cv2.setPadding(10,10,10,10);
                    cv2.setElevation(10);
                    // cv.setClipToOutline(false);
                    // cv2.setBackgroundColor(Color.parseColor("#03bbff"));
                    LinearLayout ll2 =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams lp12 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll2.setLayoutParams(lp12);
                    ll2.addView(tv);
                    cv2.addView(ll2);

                    dynll.addView(cv2);


                    RadioGroup rg =  new RadioGroup(getApplicationContext());
                    LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,5,5,5);
                    rg.setLayoutParams(lp);

                    rg.setId(999);

                    int foundev =0 ;
                    for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                    {
                        foundev = 1;
                        String  evname =  ""+d.getId();


                        RadioButton rb = new RadioButton(getApplicationContext());
                        LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        rb.setLayoutParams(lp2);

                        rb.setText(""+evname);

                        rb.setTextColor(Color.WHITE);
                        rb.setHighlightColor(Color.parseColor("#ffffff"));

                        rg.addView(rb);

                    }


                    CardView cv = new CardView(getApplicationContext());
                    CardView.LayoutParams clp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                    cv.setLayoutParams(clp);
                    clp.setMargins(10,10,10,10);

                    cv.setPadding(10,10,10,10);
                    cv.setElevation(10);
                    // cv.setClipToOutline(false);
                    cv.setBackgroundColor(Color.parseColor("#03bbff"));
                    LinearLayout ll =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams lp1 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.setLayoutParams(lp1);

                    LinearLayout tryll =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams trylp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    tryll.setLayoutParams(trylp);

                    tryll.addView(rg);
                    tryll.setWeightSum(1);

                    tryll.setOrientation(LinearLayout.VERTICAL);

                    ll.addView(tryll);
                    //  ll.addView(tryll2);

                    cv.addView(ll);

                    cv.setRadius(110);


                    if(foundev == 1)
                    {
                        dynll.addView(cv);

                        addquestions();

                        eventid = rg.getId();
                    }



                }
            }
        });

    }


    public void addquestions()
    {
        // creating a dynamic form with radiobuttons and the edittexts

        ff.collection("FORMS").document("IQSE").collection("QUESTIONS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int noq =  queryDocumentSnapshots.size();


                for(DocumentSnapshot  d : queryDocumentSnapshots.getDocuments())
                {


                    CardView cv = new CardView(getApplicationContext());
                    CardView.LayoutParams clp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                    cv.setLayoutParams(clp);
                    clp.setMargins(10,10,10,10);

                    cv.setPadding(10,10,10,10);
                    cv.setElevation(10);
                    // cv.setClipToOutline(false);
                    cv.setBackgroundColor(Color.parseColor("#03bbff"));
                    LinearLayout ll =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams lp1 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.setLayoutParams(lp1);


                    Map<String, Object> hm  = d.getData();
                    String question = ""+hm.get("QUESTION");

                    TextView tv = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams lp0  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp0.setMargins(5,5,5,5);
                    tv.setLayoutParams(lp0);


                    tv.setText(""+question);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    tv.setTextColor(Color.parseColor("#03bbff"));


                    CardView cv2 = new CardView(getApplicationContext());
                    CardView.LayoutParams clp2 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                    cv2.setLayoutParams(clp2);
                    clp2.setMargins(10,50,10,10);

                    cv2.setPadding(10,10,10,10);
                    cv2.setElevation(10);
                    // cv.setClipToOutline(false);
                    // cv2.setBackgroundColor(Color.parseColor("#03bbff"));
                    LinearLayout ll2 =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams lp12 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll2.setLayoutParams(lp12);
                    ll2.addView(tv);
                    cv2.addView(ll2);

                    dynll.addView(cv2);



                    //    tv.setTextColor(Color.parseColor("#ffffff" ));
                    //ll.addView(tv);




                    RadioGroup rg =  new RadioGroup(getApplicationContext());
                    LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,5,5,5);
                    rg.setLayoutParams(lp);
                    rg.setId(rbno++);

                    Iterator it =  hm.entrySet().iterator();


                    int rgfound = 0;
                    while(it.hasNext())
                    {
                        Map.Entry el =  (Map.Entry)it.next();

                        String ans =""+el.getKey();

                        String  vl = ""+el.getValue();

                        if((""+ans.charAt(0)).equals("r"))
                        {
                            rgfound =1 ;
                            RadioButton rb = new RadioButton(getApplicationContext());
                            LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            rb.setLayoutParams(lp2);

                            rb.setText(""+vl);

                            rb.setTextColor(Color.WHITE);
                            rb.setHighlightColor(Color.parseColor("#ffffff"));

                            rg.addView(rb);

                        }

                        if((""+ans.charAt(0)).equals("e"))
                        {


                            EditText et =  new EditText(getApplicationContext());


                            ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            et.setPadding(10,10,10,10);
                            et.setBackgroundColor(Color.parseColor(("#03bbff")));
                            et.setTextColor(Color.parseColor("#ffffff"));
                            et.setLayoutParams(etlp);
                            et.setHint(""+vl);
                            et.setId(etno++);
                            tost(""+(etno-1));

                            CardView dcv2 = new CardView(getApplicationContext());
                            CardView.LayoutParams dclp2 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                            dcv2.setLayoutParams(dclp2);
                            dclp2.setMargins(10,10,10,10);

                            dcv2.setPadding(10,10,10,10);
                            dcv2.setElevation(10);
                            // cv.setClipToOutline(false);
                            // cv2.setBackgroundColor(Color.parseColor("#03bbff"));
                            LinearLayout dll2 =  new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams dlp12 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            dll2.setLayoutParams(dlp12);
                            dll2.addView(et);
                            dcv2.addView(dll2);

                            dynll.addView(dcv2);
                        }



                    }

                    LinearLayout tryll =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams trylp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    tryll.setLayoutParams(trylp);

                    tryll.addView(rg);
                    tryll.setWeightSum(1);

                    tryll.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout tryll2 =  new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams trylp2  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    tryll2.setLayoutParams(trylp2);


                    //  ll.addView(tv);
                    tryll2.setOrientation(LinearLayout.VERTICAL);


                    ll.addView(tryll);
                    //  ll.addView(tryll2);

                    cv.addView(ll);

                    cv.setRadius(110);




                    if(rgfound ==1)
                    {
                        dynll.addView(cv);
                        String qno = ""+d.getId();

                        Integer rgid = new Integer(rg.getId());
                        rids.put(qno,rgid);

                    }else
                    {
                        rbno --;
                    }


                    // dynll.addView(tv);
                    // dynll.addView(rg);
                }
            }
        });

    }


    public void getvalues()
    {
        if(eventid !=-1)
        {
            RadioGroup evrg = findViewById(eventid);
            RadioButton evrb = findViewById(evrg.getCheckedRadioButtonId());

            if(evrb != null)
            {
                String evname =  ""+evrb.getText();
            }

            tost(""+rids);


        }


    }




    public void submit(View view) {



        ctel = 0;

        try {


            @SuppressLint("ResourceType")
            RadioGroup evrg = findViewById(999);
            RadioButton evrb = null;

            if(evrg != null)
                evrb = findViewById(evrg.getCheckedRadioButtonId());



            if(evrb!= null)
            {
                ctel ++;
                evname =""+evrb.getText();
            }
            //    tost(" evname : "+evname);



            String r = "r";

            int rit = 0;

            for (int i = 3000; i < rbno; i++) {
                RadioGroup rg = (RadioGroup) findViewById(i);

                RadioButton rb = null;

                if (rg != null)
                    rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                String rbval = "";
                if (rb != null) {
                    rbval = "" + rb.getText();
                    ctel++;
                }else
                {
                    lastrb =  i - 2999;
                }

                tost("" + rbval);

                String key = ""+r+(rit++);

                rbvals.put(key,rbval);
            }


            tost("<<0>>"+rbvals);



        }

        catch(Exception e)
        {
            tost(""+e.getMessage());
        }


// getting the edit texts .. now ..



        try {



            String set = "et";
            int etit =  0;
            for(int i = 2000 ; i<etno ;i++)
            {
                EditText et =  findViewById(i);

                String etval =  "";
                if(et != null)
                {
                    etval = ""+et.getText();
                    tost(""+etval);
                }

                String key =  ""+set+(etit++);

                if(!(etval.equals("")))
                {
                    etvals.put(key,etval);
                    ctel++;
                }else{
                    lastet = i-2000;
                }
            }

            //  tost(""+etvals);

        }catch(Exception e)
        {
            tost(""+e.getMessage());
        }


        getuserdetails();
        //uploaddata();

    }


    public void getuserdetails()
    {
        if(!evname.equals("") && !emailid.equals("")) {

            ff.collection("DYNEVE/" + evname + "/COURSES/" + course + "/USERS").document("" + emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot != null && documentSnapshot.exists()) {

                    }
                }
            });


          //  emailid = fauth.getCurrentUser().getEmail();

            ff.collection("register").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                            String checkemail = "" + d.getId();
                            if (checkemail.equals(emailid)) {
                                ff.collection("register").document(emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        Map<String, Object> hm = documentSnapshot.getData();

                                        course = "" + hm.get("course");


                                        if (!(course.equals(""))) {
                                            uploaddata();
                                        }
                                    }
                                });

                            }
                        }
                    }
                }
            });
        }
    }

    public  void uploaddata()
    {
        expdone =  false;
        regdone  = false;
        qrdone = false;
        wragain =  false;

        allowed =  true;

        syncoptions();


        if(!(""+evname).equals(""))
        {
            ff.document("ADMINEVENTS/"+evname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot!= null && documentSnapshot.exists())
                    {
                        Map<String,Object> hm  = documentSnapshot.getData();

                        String qrpass  = ""+hm.get("qrpass");

                        pass =  qrpass;


                        initverif();
                    }
                }
            });
        }else
        {
            tost("plz select  event name ! ");
        }


      //  submit();
    }

    
    public void uploaddataoriginal() {


        ff.collection("register").document(emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Map<String, Object> hm = documentSnapshot.getData();

                    course = "" + hm.get("course");


                    usergender =  ""+hm.get("gender");

                    lastrb = 0;
                    lastet = 0;

                    totalel = 1 + (etno - 2000) + (rbno - 3000);

                    if (totalel == ctel) {
                        tost(" ready to send");

                        //\\//\\

                        //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ

                        ff.collection("DYNEVE/"+evname+"/COURSES/"+course+"/USERS").document(""+emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if(documentSnapshot!=  null && documentSnapshot.exists()) {

                                    Intent  i =  new Intent(Dynamicform.this ,thankyou.class);
                                    startActivity(i);

                                }
                                else
                                {
                                    // consdition to be placed to cek for the expiry date of the event ..


                                    ff.collection("ADMINEVENTS").document(""+evname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot != null && documentSnapshot.exists())
                                            {
                                                Map<String, Object> hm =  documentSnapshot.getData();
                                                eventdate = ""+ hm.get("date");


                                                try{


                                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                                    Date strDate = sdf.parse(eventdate);
                                                    if (System.currentTimeMillis() > strDate.getTime()) {
                                                        tost("!! event expired>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
                                                    }else {

                                                        SimpleDateFormat sdf2 =  new SimpleDateFormat("yyyy-MM-dd");
                                                        String dt =  sdf2.format(strDate);

                                                        DT =  dt;
//dt=  dt.replaceAll("/","-");

                                                        tost("QQQQQQQQQQQ Go Ahead for reg test : QQQQQQQQQQQ \n"+"ALREADYREGISTERED/"+dt+"/EVENTS/"+evname );


                                                        ff.collection("ALREADYREGISTERED/"+dt+"/EVENTS/"+evname+"/PARTICIPANTS").document(""+emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                if(documentSnapshot != null  && documentSnapshot.exists()) {
                                                                    tost("  user : " + emailid + " also found in the ristered list ! ");


                                                                    //* ^


/*
                                                Map<String, Object> mam = new HashMap<>();
                                                mam.put("mam", "isbest");

                                                ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).set(mam).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        tost("" + e.getMessage());
                                                    }
                                                });
*/


                                                                 if(!codescannerset)
                                                                    startqr();

                                                                    if (qrdone) {
                                                                        tost("time to submit data ");


                                                                    ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("RADIO")
                                                                            .set(rbvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            tost("" + rbvals + " send! ");
                                                                        }
                                                                    });


                                                                    ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("EDITTEXT")
                                                                            .set(etvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            tost("" + etvals + " send! ");
                                                                        }
                                                                    });


                                                                    //--
                                                                    Map<String, Object> dummy = new HashMap<>();
                                                                    dummy.put("STK", "m");
                                                                    ff.collection("DYNEVE").document("" + evname).set(dummy);
                                                                    ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).set(dummy);
                                                                    ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).set(dummy);

                                                                    //--
                                                                    //\\//\\

                                                                }

                                                                }else
                                                                {
                                                                    tost(" sorry u r nnot registered  for this event ! ");
                                                                }
                                                            }
                                                        });


                                                    }


                                                } catch(Exception e){ tost(""+e.getMessage());}

                                            }
                                        }
                                    });




                                }

                                //^*
                            }

                        });

                        /*
                        //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
                        Map<String, Object> everything = new HashMap<>();

                        Iterator it1 = rbvals.entrySet().iterator();
                        while (it1.hasNext()) {
                            Map.Entry el = (Map.Entry) it1.next();
                            String key = "" + el.getKey();
                            String val = "" + el.getValue();

                            everything.put(key, val);

                        }

                        Iterator it2 = etvals.entrySet().iterator();
                        while (it1.hasNext()) {
                            Map.Entry el = (Map.Entry) it2.next();
                            String key = "" + el.getKey();
                            String val = "" + el.getValue();

                            everything.put(key, val);

                        }


                        //EXTRA><>

                        ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("RADIO")
                                .set(rbvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                tost("" + rbvals + " send! ");
                            }
                        });

                        ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("EDITTEXT")
                                .set(etvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                tost("" + etvals + " send! ");
                            }
                        });

                         */



                        //EXTRA<<>

                    } else {
                        tost(" something is missing ! " + "total : " + totalel + " ctel" + ctel);
                        tost("Plz fill rbno : " + lastrb + " & etno : " + lastet);
                    }


                }


            }

        });

    }

    private CodeScanner mCodeScanner;

    public void startqr()
    {

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();

                        if((""+result.getText()).equals(""+pass))
                        {
                            tost("go ahead to submit ");
                            qrdone = true;
                            uploaddata();
                        }else
                        {
                            tost("try again .. click on screen !");
                        }
                    }
                });
            }
        });


        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        mCodeScanner.startPreview();

        codescannerset  = true;
    }



    @Override
    protected void onResume() {
        super.onResume();
    if(codescannerset)
    {
        mCodeScanner.startPreview();
    }

    syncoptions();
    }



    public void hideqr()
    {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerView.setVisibility(View.INVISIBLE);
    }


    boolean op1, op2,op3 ,op4;

    public void syncoptions()
    {
        ff.collection("VERIFICATIONSELECTION").document("IQSE").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot !=  null  && documentSnapshot.exists())
                {
                    Map<String, Object> op =  documentSnapshot.getData();

                     op1 =  (boolean)op.get("exp");
                     op2 =  (boolean)op.get("reg");
                     op3 =  (boolean)op.get("qr");
                     op4 =  (boolean)op.get("ots");


                     if(op3  == false) {
                         hideqr();
                     }
                  /*
                    if(op1)jtb.setChecked(true); else jtb.setChecked(false);
                    if(op2)jtb2.setChecked(true); else jtb2.setChecked(false);
                    if(op3)jtb2.setChecked(true); else jtb3.setChecked(false);
                    if(op4)jtb4.setChecked(true); else jtb4.setChecked(false);
                   */



                }
            }
        });

    }

boolean expdone  = false , regdone  =  false , cqrdone = false  , wragain  =  false ;


    public boolean areallchecked()
    {
        boolean alldone = true;

        if(op1) alldone =   false;
        if(op2) alldone =  false;
        if(op3) alldone =  false;
        if(op4) alldone = false;


        return  alldone;
    }

    public void exp() {


            ff.collection("ADMINEVENTS").document("" + evname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Map<String, Object> hm = documentSnapshot.getData();


                        try {

                            String minutes  =  ""+hm.get("minutes");

Long perftime  =  Long.parseLong(""+minutes);

                            if ((System.currentTimeMillis()/60000) > perftime) {
                                tost("!! event expired>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");


                                allowed = false;

                            } else {

                                tost(" time has not expired till yet ! ");
                                /*
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                                String dt = sdf2.format(strDate);

                                DT = dt;


                                 */
                            }
                        } catch (Exception e) {
                            tost("error: " + e.getMessage());
                        }

                        expdone=  true;

                        initverif();
                    }
                }
            });

    }

    public void checkreg() {


        try {

            ff.collection("ADMINEVENTS").document("" + evname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot != null && documentSnapshot.exists()) {

                      try {

                          Map<String, Object> hm = documentSnapshot.getData();

                          String eventdate = "" + hm.get("date");

                          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                          Date strDate = sdf.parse(eventdate);

                          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                          String dt = sdf2.format(strDate);

                          DT = dt;


                          ff.collection("ALREADYREGISTERED/" + DT + "/EVENTS/" + evname + "/PARTICIPANTS").document("" + emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                              @Override
                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                  if (documentSnapshot != null && documentSnapshot.exists()) {
                                      tost("  user : " + emailid + " also found in the ristered list ! ");

                                  } else {

                                      tost(" failed in the registration list ");
                                      allowed = false;
                                  }

                                  regdone = true;

                                  initverif();
                              }
                          });

                      }catch (Exception e)
                      {
                          tost("inner ex : "+e.getMessage());
                      }
                    }
                }
            });

        }
        catch (Exception e)
            {
tost(" ex : "+e.getMessage());
        }

    }

    boolean qrfound =  false;

    public void checkqr() {

if(!codescannerset) {
    CodeScannerView scannerView = findViewById(R.id.scanner_view);
    mCodeScanner = new CodeScanner(this, scannerView);


    mCodeScanner.setDecodeCallback(new DecodeCallback() {
        @Override
        public void onDecoded(@NonNull final Result result) {
            qrdone = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();

                    if (("" + result.getText()).equals("" + pass)) {
                        tost("go ahead to submit ");
                        qrfound = true;

                        initverif();

                    } else {
                        tost("try again .. click on screen !");
                    }
                }


            });
        }


    });


    scannerView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCodeScanner.startPreview();
        }
    });

    mCodeScanner.startPreview();
    codescannerset = true;
}else
{
    mCodeScanner.startPreview();
}

    }

    public void checktowriteagain()
    {

        ff.collection("DYNEVE/"+evname+"/COURSES/"+course+"/USERS").document(""+emailid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot != null && documentSnapshot.exists()) {


                    tost(" failed to write again");
    allowed =  false;
                 }


                wragain =  true;
                initverif();
            }
        });


    }



    public  void verify()
    {
        allowed = true;

        exp();
        checkreg();
        checkqr();
        checktowriteagain();

    }


    int lastlim = 0;

    public  void  initverif()
    {

        try {



            if (lastlim++ < 20) {
                if (op1) {
                    if (expdone) {

                    } else {
                        exp();
                    }

                } else {
                    expdone = true;
                }

                if (op2) {
                    if (regdone) {

                    } else {
                        checkreg();
                    }
                } else {
                    regdone = true;
                }


                if (op3) {
                    if (qrdone) {

                    } else {
                        checkqr();
                    }
                } else {
                    qrdone = true;
                }

                if (!op4) {
                    if (wragain) {

                    } else {
                        checktowriteagain();
                    }
                } else {
                    wragain = true;
                }


                if (expdone && regdone && qrdone && wragain) {
                    if (allowed) {

                        if (op2 && qrfound) {
                            submit();
                        } else if (!op2) {
                            submit();
                        }
                    }
                }
            } else {
                tost(" recu running heavily breaking out of loop ");
            }
        }
        catch(Exception e)
        {
            tost(" e"+e.getMessage());
        }

    }

    public void submit() {

        try {


            if (!evname.equals("")) {

                tost(" submitting data ");

                ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("RADIO")
                        .set(rbvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tost("F " + rbvals + " send! ");
                    }
                });


                ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).collection("RESPONSES").document("EDITTEXT")
                        .set(etvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tost("F " + etvals + " send! ");
                    }
                });


                Map<String, Object> dummy = new HashMap<>();
                dummy.put("STK", "m");
                ff.collection("DYNEVE").document("" + evname).set(dummy);
                ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).set(dummy);
                ff.collection("DYNEVE").document("" + evname).collection("COURSES").document(course).collection("USERS").document("" + emailid).set(dummy);

            } else {
                tost("plz select the event name ");
            }
        } catch (Exception e) {
tost(" error : "+e.getMessage());
        }
    }

}
