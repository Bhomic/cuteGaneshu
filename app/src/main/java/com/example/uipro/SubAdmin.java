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

public class SubAdmin extends AppCompatActivity {


    EvaporateTextView evt;

    BubblePicker bubblePicker;

    int bubbleid  = 1000;

    String[] name={
            "IQSE Analysis",
            "Create Events",
            "Show Excel Sheet",
            "Create User Form",
            "Create Events to register",
            "Share Analysis"
    };

    int[] images = {

            R.drawable.analysis,
            R.drawable.events,
            R.drawable.exceldnld,
            R.drawable.crform,
            R.drawable.futureregevents,
            R.drawable.shareanal
    };

   // R.drawable.subadmin,
   // R.drawable.shareresponses,


    int[] colors = {
            android.graphics.Color.parseColor("#bf9fdc"),
            android.graphics.Color.parseColor("#c6ebfb"),
            android.graphics.Color.parseColor("#fefd97"),
            android.graphics.Color.parseColor("#a6f8ce"),
            android.graphics.Color.parseColor("#fec9a7"),
            android.graphics.Color.parseColor("#fe8d6f"),
            android.graphics.Color.parseColor("#fdc453"),
            android.graphics.Color.parseColor("#a0dde0")

    };



    public void tost(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        evt = findViewById(R.id.evat);

        DatabaseManager db =  new DatabaseManager(SubAdmin.this);

        if(db.updateEmployee(10,"subadmin"))
        {
            tost(" added admin to sqlite ");
        }




        makebubbles();

//   fallingleaves();

        boommenu2();

    }



    public void makebubbles()
    {
        Typeface t =  Typeface.createFromAsset(getAssets(),"roboto_bold.ttf");


        bubblePicker = (BubblePicker) findViewById(R.id.picker);


        final ArrayList<PickerItem> listitems =  new ArrayList<>();
        for (int i=0;i<name.length-1;i++){
            PickerItem item = new PickerItem(name[i],getDrawable(R.drawable.gradient),true,Color.parseColor("#0099cc"),new BubbleGradient(colors[i],colors[(i+1)%(colors.length)]),(float)0.9,t,Color.parseColor("#000000"),30,getDrawable(images[i]));

            listitems.add(item);


        }



        bubblePicker.setBubbleSize(1);
        bubblePicker.setItems(listitems);




        bubblePicker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem pickerItem) {
                Toast.makeText(SubAdmin.this,pickerItem.getTitle()+" Added To Your Interest", Toast.LENGTH_SHORT).show();

                String title = ""+pickerItem.getTitle();

                if(name[0].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,SubAdminIQSEAnalysis.class));
                }else
                if(name[1].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,CreateEvents.class));
                }else
                if(name[2].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,SubAdminShowSheet.class));
                }else
                if(name[3].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,Createform.class));
                }else
                if(name[4].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,Createregevents.class));
                }else
                if(name[5].equals(title))
                {
                    startActivity(new Intent(SubAdmin.this,MainActivity.class));
                }


            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem pickerItem) {
                Toast.makeText(SubAdmin.this,pickerItem.getTitle()+"Removed from Your Interest", Toast.LENGTH_SHORT).show();
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
            "Welcome SubAdmin",
            "Pick ur appropriate Bubble ! ",
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

        /*
        String options[] = {"Logout", "Other Options"};

        BoomMenuButton bmb ;
        bmb =  findViewById(R.id.bmb);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(R.drawable.butterfly)
                    .normalText(options[i]);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    if(index == 0 )
                    {

                        DatabaseManager db  =  new DatabaseManager(SubAdmin.this);

                        if(db.updateEmployee(10,"logout"))
                        {
                            Intent i =  new Intent(SubAdmin.this, studorteach.class);

                            startActivity(i);
                        }



                    }
                }
            });

            bmb.addBuilder(builder);
        }

         */
    }



    public void Logout(View view) {
        DatabaseManager db  =  new DatabaseManager(SubAdmin.this);

        if(db.updateEmployee(10,"logout"))
        {
            Intent i =  new Intent(SubAdmin.this, studorteach.class);

            startActivity(i);
        }
    }

}
