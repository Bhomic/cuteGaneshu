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


//>>

public class SubAdminShowSheet extends AppCompatActivity {

    List<String> radiolist;
    LinearLayout ll ;

    GlowButton glbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_show_sheet);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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




    public void makesheetfor(final String eventname) {
        radiolist = new ArrayList<String>();

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return;
        }


        wb = new HSSFWorkbook();
        sheet1 = wb.createSheet("Entered Info for " + eventname);


        cs2 = wb.createCellStyle();
        cs2.setFillForegroundColor(HSSFColor.AQUA.index);
        cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        row0 = sheet1.createRow(0);


        cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

   /*
    c2 = row0.createCell(0);
    c2.setCellValue("Event");
    c2.setCellStyle(cs2);
*/


//---------------------------------------


        //
        c2 = row0.createCell(0);
        c2.setCellValue("Course");
        c2.setCellStyle(cs2);


        c2 = row0.createCell(1);
        c2.setCellValue("Email");
        c2.setCellStyle(cs2);


        ff.collection("FORMS/IQSE/QUESTIONS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    final      String q = d.getId();


                    final      int A = a;
                    ff.document("FORMS/IQSE/QUESTIONS/"+q).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot!=  null && documentSnapshot.exists())
                            {
                                Map<String ,Object> hm =  documentSnapshot.getData();
                                String question = ""+hm.get("QUESTION");

                                c2 = row0.createCell(A);
                                c2.setCellValue("" + question);
                                c2.setCellStyle(cs2);



                                if(q.charAt(0) == 'q')
                                    questions.put("Q" + (A), q);
                                else
                                {
                                    radioq.put("RQ"+ (rq++),q);
                                    radiolist.add(q);

                                    sheet1.setColumnWidth(rq+2, (15 * 500));
                                }

                            }
                        }
                    });


                    a++;
                  /*
                    c2 = row0.createCell(a++);
                    c2.setCellValue("" + q);
                    c2.setCellStyle(cs2);


                   */

                }


                //QQQQQQQQQQQQQQQQQQQQQQQQ


                ff.collection("DYNEVE").document("" + eventname).collection("COURSES").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (DocumentSnapshot d : queryDocumentSnapshots) {

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

                                                    testcolno = 4;


                                                    Map<String, Object> hm = documentSnapshot.getData();


                                                    for(int i =0;i<   radiolist.size();i++)
                                                    {
                                                        String docid = radiolist.get(i);


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

                                                                row0 =  (Row)rowlist.get(newrow++);

                                                                colno = 0;

                                                                Map<String, Object> hm = documentSnapshot.getData();

                                                                c2 = row0.createCell(colno++);
                                                                c2.setCellValue(cname);
                                                                c2.setCellStyle(cs);


                                                                c2 = row0.createCell(colno++);
                                                                c2.setCellValue(user);
                                                                c2.setCellStyle(cs);


                                                                Map<String, Object> hm2 = documentSnapshot.getData();

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


                                                            }


                                                            //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ


                                                            //WWWWWWWWWWWWWWWWWWWWWWWWww


                                                            sheet1.setColumnWidth(0, (15 * 500));
                                                            sheet1.setColumnWidth(1, (15 * 500));
                                                            sheet1.setColumnWidth(2, (15 * 500));


                                                            //==========================================


                                                            File file = new File(Environment.getExternalStorageDirectory(), "temp" + eventname + ".xls");
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
        });

    }


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

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.e(TAG, "Storage not available or read only");
            return;
        }

        try {
            // Creating Input Stream
            File file = new File(Environment.getExternalStorageDirectory() + "/temp" + filename + ".xls");

            if (file.exists()) {

                tl.removeAllViews();
                FileInputStream myInput = new FileInputStream(file);

                // Create a POIFSFileSystem object
                POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

                // Create a workbook using the File System
                HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

                // Get the first sheet from workbook
                HSSFSheet mySheet = myWorkBook.getSheetAt(0);

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
                tost("file not exists");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();

        File file = new File(Environment.getExternalStorageDirectory() + "/temp" + filename + ".xls");

        if(file.exists())
        {
            file.delete();
        }
    }


    //>>
}
