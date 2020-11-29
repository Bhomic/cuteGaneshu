package com.example.uipro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.ramotion.fluidslider.FluidSlider;

import kotlin.Unit;


public class Createnote extends AppCompatActivity {

    FluidSlider fsl ;

   // BubbleEmitterView  bev  ;

    NoteDatabase ndb ;

    BoomMenuButton bmb;

    EditText noteid , note;


    String task  ;
    String intnoteid;

    public void tost(String str )
    {
        Toast.makeText(Createnote.this,str, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//bev  = findViewById(R.id.bev);
//bev.emitBubble(20);

bmb  = (BoomMenuButton) findViewById(R.id.bmb);
        ndb = new NoteDatabase(Createnote.this);

        noteid =  findViewById(R.id.noteid);
        note =  findViewById(R.id.note);

        String titles []= {"Save Note * ^" , "Delete Note" ,"Query all notes","Edit" ,"Brush Size"};

        String subt[]={"Saves ur note to ur phone " , " Delete current Note " ," Get all notes added ","Edit Your Note " ," Change your Brush Size"};


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
                            Toast.makeText(Createnote.this, "Clicked " + index, Toast.LENGTH_LONG).show();


                            if(index ==0)
                            {
                                // save current note

savenote();

                            }else if(index  == 1)
                            {
delnote();
                                // delete current note and then intent ot takenotes

                            }else if(index == 2)
                            {
                                getallnotes();
                            }else if( index ==  3)
                            {
                                //edit note
                                noteid.setEnabled(true);
                                note.setEnabled(true);
                            }else if(index == 4)
                            {
                                // brush size ..

                                changebrushsize();
                            }



                        }
                    });
            bmb.addBuilder(builder);
        }


        task =  this.getIntent().getExtras().getString("task");

        if(task.equals("edit")) {
            intnoteid = this.getIntent().getExtras().getString("noteid");
            sync(intnoteid);

        }

    }



    public void savenote()
    {
        String idstr =  ""+noteid.getText();
        String notestr = ""+note.getText();

        if(idstr.equals("") || notestr.equals(""))
        {
            tost(" something missing ");
        }else
        {
            if(ndb.addNote(idstr, notestr))
            {
                tost(" note "+idstr+" was successfully added * ^");
            }else
            {
                AlertDialog.Builder adb = new AlertDialog.Builder(Createnote.this);

                adb.setMessage("Already present note id : "+idstr +" UPDATE ? ");

                DialogInterface.OnClickListener oc =  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :

                                ndb.updatenote(idstr, notestr);

                                break;


                                case DialogInterface.BUTTON_NEGATIVE:

                                    break;

                        }
                         }
                };

                adb.setPositiveButton("YES",oc).setNegativeButton("NO",oc);
                AlertDialog ad =  adb.create();

                ad.show();
            }
        }
    }

    public  void delnote()
    {
        String idstr = ""+noteid.getText();
       if( ndb.delnote(idstr))
       {
           tost(" id: "+idstr+" deleted ");
       }
      //  startActivity(new Intent(Createnote.this, TakeNotes.class));
    }


    public void  getallnotes()
    {
        Cursor c =  ndb.getallnotes();

        c.moveToFirst();
        String res  = "";

        if(c.getCount()>0)
        {
            do {
                res +=  c.getString(0);



                res +=  c.getString(1);


            }while(c.moveToNext());

        }


        tost(""+res);

        c.close();
    }

    public void sync(String snoteid)
    {
        Cursor c =  ndb.getnote(snoteid);

        c.moveToFirst();

        if(c.getCount() >0 )
        {
            String snote = ""+c.getString(1);

            tost("notid : "+snoteid+" note :" +snote);
            noteid.setText(snoteid);
            note.setText(snote);

            noteid.setEnabled(false);
            note.setEnabled(false);
        }

    }



    public void changebrushsize()
    {
        AlertDialog.Builder adb  = new AlertDialog.Builder(Createnote.this);
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

        fsl =  new FluidSlider(Createnote.this);
        LinearLayout.LayoutParams lp  =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fsl.setLayoutParams(lp);

        fsl.setStartText("0");
        fsl.setEndText("20");

        fsl.setPositionListener(pos -> {
            final String value = String.valueOf( (int)((1 - pos) * 100) );
            fsl.setBubbleText(""+pos*20);
            return Unit.INSTANCE;
        });

        adb.setView(fsl);

        adb.setPositiveButton("Done",oc);
        AlertDialog ad =  adb.create();

        ad.show();
    }
}
