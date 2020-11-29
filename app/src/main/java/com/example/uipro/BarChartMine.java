package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.hanks.htextview.rainbow.RainbowTextView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;


//>>


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//>>

public class BarChartMine extends AppCompatActivity {


    BoomMenuButton bmb ;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bmb = findViewById(R.id.bmb);




        oncreate2();


        boommenu();
    }



    String btnttxts[]={
            "Show Pies","Download Pdf","Choose Background Color"
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
Intent i =  new Intent(BarChartMine.this, PieChartMine.class);
i.putExtra("evname",event);
startActivity(i);


                    }else
                    if(index == 1)
                    {
                        download();
                    }else
                    if(index == 2)
                    {
                        pickcolor();
                    }
                }
            });

            bmb.addBuilder(builder);
        }
    }




    public void pickcolor() {


        final ColorPicker cp = new ColorPicker(BarChartMine.this,r,g,B);
        cp.show();



        Button okColor = (Button)cp.findViewById(R.id.okColorButton);

        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* You can get single channel (value 0-255) */
                r = cp.getRed();
                g = cp.getGreen();
                B = cp.getBlue();

                /* Or the android RGB Color (see the android Color class reference) */

                int c = cp.getColor();

                llb.setBackgroundColor(Color.rgb(r,g,B));
                cp.dismiss();
            }
        });



    }


    //>>

    int  r =  3 ,g = 68, B = 114;

    TextView tbh;

    String event="Canvas";

    String course  = "C S";

    BarChart b;

    FirebaseFirestore ff  ;

    String questions[] =  new String[]
            {
                    "Gender colors","What is your overall assessment of the event ?","Was the topic or aspect of the workshop interesting or useful ?","Did the workshop/seminar achieve the programme objective ?","Knowledge and information gained from participation at this event ?","Please comment on the organization of the event ?","extra_safety"
            };

    LinearLayout llb  ;






    public void oncreate2()
    {


        //--------------


        llb = findViewById(R.id.barsheetanalyse);
        ff = FirebaseFirestore.getInstance();

        tbh = findViewById(R.id.head);


        String labels[]  = new String[]{"a","b","c","d","e","e"};

       event = this.getIntent().getExtras().getString("evname");

        // course  =  this.getIntent().getExtras().getString("course");


        getfiredata(event);


    }


    public void getfiredata(String event )
    {
        try {

            tost(event);


//"cutecoll/favevent/"+event
//        ff.collection("EVENTREZ/"+event+"/COURSES/"+course+"/COUNTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            tbh.setText(("" + event + " ANALYSIS").toUpperCase());

            ff.collection("DYNEVEREZ/" + event + "/COUNTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> ds = queryDocumentSnapshots.getDocuments();


                    String data = " ";


                    int qno = 0;
                    for (DocumentSnapshot d : ds) {

                        Map m = d.getData();


                        String question = d.getId();


                        data += "Q: " + question + "\n";

                        Iterator it = m.entrySet().iterator();


                        final int counts[] = new int[m.size()];

                        int cit = 0;

                        int qit = 0;

                        final String ques[] = new String[m.size()];
                        while (it.hasNext()) {
                            Map.Entry me = (Map.Entry) it.next();


                            data += me + "\n";

                            int a = Integer.parseInt("" + me.getValue());

                            counts[cit++] = a;

                            ques[qit++] = "" + me.getKey();


                        }

                        ff.document("FORMS/IQSE/QUESTIONS/" + d.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String extrq = "";

                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    extrq = "" + documentSnapshot.getData().get("QUESTION");

                                } else {
                                    extrq = "Courses Entrolled";
                                }


                                addbarchart(ques, counts, extrq);
                            }
                        });


                        data += "\n";
                    }

                    //   t.setText(data);


                }
            });
        }
        catch(Exception e)
        {
            tost(""+e.getMessage());
        }
    }





    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;

        public String values[];
        public DayAxisValueFormatter(BarLineChartBase<?> chart , String values[]) {
            this.chart = chart;
            this.values =  values;
        }
        @Override
        public String getFormattedValue(float value) {
            return values[(int) value];
        }
    }



    public void addbarchart(String lb[], int counts[], String question)
    {

        String labels[] = new String[lb.length];

        for(int i =0;i< lb.length;i++)
        {
            labels[i]= lb[i].substring(0);
        }
        //===================================

        CardView cv  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,320);
        cv.setLayoutParams(clp);

        clp.setMargins(10,10,10,10);
        cv.setElevation(8);
        LinearLayout.LayoutParams  lp1  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout l   = new LinearLayout(getApplicationContext());
        l.setLayoutParams(lp1);


        cv.setRadius(20);


        //-----------------------------------




        // b=  findViewById(R.id.mybar);

        BarChart b  = new BarChart(getApplicationContext());

        ViewGroup.LayoutParams lp =  new BarChart.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);



        b.setLayoutParams(lp);




        List<BarEntry> entries  =  new ArrayList<>();

        for( int i =0 ;i<counts.length;i++)
        {
            entries.add(new BarEntry(i,counts[i]));

        }

        /*
        entries.add(new BarEntry(1f,1));
        entries.add(new BarEntry(2f,2));
        entries.add(new BarEntry(3f,3));
        entries.add(new BarEntry(4f,4));
        entries.add(new BarEntry(5f,5));
*/
        BarDataSet ds  = new BarDataSet(entries,"");

        ds.setColors(ColorTemplate.JOYFUL_COLORS);

        b.setFitBars(true);

        b.getDescription().setText("__"+question);



        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(b, labels);
        XAxis xAxis = b.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);

        BarData bd  = new BarData(ds);

        xAxis.setGranularity(1);
        b.setData(bd);
        b.getDescription().setText("");
        b.invalidate();
        b.animateY(1000);

        //--------------------------

        l.addView(b);

        cv.addView(l);

        llb.addView(cv);
        //--------------------------

        CardView cv2  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp2 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cv2.setLayoutParams(clp2);
        cv2.setRadius(20);

        LinearLayout.LayoutParams  lpm2  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout l2   = new LinearLayout(getApplicationContext());
        l2.setLayoutParams(lpm2);
        cv2.setBackgroundColor(Color.parseColor("#ffffff"));
        cv2.setElevation(8);

        // ll.addView(cv2);





        TextView tv = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lpt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lpt);
        tv.setText(question);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.parseColor("#034472"));

        tv.setTextSize(10);

        cv2.setPadding(5,0,5,0);
        l2.addView(tv);
        cv2.addView(l2);


//        llb.addView(tv);


        llb.addView(cv2);


        View v  = new View(getApplicationContext());
        ViewGroup.LayoutParams sp =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,50);
        v.setLayoutParams(sp);
        //  llb.addView(v);
//===================================

        CardView cv3  =  new CardView(getApplicationContext());
        CardView.LayoutParams clp3 = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, 30);
        cv3.setLayoutParams(clp3);
        llb.addView(cv3);

        cv3.setCardBackgroundColor(Color.TRANSPARENT);
        //===================================


    }



    public void download() {


        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(new BaseColor(r,g,B));





        Document mdoc = new Document(pageSize);
        String filename = "Bar_"+event;
        //    String filename  =  new SimpleDateFormat("yyyyMMdd_HHmmss",
        //          Locale.getDefault()).format(System.currentTimeMillis());

        String filepath = Environment.getExternalStorageDirectory() + "/" + filename + ".pdf";

        try {

            PdfWriter.getInstance(mdoc, new FileOutputStream(filepath));

            mdoc.open();

            String mtext = "Hii  there * ^";

            mdoc.addAuthor("me");


            //   mdoc.add(new Paragraph(mtext));

            // String url = "https://i.imgur.com/islPcrO.png";
            // Image image = Image.getInstance(url);




            try {


                //  Bitmap bmp = BitmapFactory.decodeStream(ims);
                LinearLayout ll = findViewById(R.id.barsheetanalyse);

                for (int i = 0; i < ll.getChildCount(); i++) {
                    View v = ll.getChildAt(i);

                    if (v instanceof CardView) {
                        // Do something

                        v.buildDrawingCache();
                        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        image.scalePercent(80);
                        image.setAlignment(Element.ALIGN_CENTER);
                        mdoc.add(image);

                    } else if (v instanceof ViewGroup || v instanceof RainbowTextView) {

                        //this.loopViews((ViewGroup) v);
                    }

                    if(v instanceof TextView )
                    {
                        String txt =  ""+((TextView) v).getText();

                        // mdoc.add(new Paragraph(txt));
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            mdoc.close();

            tost("pdf saved ");


        }catch(Exception e)
        {
            tost("errot : " + e.getMessage());
        }


        try {

            takeScreenShot();

        }catch(Exception e)
        {
            tost("ex : "+e.getMessage());
        }


    }



    private void takeScreenShot()
    {
        View u = ((Activity) BarChartMine.this).findViewById(R.id.scroll);

        ScrollView z = (ScrollView) ((Activity) BarChartMine.this).findViewById(R.id.scroll);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"";
        String fileName = "SakshiBar"+event+".jpg";

        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
            tost("ScreenShot Saved");
        }catch (FileNotFoundException e) {

            tost("f ex" +  e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            tost("ex: "+e.getMessage());
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.rgb(r,g,B));
        view.draw(canvas);
        return returnedBitmap;
    }


    //>>

}
