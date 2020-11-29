package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hanks.htextview.evaporate.EvaporateText;
import com.hanks.htextview.evaporate.EvaporateTextView;
import com.hanks.htextview.line.LineTextView;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.waynell.library.DropAnimationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class TeacherDasboard extends AppCompatActivity {


    EvaporateTextView evt;

    BubblePicker bubblePicker;

    int bubbleid  = 1000;

    String[] name={
            "Create Sub Admins",
            "IQSE Analysis",
            "Create Events",
            "Download Excel Sheet",
            "Share Analysis",
            "Create User Form",
            "Share Student Responses",
            "Create Events to register",
            "Select Verification Options",
            "Show Dynamic Form Analysis",
            "Generate QR Code ",
            "Send Notifications",
            "Nlp Analysis"
    };

    int[] images = {
            R.drawable.subadmin,
            R.drawable.analysis,
            R.drawable.events,
            R.drawable.exceldnld,
            R.drawable.shareanal,
            R.drawable.crform,
            R.drawable.shareresponses,
            R.drawable.futureregevents,
            R.drawable.cutegreendinoaus,
            R.drawable.rabbit,
            R.drawable.pass,
            R.drawable.cutegreendinoaus,
            R.drawable.butterfly
    };

    int[] colors = {
            android.graphics.Color.parseColor("#bf9fdc"),
            android.graphics.Color.parseColor("#c6ebfb"),
            android.graphics.Color.parseColor("#fefd97"),
            android.graphics.Color.parseColor("#a6f8ce"),
            android.graphics.Color.parseColor("#fec9a7"),
            android.graphics.Color.parseColor("#fe8d6f"),
            android.graphics.Color.parseColor("#fdc453"),
            android.graphics.Color.parseColor("#a0dde0"),
            android.graphics.Color.parseColor("#ff0066"),
            android.graphics.Color.parseColor("#fffdd0"),
            android.graphics.Color.parseColor("#0099cc"),
            android.graphics.Color.parseColor("#ff00ff"),
            android.graphics.Color.parseColor("#aa00aa")

    };



    public void tost(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dasboard);


        DatabaseManager db =  new DatabaseManager(TeacherDasboard.this);

        if(db.updateEmployee(10,"admin"))
        {
            tost(" added admin to sqlite ");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


      evt = findViewById(R.id.evat);



makebubbles();

//   fallingleaves();


        boommenu2();


    }



    public void makebubbles()
    {
        Typeface t =  Typeface.createFromAsset(getAssets(),"roboto_bold.ttf");


        bubblePicker = (BubblePicker) findViewById(R.id.picker);


        final ArrayList<PickerItem> listitems =  new ArrayList<>();
        for (int i=0;i<name.length;i++){
            PickerItem item = new PickerItem(name[i],getDrawable(R.drawable.gradient),true,Color.parseColor("#0099cc"),new BubbleGradient(Color.parseColor("#03bbff"),colors[(i+1)%(colors.length)]),(float)0.9,t,Color.parseColor("#ffffff"),35,getDrawable(images[i]));

            listitems.add(item);


        }



        bubblePicker.setBubbleSize(1);
        bubblePicker.setItems(listitems);




        bubblePicker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem pickerItem) {
                Toast.makeText(TeacherDasboard.this,pickerItem.getTitle()+" Added To Your Interest", Toast.LENGTH_SHORT).show();

                String title = ""+pickerItem.getTitle();

                if(name[0].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,CreateSubadmins.class));
                }else
                if(name[1].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,IQSEAnalysis.class));
                }else
                if(name[2].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,CreateEvents.class));
                }else
                if(name[3].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,DownloadExcelSheets.class));
                }else
                if(name[4].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,ShareAnalysis.class));
                }else
                if(name[5].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,Createform.class));
                }else
                if(name[6].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,SendExcel.class));
                }else
                if(name[7].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,Createregevents.class));
                }else
                if(name[8].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,Toggle_verification_Options.class));
                }else
                if(name[9].equals(title))
                {
                    startActivity(new Intent(TeacherDasboard.this,CraetedFormAnalsisSelect.class));
                }else
                   if(name[10].equals(title))
                   {
                       startActivity(new Intent(TeacherDasboard.this,SelectEvenToSeeQR.class));
                   }
                   else
                   if(name[11].equals(title))
                   {
                       startActivity(new Intent(TeacherDasboard.this,SendNotifs.class));
                   }
                   else
                   if(name[12].equals(title))
                   {
                       startActivity(new Intent(TeacherDasboard.this,chooseevfornlp.class));
                   }
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem pickerItem) {
                Toast.makeText(TeacherDasboard.this,pickerItem.getTitle()+"Removed from Your Interest", Toast.LENGTH_SHORT).show();
            }
        });


    }



    class anihead implements Runnable
    {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            while (true) {
                anieva(evt);

                try {
                    Thread.sleep(4000);
                } catch (Exception e) {

                }
            }
        }
    }

    public void fallingleaves()
    {
        /*
        DropAnimationView view = (DropAnimationView) findViewById(R.id.drop_animation_view);
        view.setDrawables(R.drawable.leaf_1,
                R.drawable.leaf_2,
                R.drawable.leaf_3,
                R.drawable.leaf_4,
                R.drawable.leaf_5,
                R.drawable.leaf_6);
        view.startAnimation();



         */
    }


    @Override
    protected void onResume() {
        super.onResume();

    makebubbles();

    }





    int hno = 0;

    String harr[] = {
            "Choose one function",
            "Welcome Admin",
            "Create some admins ",
            "Create events ",
            "View Alalysis "

    };



    public void anieva(View view) {

        EvaporateTextView lt =  (EvaporateTextView) view;

        lt.animateText(""+harr[((hno++)%(harr.length))]);

        lt.animate();
    }






    public void boommenu2()
    {

        BoomMenuButton  bmb ;
         bmb =  findViewById(R.id.bmb);

         String options[]= {"Logout","Other Options"};

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(R.drawable.butterfly)
                    .normalText(options[i]);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    if(index == 0 )
                    {

                        DatabaseManager db  =  new DatabaseManager(TeacherDasboard.this);

                        if(db.updateEmployee(10,"logout"))
                        {
                            Intent i =  new Intent(TeacherDasboard.this, studorteach.class);

                            startActivity(i);
                        }



                    }
                }
            });

            bmb.addBuilder(builder);
        }
    }



    public void Logout(View view) {


        DatabaseManager db  =  new DatabaseManager(TeacherDasboard.this);

        if(db.updateEmployee(10,"logout"))
        {
            Intent i =  new Intent(TeacherDasboard.this, studorteach.class);

            startActivity(i);
        }


    }



}
