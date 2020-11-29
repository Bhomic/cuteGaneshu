package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.ramotion.fluidslider.FluidSlider;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathDrawnListener;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;
import com.rm.freedrawview.ResizeBehaviour;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;

public class DrawNote extends AppCompatActivity {
int r = 0 ;
int g = 0;
int b = 0;


int Br  = 255;
int Bg  = 255;
int Bb  = 255;


FluidSlider  fsl ;

DrawNoteDatabase dndb;

    BoomMenuButton bmb ;

    FreeDrawView mSignatureView;
    private String TAG ="TAG";

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_note);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bmb = findViewById(R.id.bmb);

        dndb=  new DrawNoteDatabase(DrawNote.this);
    init();

    boommenu();
    }


    public void init()
    {
        mSignatureView = (FreeDrawView) findViewById(R.id.your_id);

        // Setup the View
        mSignatureView.setPaintColor(Color.BLACK);
        mSignatureView.setPaintWidthPx(10);
        //mSignatureView.setPaintWidthPx(12);
        mSignatureView.setPaintWidthDp(10);
        //mSignatureView.setPaintWidthDp(6);
        mSignatureView.setPaintAlpha(255);// from 0 to 255
        mSignatureView.setResizeBehaviour(ResizeBehaviour.CROP);// Must be one of ResizeBehaviour
        // values;

        // This listener will be notified every time the path done and undone count changes
        mSignatureView.setPathRedoUndoCountChangeListener(new PathRedoUndoCountChangeListener() {
            @Override
            public void onUndoCountChanged(int undoCount) {
                // The undoCount is the number of the paths that can be undone
            }

            @Override
            public void onRedoCountChanged(int redoCount) {
                // The redoCount is the number of path removed that can be redrawn
            }
        });
        // This listener will be notified every time a new path has been drawn
        mSignatureView.setOnPathDrawnListener(new PathDrawnListener() {
            @Override
            public void onNewPathDrawn() {
                // The user has finished drawing a path
            }

            @Override
            public void onPathStart() {
                // The user has started drawing a path
            }
        });

        // This will take a screenshot of the current drawn content of the view
        mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
            @Override
            public void onDrawCreated(Bitmap draw) {
                // The draw Bitmap is the drawn content of the View
            }

            @Override
            public void onDrawCreationError() {
                // Something went wrong creating the bitmap, should never
                // happen unless the async task has been canceled
            }
        });
    }


    String btnttxts[]={
            "Undo","Redo","Save ","Pick Color","Queryalll","Change Brush Size","UnDo All","BackgroundColor"
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
                        mSignatureView.undoLast();
                    }else
                    if(index == 1)
                    {
                        mSignatureView.redoLast();
                    }else
                    if(index == 2)
                    {
                        mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
                        @Override
                        public void onDrawCreated(Bitmap image) {
                            // The draw Bitmap is the drawn content of the View

                            savetosqlite();

                            File pictureFile = getOutputMediaFile();
                            if (pictureFile == null) {


                                return;
                            }
                            try {
                                FileOutputStream fos = new FileOutputStream(pictureFile);
                                image.compress(Bitmap.CompressFormat.PNG, 90, fos);
                                fos.close();

                                tost("image created ");

                            } catch (FileNotFoundException e) {
                                Log.d(TAG, "File not found: " + e.getMessage());
                            } catch (IOException e) {
                                Log.d(TAG, "Error accessing file: " + e.getMessage());
                            }

                        }

                        @Override
                        public void onDrawCreationError() {
                            // Something went wrong creating the bitmap, should never
                            // happen unless the async task has been canceled
                        }
                    });




                    }else if(index == 3)
                    {
                        pickcolor();


                    }else if(index == 4){
                        queryalldraw();
                    }else if(index ==  5)
                        {
                            changebrushsize();
                        }
                    else if(index == 6)
                            {
                                mSignatureView.undoAll();
                            }
                    else if(index == 7)
                    {
                     backcolor();
                    }

                }
            });

            bmb.addBuilder(builder);
        }
    }



    //-------------


    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }




    public void pickcolor() {


        final ColorPicker cp = new ColorPicker(DrawNote.this,r,g,b);
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

                mSignatureView.setPaintColor(Color.rgb(r,g,b));

                cp.dismiss();
            }
        });



    }




    public void savetosqlite()
    {

        EditText et  =  new EditText(DrawNote.this);
        LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);

        et.setHint("Enter Note title : ");

        AlertDialog.Builder adb =  new AlertDialog.Builder(DrawNote.this);

        DialogInterface.OnClickListener oc  = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which)
                {
                    case DialogInterface.BUTTON_POSITIVE:

                        String noteid = ""+et.getText();

                        saveimg(noteid);


                        break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                }
            }
        };

        adb.setView(et);
        adb.setPositiveButton("Done ", oc ).setNegativeButton("No" , oc);

        AlertDialog ad =  adb.create();

 ad.show();

    }


    public void saveimg(String noteid)
    {
if(noteid.equals(""))
{
    tost(" plz enter some ");
}else
{
    mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
        @Override
        public void onDrawCreated(Bitmap image) {
            // The draw Bitmap is the drawn content of the View

            byte []  imgarr =  getBytes(image);

            if(dndb.addImage(noteid,imgarr))
            {
                tost("image added ");
            }else
            {
                tost(" image not added maybe duplicate primary key ");
                //update
            }
        }

        @Override
        public void onDrawCreationError() {

        }

    });

}
    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    public void queryalldraw()
    {
       Cursor c= dndb.getallimgs();
     c.moveToFirst();

     String str = "";
     do {
         str += ""+c.getString(0);
        str +=  ""+c.getBlob(1);
     }
     while (c.moveToNext());

     tost(str);
    }



    public void changebrushsize()
    {
        androidx.appcompat.app.AlertDialog.Builder adb  = new androidx.appcompat.app.AlertDialog.Builder(DrawNote.this);
        DialogInterface .OnClickListener oc =  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which)
                {
                    case DialogInterface.BUTTON_POSITIVE :

                        // change brush size
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        fsl =  new FluidSlider(DrawNote.this);
        LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fsl.setLayoutParams(lp);

        fsl.setStartText("0");
        fsl.setEndText("20");

        fsl.setPositionListener(pos -> {
            final String value = String.valueOf( (int)((1 - pos) * 100) );
            fsl.setBubbleText(""+pos*20);
            mSignatureView.setPaintWidthDp(pos*20);
            return Unit.INSTANCE;
        });

        adb.setView(fsl);

        adb.setPositiveButton("Done",oc);
        androidx.appcompat.app.AlertDialog ad =  adb.create();

        ad.show();
    }




    public void backcolor() {


        final ColorPicker cp = new ColorPicker(DrawNote.this,Br, Bg , Bb);
        cp.show();



        Button okColor = (Button)cp.findViewById(R.id.okColorButton);

        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* You can get single channel (value 0-255) */
                Br = cp.getRed();
                Bg = cp.getGreen();
                Bb = cp.getBlue();

                /* Or the android RGB Color (see the android Color class reference) */

                int c = cp.getColor();

                mSignatureView.setBackgroundColor(Color.rgb(Br,Bg,Bb));

                cp.dismiss();
            }
        });



    }
}
