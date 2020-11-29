package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.hanks.htextview.rainbow.RainbowTextView;
import com.itextpdf.text.pdf.parser.Line;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;



//>>



import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.FitWindowsLinearLayout;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




//--------------------
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;


//>>
public class SubAdminPieCart extends AppCompatActivity {


    BoomMenuButton bmb ;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_pie_cart);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bmb = findViewById(R.id.bmb);


        oncreate2();


        boommenu();

    }



    String btnttxts[]={
            "Show Bars","Choose Background Color"
    };
    public void boommenu()
    {
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(R.drawable.butterfly)
                    .normalText(btnttxts[i]);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    tost("index: "+index);

                    if(index == 0 )
                    {
                        Intent i =  new Intent(SubAdminPieCart.this, SubAdminBarChart.class);
                        i.putExtra("evname",event);
                        startActivity(i);


                    }else
                    if(index == 1)
                    {
                        pickcolor();
                    }
                }
            });

            bmb.addBuilder(builder);
        }
    }


    //>>


    int  r =  3 ,g = 68, b = 114;
    String event ="";
    String course  ="";


    String questions[] =  new String[]
            {
                    "Gender numbers ", "What is your overall assessment of the event ?","Was the topic or aspect of the workshop interesting or useful ?","Did the workshop/seminar achieve the programme objective ?","Knowledge and information gained from participation at this event ?","Please comment on the organization of the event ?","extra_safety"
            };

    String data = " ";

    TextView t ;

    TextView body ;


    FirebaseFirestore f ;

    ConstraintLayout cl ;

    LinearLayout ll  ;

    ScrollView sv ;





    void setuppie(String questions[],int counts[] , String question  )
    {


        ll = findViewById(R.id.analysesheet);

        //poplating a list of ie entries



        //calculating ercentages ...

     /*   float percents[] = new float[counts.length];

        int total =0;
       for( int  i=0;i< counts.length;i++)
       {
           total += counts[i];
       }

       for(int  i=0;i< counts.length;i++)
       {
           float p = (float)counts[i]/total*100;
           percents[i] = p;
       }
*/


        //-----------------------------

        CardView cv  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,330);
        cv.setLayoutParams(clp);

        clp.setMargins(10,10,10,10);
        cv.setElevation(8);
        LinearLayout.LayoutParams  lp1  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout l   = new LinearLayout(getApplicationContext());
        l.setLayoutParams(lp1);


        cv.setRadius(20);
//cv.setClipToPadding(false);

        //-----------------------------


        List<PieEntry> p=  new ArrayList<>();

        for( int i =0;i<questions.length;i++)
        {
            p.add(new PieEntry(counts[i],questions[i].substring(0)));

        }


        PieDataSet dataSet =  new PieDataSet(p," ");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        PieData data =  new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());

        //get the chart



        PieChart chart  = new PieChart(getApplicationContext());
        PieChart.LayoutParams prms = new PieChart.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        chart.setLayoutParams(prms);


        //  l.addView(chart);

        chart.setDrawEntryLabels(false);

        // chart.setEntryLabelColor(Color.parseColor("#ff0066"));

        // chart.getXAxis().setTextColor(Color.parseColor("#ff0066"));
        chart.getLegend().setTextColor(Color.parseColor("#ff0066"));
        chart.getDescription().setText("");

        // PieChart chart = (PieChart)findViewById(R.id.mypie);

        chart.setData(data);
        chart.animateXY(1000,1000);
        chart.invalidate();

        chart.getDescription().setText(question);
        chart.getDescription().setTextColor(Color.parseColor("#8a2be2"));
        chart.getDescription().setTextSize(10);

        l.addView(chart);


        View v  = new View(getApplicationContext());
        ViewGroup.LayoutParams sp =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
        v.setLayoutParams(sp);
        // l.addView(v);


        cv.addView(l);
        //  cv.setCardElevation(10);
        cv.setCardBackgroundColor(Color.parseColor("#eeeeee"));
        ll.addView(cv);




        TextView tv = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lpt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lpt);
        tv.setText(question);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.parseColor("#8a2be2"));

        CardView cv2  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp2 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cv2.setLayoutParams(clp2);
        cv2.setRadius(20);

        LinearLayout.LayoutParams  lpm2  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout l2   = new LinearLayout(getApplicationContext());
        l2.setLayoutParams(lpm2);
        cv2.setBackgroundColor(Color.parseColor("#22222222"));

        l2.addView(tv);
        cv2.addView(l2);

        // ll.addView(cv2);

        CardView cv3  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp3 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, 30);
        cv3.setLayoutParams(clp3);
        cv3.setCardBackgroundColor(Color.TRANSPARENT);
        ll.addView(cv3);

    }






    public void ananlyseallcourse(String event)
    {
        TextView t =  findViewById(R.id.head);

        t.setText((""+event+" analysis").toUpperCase());

        f.collection("DYNEVEREZ/"+event+"/COUNTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int qno  =0;

                for(DocumentSnapshot questionno : queryDocumentSnapshots.getDocuments())
                {
                    Map m =  questionno.getData();

                    Iterator it =  m.entrySet().iterator();


                    int cit =0;

                    int qit = 0;

                    final int counts[] = new int[m.size()];

                    final String ques[] = new String[m.size()];

                    while(it.hasNext()) {
                        Map.Entry el = (Map.Entry) it.next();
                        //data += "\n" + el;
                        int a  =Integer.parseInt(""+el.getValue());
                        counts[cit++] = a;

                        ques[qit++] = ""+el.getKey();

                    }    //data += "\n";


                    //>>

                    f.document("FORMS/IQSE/QUESTIONS/"+questionno.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            String extrq = "";

                            if(documentSnapshot!= null && documentSnapshot.exists())
                            {
                                extrq  = ""+documentSnapshot.getData().get("QUESTION");

                            }else
                            {
                                extrq  = "Courses Entrolled" ;
                            }


                            setuppie(ques,counts,extrq);
                        }
                    });

                    //>>


                }

            }
        });
    }



    //>>
    public void oncreate2()
    {


        ll = findViewById(R.id.analysesheet);
        f= FirebaseFirestore.getInstance();
        body= findViewById(R.id.body);

        event = "canvas";
        course = "cs";


        event = this.getIntent().getExtras().getString("evname");


//course = this.getIntent().getExtras().getString("course");


        ananlyseallcourse(event);

    }



    public void download() {



        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(new BaseColor(r,g,b));


        pageSize.setBorder(10);
        pageSize.setBorderColor(new BaseColor(212, 141, 212));

        Document mdoc = new Document(pageSize);
        String filename = "Pie_"+event;
        //    String filename  =  new SimpleDateFormat("yyyyMMdd_HHmmss",
        //          Locale.getDefault()).format(System.currentTimeMillis());

        String filepath = Environment.getExternalStorageDirectory() + "/" + filename + ".pdf";

        try {

            PdfWriter.getInstance(mdoc, new FileOutputStream(filepath));

            mdoc.open();

            String mtext = "Hii  there * ^";

            mdoc.addAuthor("me");


            //  mdoc.add(new Paragraph(mtext));

            // String url = "https://i.imgur.com/islPcrO.png";
            // Image image = Image.getInstance(url);




       /*
            try {

               // InputStream ims = getAssets().open("ganesh.jpg");


                //  Bitmap bmp = BitmapFactory.decodeStream(ims);
                LinearLayout ll = findViewById(R.id.analysesheet);

                for (int i = 0; i < ll.getChildCount(); i++) {
                    View v = ll.getChildAt(i);

                    if (v instanceof CardView) {
                        // Do something

                        v.buildDrawingCache();
                        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        image.scalePercent(100);
                        image.setAlignment(Element.ALIGN_CENTER);
                        mdoc.add(image);

                    } else if (v instanceof ViewGroup) {

                        //this.loopViews((ViewGroup) v);
                    }

                    if(v instanceof TextView )
                    {
                        String txt =  ""+((TextView) v).getText();

                      //  mdoc.add(new Paragraph(txt));
                    }

;
                }



                mdoc.add(new Paragraph(" testing line .,,, "));

            } catch (IOException e) {
                e.printStackTrace();
            }

*/

            try {

                LinearLayout l =  findViewById(R.id.analysesheet);

                for(int i =0 ;i< l.getChildCount();i++)
                {
                    View v  =  l.getChildAt(i);

                    tost(""+v);

                    if( v instanceof  CardView)
                    {
                        tost(" writing "+v+ " to pdf ");

                        v.setDrawingCacheEnabled(true);
                        v.buildDrawingCache();

                        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        image.scalePercent(100);
                        image.setAlignment(Element.ALIGN_CENTER);
                        mdoc.add(image);

                    }
                }

            }catch (Exception e)
            {
                tost(" error : "+e.getMessage());

            }
            mdoc.close();

            tost("pdf saved ");


        }catch(Exception e)
        {
            tost("errot : " + e.getMessage());
        }



    }





    public void pickcolor() {


        final ColorPicker cp = new ColorPicker(SubAdminPieCart.this,r,g,b);
        cp.show();



        Button okColor = (Button)cp.findViewById(R.id.okColorButton);

        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* You can get single channel (value 0-255) */
                r = cp.getRed();
                g = cp.getGreen();
                b = cp.getBlue();

                /* Or the android RGB Color (see the android Color class reference) */

                int c = cp.getColor();

                ll.setBackgroundColor(Color.rgb(r,g,b));
                cp.dismiss();
            }
        });



    }

}
