package com.example.uipro;



import info.hoang8f.android.segmented.SegmentedGroup;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;


//>>

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.pdf.parser.Line;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Intent.createChooser;
//>>

public class ShareAnalysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_analysis);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

oncreate2();

    }

//>>


    Set<String> bar;

    String charttype ="Pie";

    EditText phno ;

    Set<String> hs ;

    String selected  = "";

    LinearLayout ll ;
    String ev;

    ArrayList<String> selev ;

    Set<String> recievers;

    LinearLayout emailsll;

    ArrayList<String> ar ;

    EditText et ;


    LinearLayout selectedlist;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }



    SegmentedGroup rg;
    SegmentedGroup segmentImg;

    public void oncreate2()
    {

        selectedlist =  findViewById(R.id.selectedlist);
        ar  = new ArrayList<>();

        ll = findViewById(R.id.files);

        selev  = new ArrayList<>();

        rg =  findViewById(R.id.charttype);

        phno = findViewById(R.id.phoneno);

        hs = new HashSet<String>();

        bar = new HashSet<String>();

        recievers =  new HashSet<>();

        emailsll =  findViewById(R.id.recievers); // the lineatlayout of all the email list ..

        et  = findViewById(R.id.emailet);

        //    getevents();


        //   sendEmailWithAttachment(sendemail.this,new String[]{"vishukaushik174@gmail.com"},"sub","dummymessage","");



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton  rb =  findViewById(id);


                String str = ""+rb.getText();

                charttype = ""+rb.getText();

                getevents(str);

                listselectedfiles();

            }
        });

    }




    public void getevents(final String charttype)
    {


        FirebaseFirestore ff  = FirebaseFirestore.getInstance();



        ff.collection("/ADMINEVENTSTILLNOW").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                ll.removeAllViews();

                for(DocumentSnapshot d  :  queryDocumentSnapshots.getDocuments())
                {
                    ar.add(d.getId());
                    ev = d.getId();
                    tost(""+ar);

                    final  TextView tv =  new TextView(getApplicationContext());
                    LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(2,5,2,5);
                    tv.setLayoutParams(lp);
                    tv.setText(ev);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTextSize(20);


                    final String path = Environment.getExternalStorageDirectory()+"/"+charttype+"_"+ev+".pdf";

                    int foundonphone = 0;
                    File file  = new File(path);

                    if(file.exists())
                    {
                        tost(""+file+"found");
                        foundonphone =1;

                    }else
                    {
                        tost("you need to first download "+file);
                        foundonphone =0;
                    }


                    if(foundonphone == 1)
                    {

                        tv.setBackgroundColor(Color.parseColor("#03bbff"));
                        tv.setTextColor(Color.parseColor("#ffffff"));


                        //-----------------

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String txt = ""+((TextView)view).getText();

                                if(charttype.equals("Pie"))
                                {
                                    hs.add(""+txt);
                                    tost(""+hs);

                                }else
                                {
                                    bar.add(""+txt);
                                    tost(""+bar);
                                }

                                listselectedfiles();

                                Iterator it  = selev.iterator();

                                selected =  ev;

                                int found  =0;

                                while(it.hasNext())
                                {
                                    String el = ""+it.next();

                                    String me = ""+tv.getText();
                                    if(el.equals(ev))
                                    {
                                        found  =1;
                                    }

                                }

                                // tost(""+found);
                                if(found == 0)
                                {
                                    selev.add(ev);

                                }

                                //tost(""+selev);
                            }
                        });


                        //-----------------

                    }
                    else
                    {

                        tv.setBackgroundColor(Color.parseColor("#0099cc"));
                        tv.setTextColor(Color.parseColor("#ffffff"));

                        //-------------------

                        //---------------

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                tost("download "+path+"first");

                                Intent i;
                                if(charttype.equals("Pie"))
                                {
                                    i= new Intent(ShareAnalysis.this, PieChartMine.class);
                                }else
                                {
                                    i= new Intent(ShareAnalysis.this,BarChartMine.class);
                                }

                                i.putExtra("evname",""+((TextView)view).getText());
                                i.putExtra("action","download");
                                startActivity(i);

                            }
                        });

                        // ----
                    }

                    ll.addView(tv);

                }
            }
        });


        tost("got events till now : "+ar);

    }





    public void listselectedfiles()
    {


        Iterator it ;

        if (charttype.equals("Pie")) {

            it =  hs.iterator();

        }else
        {
            it =  bar.iterator();
        }

        selectedlist.removeAllViews();

        while (it.hasNext())
        {
            final  String val =  ""+it.next();

            final  TextView tv =  new TextView(getApplicationContext());
            LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2,5,2,5);
            tv.setLayoutParams(lp);
            tv.setText(val);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(20);
            tv.setBackgroundColor(Color.parseColor("#fffdd0"));
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked



                                    Iterator it = hs.iterator();

                                    int needtoref =0 ;

                                    int index  =0;

                                    if(charttype.equals("Pie"))
                                        hs.remove(val);

                                    if(charttype.equals("Bar"))
                                        bar.remove(val);


                                    /*
                                    while (it.hasNext()) {

                                        String sval = "" + it.next();

                                        if (sval.equals("" + val)) {
                                            tost("found");
                                            hs.remove(val);
                                            needtoref =1;


                                        }
                                        index++;

                                    }
                                     */

                                    if(needtoref ==1)
                                    {
                                        //   createlist();
                                    }
                                    listselectedfiles();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    tost("deletion cancelled ");
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShareAnalysis.this);
                    builder.setMessage("Are you sure to delete < "+val+" >from the live events ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });


            selectedlist.addView(tv);
        }

    }



    public void whatsappmultiple(View v)   // sending multiple files to a particular user given by a whatsapp number ..
    {

        try
        {
        // to send to a particular number trough whatsapp


        //    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //    StrictMode.setVmPolicy(builder.build());

            phno=  findViewById(R.id.phonenoet);


        String toNumber = "91"+phno.getText();                       //9958197992"; // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "");

        if (toNumber.equals("91")) {

            phno.setError("Please enter the number ! ");
        }
        else {
            String toNumber2 = "919953245840"; // contains spaces.
            toNumber2 = toNumber2.replace("+", "").replace(" ", "");


            //File fl = new File(""+Environment.getExternalStorageDirectory()+"/cuteexcel.xls");

            ArrayList<Uri> uris = new ArrayList<>();
            //uris.add(Uri.fromFile(fl));

            //File fl2  = new File(""+Environment.getExternalStorageDirectory()+"/Pie_AWS.pdf");
            //uris.add(Uri.fromFile(fl2));

            Iterator it = hs.iterator();
            while (it.hasNext()) {

                String evname  = ""+it.next();

                String el = "" + Environment.getExternalStorageDirectory() + "/Pie_" + evname + ".pdf";
                File fl = new File(el);
                uris.add(Uri.fromFile(fl));

                String ss = "" + Environment.getExternalStorageDirectory() + "/SakshiPie" + evname + ".jpg";
                File f2 = new File(ss);
                uris.add(Uri.fromFile(f2));

            }

            Iterator it2 = bar.iterator();
            while (it2.hasNext()) {

                String evname = ""+it2.next();

                String el = "" + Environment.getExternalStorageDirectory() + "/Bar_" + evname + ".pdf";
                File fl = new File(el);
                uris.add(Uri.fromFile(fl));

                String ss = "" + Environment.getExternalStorageDirectory() + "/SakshiBar" + evname + ".jpg";
                File f2 = new File(ss);
                uris.add(Uri.fromFile(f2));

            }


            String message = "Here are your Analysis Stuff ! ";
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.putExtra(Intent.EXTRA_STREAM, uris);
            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");

            /*
ArrayList<Uri> mam = new ArrayList<>();

mam.add(Uri.parse("smsto:"+toNumber));   //+toNumber + "@s.whatsapp.net"));
mam.add(Uri.parse("smsto:"+toNumber2));   //+toNumber2 + "@s.whatsapp.net"));
sendIntent.putExtra("jid",mam);
             */


            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setPackage("com.whatsapp");

            String [] mimeTypes = {"image/png", "image/jpg","image/jpeg","application/excel","application/pdf"};
            sendIntent.setType("*/*");
            sendIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);


//            sendIntent.setType("application/excel|application/pdf");
            ShareAnalysis.this.startActivity(sendIntent);


        }
        }
        catch (Exception e)
        {
            tost("error: "+e.getMessage());
        }
    }




    public void sendsheet(View view) {

        //  String[] array = recievers.toArray(new String[0]);

        //   sendEmailWithAttachment(sendemail.this,array,"sub","dummymessage","");


        try {


            List<String> filepaths = new ArrayList<>();

            //  String path  = ""+Environment.getExternalStorageDirectory()+"/Pie__AWS.pdf";
            //  String path2  = ""+Environment.getExternalStorageDirectory()+"/cuteexcel.xls";

            //  filepaths.add(path);
// filepaths.add(path2);

            Iterator it = hs.iterator();

            while (it.hasNext()) {
                String el = "" + it.next();
                String pathpdf = "" + Environment.getExternalStorageDirectory() + "/Pie_" + el + ".pdf";
                filepaths.add(pathpdf);

                String pathss = "" + Environment.getExternalStorageDirectory() + "/SakshiPie" + el + ".jpg";
                filepaths.add(pathss);


            }


            Iterator i2 = bar.iterator();

            while (i2.hasNext()) {
                String el = "" + i2.next();
                String pathpdf = "" + Environment.getExternalStorageDirectory() + "/Bar_" + el + ".pdf";
                filepaths.add(pathpdf);

                String pathss = "" + Environment.getExternalStorageDirectory() + "/SakshiBar" + el + ".jpg";
                filepaths.add(pathss);

            }


            Iterator it2 = recievers.iterator();

            String emails[] = new String[recievers.size()];

            int i = 0;
            while (it2.hasNext()) {
                String el = "" + it2.next();

                emails[i++] = el;

            }

            tost(">>>>>f: "+filepaths+" emals: "+emails);

            email(ShareAnalysis.this, emails, "", "Analysis Share", "Here are your Analysis", filepaths);

        }
        catch(Exception e)
        {
            tost(""+e.getMessage());
        }
    }


    public void createlist()
    {

        Iterator it =  recievers.iterator();

        emailsll.removeAllViews();

        while (it.hasNext())
        {
            final  String val =  ""+it.next();

            final  TextView tv =  new TextView(getApplicationContext());
            LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2,5,2,5);
            tv.setLayoutParams(lp);

            tv.setPadding(5,5,5,5);
            tv.setText(val);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(20);
            tv.setBackgroundColor(Color.parseColor("#ffffff"));
            tv.setTextColor(Color.parseColor("#03bbff"));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked


                                    Iterator it = recievers.iterator();

                                    int needtoref =0 ;

                                    recievers.remove(""+val);

                                    /*
                                    int index  =0;
                                    while (it.hasNext()) {

                                        String sval = "" + it.next();

                                        if (sval.equals("" + val)) {
                                            tost("found");
                                            recievers.remove(index);
                                        needtoref =1;


                                        }
                                        index++;

                                    }

                                    if(needtoref ==1)
                                    {
                                     //   createlist();
                                    }
                                    */


                                    createlist();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    tost("deletion cancelled ");
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShareAnalysis.this);
                    builder.setMessage("Are you sure to delete < "+val+" >from the live events ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });


            emailsll.addView(tv);
        }
    }


    public void addreciever(View view) {


        String eml = ""+et.getText();

        if(!(eml.equals("")))
        {
            recievers.add(eml);
        }
/*

Iterator itt =  recievers.iterator();

int already = 0 ;

if(eml.equals(""))
{
    already = 1;
}
while(itt.hasNext())
{
    String tbc = ""+itt.next();
    if(eml.equals(""+tbc))
    {
        already =1;
    }
}
if(already == 0 )
{
    recievers.add(""+et.getText());

    tost(""+recievers);

}
*/

        createlist();


    }





    @Override
    protected void onResume() {
        super.onResume();


        getevents(charttype);

        listselectedfiles();


    }




    public void whatsappwithoutrecievers(View view)  // multiple recievers send via whatsapp
    {

        String toNumber = "919958197992"; // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "");

        String toNumber2 = "919953245840"; // contains spaces.
        toNumber2 = toNumber2.replace("+", "").replace(" ", "");


        //File fl = new File(""+Environment.getExternalStorageDirectory()+"/cuteexcel.xls");

        ArrayList<Uri> uris =  new ArrayList<>();
        //uris.add(Uri.fromFile(fl));

        // File fl2  = new File(""+Environment.getExternalStorageDirectory()+"/Pie_AWS.pdf");
        // uris.add(Uri.fromFile(fl2));

        Iterator it =  hs.iterator();
        while(it.hasNext())
        {
            String evname =  ""+it.next();

            String el = ""+Environment.getExternalStorageDirectory()+"/Pie_"+evname+".pdf";
            File fl  = new File(el);
            uris.add(Uri.fromFile(fl));

            String ss = ""+Environment.getExternalStorageDirectory()+"/SakshiPie"+evname+".jpg";

            File f2  = new File(ss);
            if(f2.exists())
            {
                uris.add(Uri.fromFile(f2));
            }
        }

        Iterator it2 =  bar.iterator();
        while(it2.hasNext())
        {

            String evname = ""+it2.next();

            String el = ""+Environment.getExternalStorageDirectory()+"/Bar_"+evname+".pdf";

            File fl  = new File(el);
            uris.add(Uri.fromFile(fl));

            String ss = ""+Environment.getExternalStorageDirectory()+"/SakshiBar"+evname+".jpg";

            File f2  = new File(ss);
            if(f2.exists())
            {
                uris.add(Uri.fromFile(f2));
            }

        }

        String message ="Whatsapp Analysis Share ";
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uris);
//        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");

        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");

        String [] mimeTypes = {"image/png", "image/jpg","image/jpeg","application/excel","application/pdf"};
        sendIntent.setType("*/*");
        sendIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

    //    sendIntent.setType("application/excel|application/pdf");
        ShareAnalysis.this.startActivity(sendIntent);

    }

    public void email(Context context, String emailTo[], String emailCC, String subject, String emailText, List<String> filePaths)
    {
        //need to "send multiple" to get more than one attachment

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {


            final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);

//            emailIntent.setType("application/pdf");

            emailIntent.setType("*/*");

            String [] mimeTypes = {"image/png", "image/jpg","image/jpeg","application/excel","application/pdf"};
            emailIntent.setType("*/*");
            emailIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);


            // MimeTypeMap type = MimeTypeMap.getSingleton();
            // emailIntent.setType(type.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(filePaths.get(0))));

            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    emailTo);
            emailIntent.putExtra(android.content.Intent.EXTRA_CC,
                    new String[]{emailCC});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
            //has to be an ArrayList


            ArrayList<Uri> uris = new ArrayList<Uri>();
            //convert from paths to Android friendly Parcelable Uri's
            for (String file : filePaths) {
                File fileIn = new File(file);
                if(fileIn.exists())
                {
                    Uri u = Uri.fromFile(fileIn);
                    uris.add(u);
                }
            }

            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

            tost("" + uris);

            startActivity(createChooser(emailIntent, "Send mail..."));
        }
        catch(Exception e)
        {
            tost(e.getMessage());
        }

    }


//>>
}
