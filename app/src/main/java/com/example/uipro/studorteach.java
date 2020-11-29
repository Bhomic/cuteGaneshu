package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import me.itangqi.waveloadingview.WaveLoadingView;

public class studorteach extends AppCompatActivity {


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studorteach);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setTopTitle("");
        mWaveLoadingView.setCenterTitle("How do you want to log in ?");
        mWaveLoadingView.setCenterTitleColor(Color.parseColor("#fffdd0"));
        mWaveLoadingView.setBottomTitleSize(18);
        mWaveLoadingView.setProgressValue(80);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.parseColor("#fffdd0"));
        mWaveLoadingView.setBorderColor(Color.parseColor("#ffffff"));
        mWaveLoadingView.setTopTitleStrokeColor(Color.parseColor("#000000" ));
       mWaveLoadingView.setCenterTitleColor(Color.parseColor("#034472"));
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setTopTitleColor(Color.parseColor("#0099cc"));
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

    public void userloginfun(View view) {

        Intent i =  new Intent(studorteach.this, UserLogin.class);
        startActivity(i);

    }

    public void adminloginfun(View view) {


        DatabaseManager db =  new DatabaseManager(studorteach.this);


        Cursor c  =  db.getAllEmployees();

        if( c != null && c.getCount() > 0) {


            c.moveToFirst();

            String status = c.getString(1);


            tost("" + status);


            if (status.equals("logout")) {

                Intent i1 = new Intent(studorteach.this,
                        Teacherlogin.class);
                startActivity(i1);

            } else if (status.equals("admin")) {

                Intent i2 = new Intent(studorteach.this,
                        TeacherDasboard.class);
                startActivity(i2);

            } else if (status.equals("subadmin")) {

                Intent i3 = new Intent(studorteach.this,
                        SubAdmin.class);
                startActivity(i3);

            }

            c.close();

        }
        else{

           if( db.addEmployee(10,"logout")) {
               Intent i = new Intent(studorteach.this, Teacherlogin.class);
               startActivity(i);
           }

        }


         /*
        Intent i =  new Intent(studorteach.this, Teacherlogin.class);
        startActivity(i);

*/

    }
}
