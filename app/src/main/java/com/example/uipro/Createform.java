package com.example.uipro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.method.DialerKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Createform extends AppCompatActivity {

    String formname = "";
    EditText et ;
    FirebaseFirestore ff  ;

    Map<String , Object> full2 ;

    String question ="";
    int Q = 0;
    int QR =0;
    int QE =0;
    Map<String,Map> allquestions ;



    int rbno  =0;
    int etno  =0;

    int qid =  1000;

    int rbvalid =  2000;

    int ethintid  = 3000;

    LinearLayout menu ;
    LinearLayout optionll ;
    LinearLayout selectedoptionll;
    LinearLayout showaddedoptions;
    LinearLayout mainform;
    LinearLayout done ;

    Map<String,Object> currentoptions ;


    public void tost(String msg)
    {
        Toast.makeText(getApplication(),msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createform);

        ff = FirebaseFirestore.getInstance();

        full2 =  new HashMap<>();

        menu =  findViewById(R.id.menu);

        currentoptions =  new HashMap<>();

        optionll = findViewById(R.id.option);

        selectedoptionll = findViewById(R.id.selectedoptions);

        showaddedoptions = findViewById(R.id.showaddedoptions);

        mainform = findViewById(R.id.mainform);

        done = findViewById(R.id.done);

        allquestions = new HashMap<>();


    }

    public void newquesion(View view) {

        currentoptions = new HashMap<>();

        rbno  = 0;
        etno  = 0;

        menu.removeAllViews();

        EditText et =  new EditText(getApplicationContext());

        ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setPadding(10,10,10,10);
        et.setBackgroundColor(Color.parseColor(("#fec8d8" )));
        et.setTextColor(Color.parseColor("#ffffff"));
        et.setLayoutParams(etlp);
        et.setHint("Enter your question : ");
        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        et.setId(qid);

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

        menu.addView(dcv2);


        CardView c0 =  new CardView(getApplicationContext());
        CardView.LayoutParams clp0 =  new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
        c0.setLayoutParams(clp0);
        c0.setElevation(10);
        c0.setRadius(10);
        c0.setBackgroundColor(Color.parseColor("#fec8d8"));
        clp0.setMargins(10,10,10,10);

        LinearLayout l0 = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams llp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l0.setLayoutParams(llp0);


        RadioGroup rg0 = new RadioGroup(getApplicationContext());
        RadioGroup.LayoutParams rglp1 = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT);
        rg0.setLayoutParams(rglp1);

        RadioButton rb1 =  new RadioButton(getApplicationContext());
        LinearLayout.LayoutParams  rblp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rb1.setLayoutParams(rblp1);
        rb1.setText("RADIO");
        rb1.setTextColor(Color.parseColor("#ffffff"));
        rg0.addView(rb1);

        RadioButton rb2 =  new RadioButton(getApplicationContext());
        LinearLayout.LayoutParams  rblp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rb2.setLayoutParams(rblp2);
        rb2.setText("EDITTEXT");
        rb2.setTextColor(Color.parseColor("#ffffff"));
        rg0.addView(rb2);

        l0.addView(rg0);
        c0.addView(l0);

        rg0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb =  findViewById(i);

                String optiontype = ""+rb.getText();

                if(!(optiontype.equals("")))
                {
                    optionll.removeAllViews();

                    if(optiontype.equals("RADIO"))
                    {

                        final  EditText et =  new EditText(getApplicationContext());

                        ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        et.setPadding(10,10,10,10);
                        et.setBackgroundColor(Color.parseColor(("#fec8d8" )));
                        et.setTextColor(Color.parseColor("#ffffff"));
                        et.setLayoutParams(etlp);
                        et.setHint("Enter radio button option value");
                        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        et.setId(rbvalid);

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

                        optionll.addView(dcv2);

                        CardView c1 =  new CardView(getApplicationContext());
                        CardView.LayoutParams clp1 =  new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                        c1.setLayoutParams(clp1);
                        c1.setElevation(10);
                        c1.setRadius(10);
                        c1.setBackgroundColor(Color.parseColor("#ffffff"));
                        clp1.setMargins(10,10,10,10);

                        LinearLayout l1 = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        l1.setLayoutParams(llp1);


                        Button tv =  new Button(getApplicationContext());
                        LinearLayout.LayoutParams tlp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tlp1.setMargins(5,5,5,5);
                        tv.setLayoutParams(tlp1);
                        tv.setText("Add this radio button option ");
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv.setTextColor(Color.parseColor("#FFFFFF"));
                        tv.setPadding(10,5,10,5);
                        tv.setBackground(getDrawable(R.drawable.pinkbtn));
                        l1.addView(tv);
                        c1.addView(l1);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String rval = ""+et.getText();

                                tost(""+rval);
                                if(!(rval.equals("")))
                                    addifnotpresent("rb",rval);
                            }
                        });

                        optionll.addView(c1);

                    }else
                    if(optiontype.equals("EDITTEXT"))
                    {

                        final   EditText et =  new EditText(getApplicationContext());

                        ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        et.setPadding(10,10,10,10);
                        et.setBackgroundColor(Color.parseColor(("#fec8d8" )));
                        et.setTextColor(Color.parseColor("#ffffff"));
                        et.setLayoutParams(etlp);
                        et.setHint("Enter the edittext hint value : ");
                        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        et.setId(ethintid);

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

                        optionll.addView(dcv2);

                        CardView c1 =  new CardView(getApplicationContext());
                        CardView.LayoutParams clp1 =  new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
                        c1.setLayoutParams(clp1);
                        c1.setElevation(10);
                        c1.setRadius(10);
                        c1.setBackgroundColor(Color.parseColor("#ffffff"));
                        clp1.setMargins(10,10,10,10);

                        LinearLayout l1 = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        l1.setLayoutParams(llp1);


                        Button tv =  new Button(getApplicationContext());
                        LinearLayout.LayoutParams tlp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tlp1.setMargins(5,5,5,5);
                        tv.setLayoutParams(tlp1);
                        tv.setText("Add this Edittext ");
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv.setTextColor(Color.parseColor("#FFFFFF"));
                        tv.setPadding(10,5,10,5);
                        tv.setBackground(getDrawable(R.drawable.pinkbtn));
                        l1.addView(tv);
                        c1.addView(l1);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view){
                                String ethintval =  ""+et.getText();
                                tost(""+ethintval);

                                if(!(ethintval.equals("")))
                                    addifnotpresent("et",ethintval);
                            }
                        });

                        optionll.addView(c1);
                    }
                }

            }
        });


        menu.addView(c0);




    }



    public void addifnotpresent(String type , String val)
    {
        Iterator it =  currentoptions.entrySet().iterator();

        int found  =0;
        while(it.hasNext())
        {
            Map.Entry el = (Map.Entry)it.next();

            String mkey =""+el.getKey();
            String mval = ""+el.getValue();

            String mtype = mkey.substring(0,2);
            tost(mtype);
            if(mval.equals(val) && type.equals(mtype))
            {
                found =1;
            }
        }

        if(found ==1)
        {
            tost(" already value present ! ");
        }else
        if(found ==0)
        {
            tost(" new value ! ");
            if(type.equals("rb"))
            {
                currentoptions.put(""+type+etno++,val);
            }else
            if(type.equals("et"))
            {
                currentoptions.put(""+type+rbno++,val);
            }

        }


        showaddedoptions();
    }

    public void showaddedoptions()
    {
        showaddedoptions.removeAllViews();

        Iterator it = currentoptions.entrySet().iterator();


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


        RadioGroup rg =  new RadioGroup(getApplicationContext());
        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        rg.setLayoutParams(lp);

        int rgfound  =0;
        while(it.hasNext())
        {
            Map.Entry el = (Map.Entry)it.next();
            String val  = ""+el.getValue();
            String key = ""+el.getKey();

            if((key.substring(0,2)).equals("rb"))
            {
                rgfound  = 1;
                RadioButton rb = new RadioButton(getApplicationContext());
                LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rb.setLayoutParams(lp2);

                rb.setText(""+val);

                rb.setTextColor(Color.WHITE);
                rb.setHighlightColor(Color.parseColor("#ffffff"));

                rg.addView(rb);
            }
        }

        if(rgfound == 1)
        {
            ll.addView(rg);
            cv.addView(ll);
            showaddedoptions.addView(cv);
        }


        // now adding the edittexts
        it = currentoptions.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry el = (Map.Entry)it.next();

            String key =(""+el.getKey()).substring(0,2);

            String val =  ""+el.getValue();

            if(key.equals("et"))
            {
                EditText et =  new EditText(getApplicationContext());


                ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                et.setPadding(10,10,10,10);
                et.setBackgroundColor(Color.parseColor(("#03bbff")));
                et.setTextColor(Color.parseColor("#ffffff"));
                et.setLayoutParams(etlp);
                et.setHint(""+val);

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

                showaddedoptions.addView(dcv2);
            }
        }




        CardView c1 =  new CardView(getApplicationContext());
        CardView.LayoutParams clp1 =  new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.WRAP_CONTENT);
        c1.setLayoutParams(clp1);
        c1.setElevation(10);
        c1.setRadius(10);
        c1.setBackgroundColor(Color.parseColor("#ffffff"));
        clp1.setMargins(10,10,10,10);

        LinearLayout l1 = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l1.setLayoutParams(llp1);


        Button tv =  new Button(getApplicationContext());
        LinearLayout.LayoutParams tlp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp1.setMargins(5,5,5,5);
        tv.setLayoutParams(tlp1);
        tv.setText("Add Selected Answer Option");
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setPadding(10,5,10,5);
        tv.setBackground(getDrawable(R.drawable.pinkbtn));
        l1.addView(tv);
        c1.addView(l1);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText questionet = findViewById(qid);
                question = ""+questionet.getText();

                if(question.equals(""))
                {
                    tost(" Question empty ! ");
                }else
                {

                    DialogInterface.OnClickListener di =  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            switch(i)
                            {
                                case DialogInterface.BUTTON_POSITIVE:
                                    tost(""+currentoptions);
                                    insertboth();

                                    break;

                                case  DialogInterface.BUTTON_NEGATIVE:
                                    tost("okay ! ");
                                    break;

                                default:
                                    tost("its nothing ! ");
                            }
                        }
                    };


                    AlertDialog.Builder ab = new AlertDialog.Builder(Createform.this).setMessage(" Ready to add question ? ")
                            .setPositiveButton("Yes",di).setNegativeButton("Cancel",di);

                    ab.show();
                }
            }
        });

        showaddedoptions.addView(c1);

    }

    public void insertboth()
    {

        full2 = new HashMap<>();

        full2.put("QUESTION",""+question);
        Iterator it = currentoptions.entrySet().iterator();


        int rno = 0;
        Map<String,Object> tempr = new HashMap<>();

        int etno =0;
        Map<String,Object> tempet = new HashMap<>();
        while(it.hasNext())
        {
            Map.Entry el = (Map.Entry)it.next();
            String key = ""+el.getKey();
            String val = ""+el.getValue();

            if((key.substring(0,2)).equals("rb"))
            {
                tempr.put("r"+rno,val);
                full2.put("r"+rno,val);

                rno++;
            }else
            if(key.substring(0,2).equals("et"))
            {
                tempet.put("e"+etno,val);
                full2.put("e"+etno,val);

                etno++;
            }
        }

        tost("tempr : "+tempr+" tempet : "+tempet);



        Iterator itr =  tempr.entrySet().iterator();

        if(tempr.size()>0)
        {
            if(!(allquestions.containsValue(full2)))
            {
                allquestions.put("r"+QR,full2);
                QR++;
            }
        }else
        if(tempet.size() >0)
        {
            if(!(allquestions.containsValue(full2)))
            {
                allquestions.put("e"+QE,full2);
                QE++;
            }
        }

        tost(""+allquestions);
        updateform();
    }




    public void updateform()
    {
        mainform.removeAllViews();

        Iterator mit = allquestions.entrySet().iterator();

        while(mit.hasNext())
        {
            Map.Entry elm = (Map.Entry)mit.next();

            String qsno = ""+elm.getKey();

            Map<String ,Object> options = (Map)elm.getValue();

            Iterator opit =  options.entrySet().iterator();

            String question = ""+options.get("QUESTION");



            TextView tv = new TextView(getApplicationContext());
            LinearLayout.LayoutParams lp0  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp0.setMargins(5,5,5,5);
            tv.setLayoutParams(lp0);

            tv.setText(""+question);
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

            mainform.addView(cv2);



            //--------------------------

            Iterator it = options.entrySet().iterator();


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


            RadioGroup rg =  new RadioGroup(getApplicationContext());
            LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5,5,5,5);
            rg.setLayoutParams(lp);

            int rgfound  =0;
            while(it.hasNext())
            {
                Map.Entry el = (Map.Entry)it.next();
                String val  = ""+el.getValue();
                String key = ""+el.getKey();

                if((key.substring(0,1)).equals("r"))
                {
                    rgfound  = 1;
                    RadioButton rb = new RadioButton(getApplicationContext());
                    LinearLayout.LayoutParams lp2 =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    rb.setLayoutParams(lp2);

                    rb.setText(""+val);

                    rb.setTextColor(Color.WHITE);
                    rb.setHighlightColor(Color.parseColor("#ffffff"));

                    rg.addView(rb);
                }
            }

            if(rgfound == 1)
            {
                ll.addView(rg);
                cv.addView(ll);
                mainform.addView(cv);
            }


            // now adding the edittexts
            it = options.entrySet().iterator();

            while(it.hasNext())
            {
                Map.Entry el = (Map.Entry)it.next();

                String key =(""+el.getKey()).substring(0,1);

                String val =  ""+el.getValue();

                if(key.equals("e"))
                {
                    EditText et =  new EditText(getApplicationContext());


                    ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    et.setPadding(10,10,10,10);
                    et.setBackgroundColor(Color.parseColor(("#03bbff")));
                    et.setTextColor(Color.parseColor("#ffffff"));
                    et.setLayoutParams(etlp);
                    et.setHint(""+val);

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


                    mainform.addView(dcv2);
                }
            }

        }
    }



    public void done(View view) {

        DialogInterface.OnClickListener di =  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(i)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        formname =  ""+et.getText();

                        saveeverything();
                        break;

                    case  DialogInterface.BUTTON_NEGATIVE:
                        tost("okay.. not saving !");
                        break;

                    default:
                        tost("its nothing ! ");
                }
            }
        };

        et=  new EditText(getApplicationContext());
        ViewGroup.LayoutParams etlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setPadding(10,10,10,10);
        et.setBackgroundColor(Color.parseColor(("#03bbff")));
        et.setTextColor(Color.parseColor("#ffffff"));
        et.setLayoutParams(etlp);
        et.setHint(" Enter form name please ! ");


        AlertDialog.Builder ab = new AlertDialog.Builder(Createform.this).setMessage(" Ready to add question ? ")
                .setPositiveButton("Yes",di).setNegativeButton("Cancel",di);

        AlertDialog ad = ab.create();

        ad.setView(et);

        ad.show();


    }

    public void saveeverything()
    {
        if(!(formname.equals("")))
        {

            Iterator it = allquestions.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry el = (Map.Entry) it.next();

                String key = ""+el.getKey();

                Map val = (Map) el.getValue();

                ff.collection("DYNFORMS").document(formname).collection("QUESTIONS").document(key).set(val);

                Map<String ,Object> dummy = new HashMap<>();
                dummy.put("s","m");
                ff.collection("DYNFORMS").document(formname).set(dummy);

            }

        }else
            tost("enter the formname please !");
    }
}
