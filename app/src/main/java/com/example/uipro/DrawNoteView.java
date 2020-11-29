package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.hanks.htextview.rainbow.RainbowTextView;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.rm.freedrawview.FreeDrawView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawNoteView extends AppCompatActivity {

RainbowTextView rtv  ;

    ImageView iv  ;


    BoomMenuButton bmb ;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }


    String noteid = "";

    DrawNoteDatabase ndb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_note_view);

        bmb = findViewById(R.id.bmb);

        noteid =  this.getIntent().getExtras().getString("noteid");

        iv  = findViewById(R.id.img);

        ndb = new DrawNoteDatabase(DrawNoteView.this);

        rtv  = findViewById(R.id.noteid);

        setimage();

        boommenu();
    }



    public void setimage()
    {
        Cursor c =  ndb.getImage(noteid);

        c.moveToFirst();

        if(c.getCount() > 0)
        {
            byte [] arr = c.getBlob(1);

            Bitmap  bm = getImage(arr);

            iv.setImageBitmap(bm);

            rtv.setText(noteid);


            tost(""+arr);
        }


    }



    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }





    String btnttxts[]={
            "Delete","Some option"
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
                    if(index == 0 )
                    {
                        //delete ...

                     if(ndb.delnote(noteid))
                     {
                         tost(" deleted current note : "+noteid);

                     }else
                     {
                         tost(" deletion error { ' _ ' }");
                     }


                    }else
                    if(index == 1)
                    {
                        // reserved ..
                    }
                }
            });

            bmb.addBuilder(builder);
        }
    }




}
