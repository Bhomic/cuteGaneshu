package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian3d;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NLPAnalysis extends AppCompatActivity {


    BoomMenuButton bmb;

    int noentries =  0;

    String evname = "";

    FirebaseFirestore ff;

int i =0;
    public void tost(String msg)
    {
        Toast.makeText(NLPAnalysis.this, msg, Toast.LENGTH_LONG).show();
    }



    public  void boommenu()
    {

        bmb  = (BoomMenuButton) findViewById(R.id.bmb);

        String titles []= {"Save ScreenShot* ^" , "Future Function" };

        String subt[]={"Saves ur phone screen" , " Reserved for some future fuunxtion " };


        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.butterfly)
                    .normalText(titles[i])
                    .subNormalText(subt[i]);


            builder
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(NLPAnalysis.this, "Clicked " + index, Toast.LENGTH_LONG).show();


                            if(index ==0)
                            {
                                // save current note

                            takeScreenShot();


                            }else if(index  == 1)
                            {

                                tost(" option : 1 selected ");

                               //someotherfun();
                                // delete current note and then intent ot takenotes

                            }



                        }
                    });
            bmb.addBuilder(builder);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlpanalysis);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        evname = this.getIntent().getExtras().getString("evname");

ff =  FirebaseFirestore.getInstance();

boommenu();

        AnyChartView anyChartView = findViewById(R.id.chart);
        anyChartView.setProgressBar(findViewById(R.id.progressbar));

        Cartesian3d bar3d = AnyChart.bar3d();

        bar3d.animation(true);

        bar3d.padding(10d, 40d, 5d, 20d);

        bar3d.title("EditTexts Sentimental Analysis");

        bar3d.yScale().minimum(0d);

        bar3d.xAxis(0).labels()
                .rotation(-90d)
                .padding(0d, 0d, 20d, 0d);

        bar3d.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        bar3d.yAxis(0).title("Counts ");

        List<DataEntry> data = new ArrayList<>();
     /*   data.add(new CustomDataEntry("Eyebrow pencil", 9332, 8987, 5067));
        data.add(new CustomDataEntry("Lipstick", 9256, 7376, 5054));
        data.add(new CustomDataEntry("Lipstick1", 9256, 7376, 5054));
        data.add(new CustomDataEntry("Lipstick2", 9256, 7376, 5054));
        data.add(new CustomDataEntry("Lipstick3", 9256, 7376, 5054));


      */

        // here we will add the data from firebasefirestore to our dataset ..

     String[]qs
      = {"Was Workshop Interesting ?","How workshop can be more effective ?"};

     ///NLPETEV/Cloud/COUNTS/q0
        ff.collection("NLPETEV").document(evname).collection("COUNTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot d :  queryDocumentSnapshots.getDocuments())
                {

                    String qno =""+d.getId();

                    ff.collection("NLPETEV").document(evname).collection("COUNTS").document(qno).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot != null && documentSnapshot.exists())
                            {

                                Map<String, Object> hm = documentSnapshot.getData();


                                String question  = qs[i++];   //""+hm.get("QUESTION");

                                String positive = ""+hm.get("POSITIVE");
                                String neutral = ""+hm.get("NEUTRAL");
                                String negative = ""+hm.get("NEGATIVE");

                                tost(question);

                                data.add(new CustomDataEntry(question, Integer.parseInt(positive),Integer.parseInt( neutral), Integer.parseInt(negative)));



                           //>>>>>


                                if(++noentries ==  queryDocumentSnapshots.size()) {
                                    Set set = Set.instantiate();
                                    set.data(data);
                                    Mapping bar1Data = set.mapAs("{ x: 'x', value: 'value' }");
                                    Mapping bar2Data = set.mapAs("{ x: 'x', value: 'value2' }");
                                    Mapping bar3Data = set.mapAs("{ x: 'x', value: 'value3' }");
                                    // Mapping bar4Data = set.mapAs("{ x: 'x', value: 'value4' }");

                                    bar3d.bar(bar1Data)
                                            .name("POSITIVE");

                                    bar3d.bar(bar2Data)
                                            .name("NEUTRAL");

                                    bar3d.bar(bar3Data)
                                            .name("NEGATIVE");

                                    // bar3d.bar(bar4Data).name("Nevada");

                                    bar3d.legend().enabled(true);
                                    bar3d.legend().fontSize(13d);
                                    bar3d.legend().padding(0d, 0d, 20d, 0d);

                                    bar3d.interactivity().hoverMode(HoverMode.SINGLE);

                                    bar3d.tooltip()
                                            .positionMode(TooltipPositionMode.POINT)
                                            .position("right")
                                            .anchor(Anchor.LEFT_CENTER)
                                            .offsetX(5d)
                                            .offsetY(0d)
                                            .format("{%Value}");

                                    bar3d.zAspect("10%")
                                            .zPadding(20d)
                                            .zAngle(45d)
                                            .zDistribution(true);


                                    //>>>>>


                                    anyChartView.setChart(bar3d);

                                }
                          //  anyChartView.invalidate();

                            }
                        }
                    });
                }
            }
        });

   /*
        Set set = Set.instantiate();
        set.data(data);
        Mapping bar1Data = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping bar2Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping bar3Data = set.mapAs("{ x: 'x', value: 'value3' }");
       // Mapping bar4Data = set.mapAs("{ x: 'x', value: 'value4' }");

        bar3d.bar(bar1Data)
                .name("Florida");

        bar3d.bar(bar2Data)
                .name("Texas");

        bar3d.bar(bar3Data)
                .name("Arizona");

       // bar3d.bar(bar4Data).name("Nevada");

        bar3d.legend().enabled(true);
        bar3d.legend().fontSize(13d);
        bar3d.legend().padding(0d, 0d, 20d, 0d);

        bar3d.interactivity().hoverMode(HoverMode.SINGLE);

        bar3d.tooltip()
                .positionMode(TooltipPositionMode.POINT)
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(0d)
                .format("${%Value}");

        bar3d.zAspect("10%")
                .zPadding(20d)
                .zAngle(45d)
                .zDistribution(true);
*/

     //   anyChartView.setChart(bar3d);
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }



    private void takeScreenShot()
    {
        View u = ((Activity) NLPAnalysis.this).findViewById(R.id.chart);

        AnyChartView z = (AnyChartView) ((Activity) NLPAnalysis.this).findViewById(R.id.chart);
        int totalHeight = z.getHeight();
        int totalWidth = z.getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"";
        String fileName = "NLP"+evname+".jpg";

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
            canvas.drawColor(Color.parseColor("#03bbff"));
        view.draw(canvas);
        return returnedBitmap;
    }

}
