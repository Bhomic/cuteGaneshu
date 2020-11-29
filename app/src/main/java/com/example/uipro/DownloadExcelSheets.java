package com.example.uipro;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.sanojpunchihewa.glowbutton.GlowButton;


//>>



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import info.hoang8f.android.segmented.SegmentedGroup;


//>>

public class DownloadExcelSheets extends AppCompatActivity {

    String sheetname ;

    int allowdownload  = 1;

    SegmentedGroup rg;

    ProgressBar pb  ;


    TextView tv ;

    public void write(String msg)
    {
     /*
        String old = ""+tv.getText();

        old+= msg
;
    tv.setText(old);
   */
    }

    public void newtost(String msg )
    {
        Toast.makeText(DownloadExcelSheets.this,msg, Toast.LENGTH_LONG).show();
    }

    int evententries  =0;
    int ct  =  0;

    boolean writeallowed  = true;


    LinearLayout ll ;

    GlowButton glbtn;

    List<String> radiolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_excel_sheets);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rg =  findViewById(R.id.charttype);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb =  findViewById(id);


                String str = ""+rb.getText();

                sheetname = ""+rb.getText();

                if(sheetname.equals("Radio"))
                {
                    readsheet(0);
                }else
                    if(sheetname.equals("EditText"))
                    {
                        readsheet(1);
                    }
            }
        });

        pb =  findViewById(R.id.progressBar);

        tv =  findViewById(R.id.test);

        ll = findViewById(R.id.exll);

glbtn =  findViewById(R.id.readexcel);

      //  addbuttons();


        oncreate2();


    }

        public void addbuttons()
        {
/*            GlowButton gb = new GlowButton(getApplicationContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            gb.setLayoutParams(lp);

            gb.setPadding(20,20,20,20);
            gb.setText("Button");
gb.setClickable(true);


 */


            MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
            LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,20,0,0);
            b.setLayoutParams(lp);

            b.setBackgroundColor(Color.parseColor("#034472"));
            b.setBorderColor(Color.parseColor("#fffdd0"));
            b.setBorderWidth(2);
            b.setText("event name ");
            b.setTextColor(Color.parseColor("#fffdd0"));
            b.setPadding(10,10,10,10);
            b.setRadius(10);
            b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
            ll.addView(b);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialFancyButton b = (MaterialFancyButton) v;
                    String event = "" + (b.getText());


                   // all downloading work for te event :




                }
            });
            }



            //>>
int headno = 2;

int etrowno = 0;
    Row editrow;

    Sheet etsheet ;

    int rq = 0;

    int testrowno =  1;
    int testcolno =  4;

    Row testrow  ;


    List rowlist ;

    int colno = 0;
    Map<String ,Object> questions;

    Map<String ,Object> radioq;

    int a = 2;
    Row row0;

    TableLayout tl ;

    String filename = "AWS";


    int newrow = 0;

    //---------------------



    //---------------------


    CellStyle cs2;

    Cell c2 = null;



    //====================================


    String course ="";

    Workbook wb;


    String eventid;

    boolean success = false;

    Sheet sheet1;




    CellStyle cs;

    FirebaseFirestore ff ;

    static String TAG = "ExelLog";


    public  void tost(String msg)
    {
     //   Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


    //------------------------------


    LinearLayout eventsll;


    String event  ="Cs";



    public void oncreate2()
{

    rowlist  = new ArrayList<>();


    questions =   new HashMap<>();
    radioq =  new HashMap<>();


    ff = FirebaseFirestore.getInstance();


    tl = findViewById(R.id.tl);


    eventsll = findViewById(R.id.exll);

    ff = FirebaseFirestore.getInstance();

    ff.collection("ADMINEVENTSTILLNOW").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments())
            {
                final String event = ""+d.getId();

                final String text  = ""+event;
              //>>

                MaterialFancyButton b =  new MaterialFancyButton(getApplicationContext());
                LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,30,0,0);
                b.setLayoutParams(lp);

                b.setBackgroundColor(Color.parseColor("#034472"));
                b.setBorderColor(Color.parseColor("#fffdd0"));
                b.setBorderWidth(2);
                b.setText(""+text);
                b.setTextColor(Color.parseColor("#fffdd0"));
                b.setPadding(10,10,10,10);
                b.setRadius(10);
                b.setFocusBackgroundColor(Color.parseColor("#ffffff"));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialFancyButton b = (MaterialFancyButton) v;
                        String event = "" + (b.getText());
                        // all downloading work for te event :


                        filename =  event;
                        makesheetfor(event);
                        glbtn.setText("Read "+filename);

                    }
                });



                //>>



                eventsll.addView(b);



            }
        }
    });
}

int headr =  2;
    int headet  =  2;

public void setheading(String eventnme)
{


    Row row0t =  row0;
    Row editrowt =  editrow;


    c2 = row0t.createCell(0);
    c2.setCellValue("Course");
    c2.setCellStyle(cs2);


    c2 = row0t.createCell(1);
    c2.setCellValue("Email");
    c2.setCellStyle(cs2);


    c2 = editrowt.createCell(0);
    c2.setCellValue("Course");
    c2.setCellStyle(cs2);


    c2 = editrowt.createCell(1);
    c2.setCellValue("Email");
    c2.setCellStyle(cs2);


    //>>-----


    ff.document("FORMS/IQSE/QUESTIONS/r0").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                              @Override
                                                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                  if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                      Map<String , Object >hm =  documentSnapshot.getData();

                                                                                      String question  = ""+ hm.get("QUESTION");

                                                                                      c2 = row0t.createCell(2);
                                                                                      c2.setCellValue("" + question);
                                                                                      c2.setCellStyle(cs2);
                                                                                  }
                                                                              }
                                                                          }
    );
    ff.document("FORMS/IQSE/QUESTIONS/r1" ).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                              @Override
                                                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                  if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                      Map<String , Object >hm =  documentSnapshot.getData();

                                                                                      String question  = ""+ hm.get("QUESTION");

                                                                                      c2 = row0t.createCell(3);
                                                                                      c2.setCellValue("" + question);
                                                                                      c2.setCellStyle(cs2);
                                                                                  }
                                                                              }
                                                                          }
    );   ff.document("FORMS/IQSE/QUESTIONS/r2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                       @Override
                                                                                       public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                           if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                               Map<String , Object >hm =  documentSnapshot.getData();

                                                                                               String question  = ""+ hm.get("QUESTION");

                                                                                               c2 = row0t.createCell(4);
                                                                                               c2.setCellValue("" + question);
                                                                                               c2.setCellStyle(cs2);
                                                                                           }
                                                                                       }
                                                                                   }
);   ff.document("FORMS/IQSE/QUESTIONS/r3").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                                    Map<String , Object >hm =  documentSnapshot.getData();

                                                                                                    String question  = ""+ hm.get("QUESTION");

                                                                                                    c2 = row0t.createCell(5);
                                                                                                    c2.setCellValue("" + question);
                                                                                                    c2.setCellStyle(cs2);
                                                                                                }
                                                                                            }
                                                                                        }
);   ff.document("FORMS/IQSE/QUESTIONS/r4" ).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                 @Override
                                                                                                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                     if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                                         Map<String , Object >hm =  documentSnapshot.getData();

                                                                                                         String question  = ""+ hm.get("QUESTION");

                                                                                                         c2 = row0t.createCell(6);
                                                                                                         c2.setCellValue("" + question);
                                                                                                         c2.setCellStyle(cs2);
                                                                                                     }
                                                                                                 }
                                                                                             }
);

//=========================================================



    ff.document("FORMS/IQSE/QUESTIONS/q0").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                              @Override
                                                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                  if (documentSnapshot != null && documentSnapshot.exists()) {

                                                                                      Map<String , Object >hm =  documentSnapshot.getData();

                                                                                      String question  = ""+ hm.get("QUESTION");

                                                                                      c2 = editrowt.createCell(2);
                                                                                      c2.setCellValue("" + question);
                                                                                      c2.setCellStyle(cs2);
                                                                                  }
                                                                              }
                                                                          }
    );



    ff.document("FORMS/IQSE/QUESTIONS/q1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                              @Override
                                                                              public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                  if (documentSnapshot != null && documentSnapshot.exists()) {


                                                                                      Map<String , Object >hm =  documentSnapshot.getData();

                                                                                      String question  = ""+ hm.get("QUESTION");

                                                                                      c2 = editrowt.createCell(3);
                                                                                      c2.setCellValue("" + question);
                                                                                      c2.setCellStyle(cs2);

                                                                                  traverseall(eventnme);

                                                                                  }
                                                                              }




                                                                          }
    );



    //>>-----
}



public void thunderradio( String eventname, String cname , String user)
{

    ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").document("" + user).collection("RESPONSES").document("RADIO").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot != null && documentSnapshot.exists()) {

                testrow = sheet1.createRow(testrowno++);

                rowlist.add(testrow);

                testcolno = 2;


                Map<String, Object> hm = documentSnapshot.getData();


                c2 = testrow.createCell(0);
                c2.setCellValue(cname);
                c2.setCellStyle(cs);


                c2 = testrow.createCell(1);
                c2.setCellValue(user);
                c2.setCellStyle(cs);


                for (int i = 0; i < 5; i++) {
                    //  String docid = radiolist.get(i);

                    String docid = "r" + i;

                    String radresp = "" + hm.get("" + docid);

                    tost("" + radresp);


                    if (radresp != null) {

                        c2 = testrow.createCell(testcolno++);
                        c2.setCellValue(radresp);
                        c2.setCellStyle(cs);

                        incr(eventname);
                    }

                }

            }
        }
    });
}


public void thunderet( String eventname,String cname ,String user) {

    ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").document("" + user).collection("RESPONSES").document("EDITTEXT").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {

            if (documentSnapshot != null && documentSnapshot.exists()) {

                editrow = etsheet.createRow(etrowno++);

                colno = 0;

                Map<String, Object> hm = documentSnapshot.getData();

                c2 = editrow.createCell(0);
                c2.setCellValue(cname);
                c2.setCellStyle(cs);


                c2 = editrow.createCell(1);
                c2.setCellValue(user);
                c2.setCellStyle(cs);


                Map<String, Object> hm2 = documentSnapshot.getData();


                String etval = "" + hm2.get("et0");
                c2 = editrow.createCell(2);
                c2.setCellValue(etval);
                c2.setCellStyle(cs);
incr(eventname);

                etval = "" + hm2.get("et1");
                c2 = editrow.createCell(3);
                c2.setCellValue(etval);
                c2.setCellStyle(cs);
incr(eventname);

             //   savefile(eventname);
            }

        }
    });
}


public void savefile(String eventname)
{


        sheet1.setColumnWidth(0, (15 * 300));
        sheet1.setColumnWidth(1, (15 * 800));
        sheet1.setColumnWidth(2, (15 * 800));
        sheet1.setColumnWidth(3, (15 * 800));
        sheet1.setColumnWidth(4, (15 * 800));
        sheet1.setColumnWidth(5, (15 * 800));
        sheet1.setColumnWidth(6, (15 * 800));


        etsheet.setColumnWidth(0, (15 * 300));
        etsheet.setColumnWidth(1, (15 * 800));
        etsheet.setColumnWidth(2, (15 * 800));
        etsheet.setColumnWidth(3, (15 * 800));


        File file = new File(Environment.getExternalStorageDirectory(), "" + eventname + ".xls");
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            tost("" + file);
            success = true;



        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
            tost("" + e);
            pb.setVisibility(View.INVISIBLE);

            allowdownload = 1;

        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
            tost("" + e);
            pb.setVisibility(View.INVISIBLE);


            allowdownload = 1;

        } finally {
            try {
                if (null != os)
                    os.close();
                newtost("file saved successfully ");

allowdownload = 1;
                pb.setVisibility(View.INVISIBLE);
            } catch (Exception ex) {

                tost("exc : " + ex.getMessage());
                pb.setVisibility(View.INVISIBLE);

                allowdownload = 1;
            }
        }


}




public void traverseall(String eventname)
{

    ff.collection("DYNEVE").document("" + eventname).collection("COURSES").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots1) {

            if (queryDocumentSnapshots1.size() > 0) {

                for (DocumentSnapshot d : queryDocumentSnapshots1) {
                    colno = 0;
                    final String cname = "" + d.getId();

                    ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(final QuerySnapshot queryDocumentSnapshots2) {

                            evententries += (queryDocumentSnapshots2.size());
                            write("\n" + queryDocumentSnapshots1.size() + " : " + queryDocumentSnapshots2.size());


                            colno = 0;
                            testrowno = 1;
                            for (DocumentSnapshot d : queryDocumentSnapshots2.getDocuments()) {
                                final String user = "" + d.getId();

                                thunderradio(eventname, cname, user);

                                thunderet(eventname, cname, user);


                            }


                        }
                    });
                }
            }else
            {
                pb.setVisibility(View.INVISIBLE);
                allowdownload = 1;
            }
        }
    }
    );

            }



public void refreshvars(String eventname )
{
    headno = 2;

    a = 2;
     rq = 0;

     testrowno =  1;
     testcolno =  4;
    colno = 0;


    wb = new HSSFWorkbook();
    sheet1 = wb.createSheet("Radio Responses for " + eventname);
    row0 = sheet1.createRow(0);


    etsheet = wb.createSheet("EditText Responses for " + eventname);

    editrow = etsheet.createRow(0);

    etrowno = 1;


    cs2 = wb.createCellStyle();
    cs2.setFillForegroundColor(HSSFColor.AQUA.index);
    cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



    cs = wb.createCellStyle();
    cs.setFillForegroundColor(HSSFColor.LAVENDER.index);
    cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


    radiolist = new ArrayList<String>();

}


    public void makesheetfor(final String eventname) {

    if(allowdownload == 1) {
        allowdownload = 0;

        pb.setVisibility(View.VISIBLE);

        ct = 0;
        evententries = 0;

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return;
        }

        refreshvars(eventname);

        setheading(eventname);


    }else
    {
        newtost(" plz wait for some seconds ");
    }
    }




    public void incr(String eventname)
    {
        ct++;

        tost(""+ct+" == "+(evententries*7));

 write("\n "+ct+" == "+(evententries*7));

        if(ct ==  evententries*7)
        {

            newtost(" gonna save our file ");

            savefile(eventname);
        }
    }

    //.................................
    public void makesheetforprev(final String eventname) {


  refreshvars(eventname);



        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return;
        }

   /*
    c2 = row0.createCell(0);
    c2.setCellValue("Event");
    c2.setCellStyle(cs2);
*/


//---------------------------------------

setheading(eventname);




/*
        ff.collection("FORMS/IQSE/QUESTIONS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                //hardcoded function to make the entries of the  questions in the excel sheet ..




                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
              final String q = d.getId();


              if((!q.equals("")) && (""+q.charAt(0)).equals("r")) {
                  final int A = a;
                  ff.document("FORMS/IQSE/QUESTIONS/" + q).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                      @Override
                      public void onSuccess(DocumentSnapshot documentSnapshot) {
                          if (documentSnapshot != null && documentSnapshot.exists()) {
                              Map<String, Object> hm = documentSnapshot.getData();
                              String question = "" + hm.get("QUESTION");

                              c2 = row0.createCell(a);
                              c2.setCellValue("" + question);
                              c2.setCellStyle(cs2);

                              a++;

                                if(q.charAt(0) == 'r')
                                  {
                                  radioq.put("RQ" + (rq++), q);
                                  radiolist.add(q);

                                  sheet1.setColumnWidth(rq + 2, (15 * 500));
                              }

                          }
                      }
                  });



              }else if(q.charAt(0) == 'q')
              {
                  ff.document("FORMS/IQSE/QUESTIONS/" + q).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                      @Override
                      public void onSuccess(DocumentSnapshot documentSnapshot) {
                          if (documentSnapshot != null && documentSnapshot.exists()) {
                              Map<String, Object> hm = documentSnapshot.getData();
                              String question = "" + hm.get("QUESTION");

                              c2 = editrow.createCell(headno);
                              c2.setCellValue("" + question);
                              c2.setCellStyle(cs2);

                              headno++;


                              if (q.charAt(0) == 'q')
                                  questions.put("Q" + ((headno-2)), q);


                          }
                      }
                  });
              }

                }

*/

                //QQQQQQQQQQQQQQQQQQQQQQQQ


                ff.collection("DYNEVE").document("" + eventname).collection("COURSES").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (DocumentSnapshot d : queryDocumentSnapshots) {


                            // getting the coourse from the document d  amonng the courses stored in  queryDocumentSnapshot

                            colno = 0;
                            final String cname = "" + d.getId();

                            ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {


                                    colno = 0;

                                    testrowno =  1;

                                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                        final String user = "" + d.getId();




                                        ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").document("" + user).collection("RESPONSES").document("RADIO").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                    testrow = sheet1.createRow(testrowno++);

                                                    rowlist.add(testrow);

                                                    testcolno = 2;


                                                    Map<String, Object> hm = documentSnapshot.getData();


                                                    c2 = testrow.createCell(0);
                                                    c2.setCellValue(cname);
                                                    c2.setCellStyle(cs);


                                                    c2 = testrow.createCell(1);
                                                    c2.setCellValue(user);
                                                    c2.setCellStyle(cs);


                                                    for(int i =0;i<  5;i++)
                                                 {
                                                   //  String docid = radiolist.get(i);

                                                     String  docid = "r"+i;

                                                     String radresp = "" + hm.get("" + docid);

                                                     tost(""+radresp);


                                                     if (radresp != null) {

                                                         c2 = testrow.createCell(testcolno++);
                                                         c2.setCellValue(radresp);
                                                         c2.setCellStyle(cs);
                                                     }

                                                 }


                                                    /*
                                                    Iterator it = radioq.entrySet().iterator();

                                                    while (it.hasNext()) {
                                                        Map.Entry el = (Map.Entry) it.next();

                                                        String qte = "" + el.getValue();
                                                        String radresp = "" + hm.get("" + qte);

                                                        tost(""+radresp);


                                                        if (radresp != null) {

                                                            c2 = testrow.createCell(testcolno++);
                                                            c2.setCellValue(radresp);
                                                            c2.setCellStyle(cs);
                                                        }
                                                    }


                                                     */

                                                    //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ

                                                    ff.collection("DYNEVE").document("" + eventname).collection("COURSES").document("" + cname).collection("USERS").document("" + user).collection("RESPONSES").document("EDITTEXT").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            if (documentSnapshot != null && documentSnapshot.exists()) {


                                                                //                                                                     row0 = sheet1.createRow(++newrow);

                                                                editrow = etsheet.createRow(etrowno++)                            ;

                                                                colno = 0;

                                                                Map<String, Object> hm = documentSnapshot.getData();

                                                                c2 = editrow.createCell(0);
                                                                c2.setCellValue(cname);
                                                                c2.setCellStyle(cs);


                                                                c2 = editrow.createCell(1);
                                                                c2.setCellValue(user);
                                                                c2.setCellStyle(cs);


                                                                Map<String, Object> hm2 = documentSnapshot.getData();




                                                                String etval = ""+hm2.get("et0");
                                                                c2 = editrow.createCell(2);
                                                                c2.setCellValue(etval);
                                                                c2.setCellStyle(cs);


                                                                etval = ""+hm2.get("et1");
                                                                c2 = editrow.createCell(3);
                                                                c2.setCellValue(etval);
                                                                c2.setCellStyle(cs);

                                                                /*
                                                                Iterator it = questions.entrySet().iterator();

                                                                int a = 0;
                                                                while (it.hasNext()) {

                                                                    Map.Entry el = (Map.Entry) it.next();

                                                                    String etno = ("" + el.getValue()).substring(0);

                                                                    String etresp = "" + hm2.get("et" + (a++));

                                                                    if (etresp != null) {
                                                                        c2 = row0.createCell(colno++);
                                                                        c2.setCellValue(etresp);
                                                                        c2.setCellStyle(cs);
                                                                    }


                                                                }

                                                                 */


                                                            }


                                                            //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ



                                                            sheet1.setColumnWidth(0, (15 * 300));
                                                            sheet1.setColumnWidth(1, (15 * 500));
                                                            sheet1.setColumnWidth(2, (15 * 500));
                                                            sheet1.setColumnWidth(3, (15 * 500));
                                                            sheet1.setColumnWidth(4, (15 * 500));
                                                            sheet1.setColumnWidth(5, (15 * 500));
                                                            sheet1.setColumnWidth(6, (15 * 500));


                                                            //==========================================


                                                            File file = new File(Environment.getExternalStorageDirectory(), "" + eventname + ".xls");
                                                            FileOutputStream os = null;

                                                            try {
                                                                os = new FileOutputStream(file);
                                                                wb.write(os);
                                                                Log.w("FileUtils", "Writing file" + file);
                                                                tost("" + file);
                                                                success = true;
                                                            } catch (IOException e) {
                                                                Log.w("FileUtils", "Error writing " + file, e);
                                                                tost("" + e);
                                                            } catch (Exception e) {
                                                                Log.w("FileUtils", "Failed to save file", e);
                                                                tost("" + e);
                                                            } finally {
                                                                try {
                                                                    if (null != os)
                                                                        os.close();
                                                                    tost("file saved successfully ");

                                                                } catch (Exception ex) {
                                                                }
                                                            }

                                                            //==========================================


                                                        }


                                                    });

                                                }
                                            }
                                        });
                                    }



                                }
                            });


                        }

                    }
                });
            }


    /*    }); }*/




    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public void readexcelfun(View view) {
    readsheet(0);

    }

    public void readsheet(int index)
    {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.e(TAG, "Storage not available or read only");
            return;
        }

        try {
            // Creating Input Stream
            File file = new File(Environment.getExternalStorageDirectory() + "/" + filename + ".xls");

            if (file.exists()) {

                tl.removeAllViews();
                FileInputStream myInput = new FileInputStream(file);

                // Create a POIFSFileSystem object
                POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

                // Create a workbook using the File System
                HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

                // Get the first sheet from workbook
                HSSFSheet mySheet = myWorkBook.getSheetAt(index);

                /** We now need something to iterate through the cells.**/
                Iterator rowIter = mySheet.rowIterator();


                while (rowIter.hasNext()) {
                    HSSFRow myRow = (HSSFRow) rowIter.next();
                    Iterator cellIter = myRow.cellIterator();


                    TableRow tr = new TableRow(getApplicationContext());
                    TableRow.LayoutParams tlp =  new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(tlp);


                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        // Log.d(TAG, "Cell Value: " + myCell.toString());
                        //Toast.makeText(getApplicationContext(), "cell Value: " + myCell.toString(), Toast.LENGTH_LONG).show();


                        TextView tv =  new TextView(getApplicationContext());
                        tv.setLayoutParams(tlp);
                        tv.setText(""+myCell);
tv.setTextColor(Color.parseColor("#fffdd0"));
                        tv.setPadding(5,5,5,5);

                        tv.setBackground(getDrawable(R.drawable.cell));

                        tr.addView(tv);
                    }

                    tl.addView(tr);
                }

            }else
            {
                newtost("file: "+filename+" not exists");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }


    //>>
    }
