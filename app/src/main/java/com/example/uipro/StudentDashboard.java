package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hanks.htextview.scale.ScaleTextView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.ParticleSystem;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;



//>>

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.bind.ObjectTypeAdapter;

import java.util.Map;

import javax.annotation.Nullable;
//>>

public class StudentDashboard extends AppCompatActivity {


    public void sweetalert()
    {
        // makea sweet alert
    }


  KonfettiView viewKonfetti;

  String name ="Myname", course ="Mycourse", rollno= "Myrollno";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);







        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
/*
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_3);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
            bmb.addBuilder(new SimpleCircleButton.Builder()
                    .normalImageRes(R.drawable.logo));

        }
  */



String titles []= {"Register for Future Events",
"Give Feedback " ,"Select Dynamic Form for Response" , "Chat","Gallery","Take Note"};

String subt[]={"Takes u to register for upcoming events",
        "Takes u to give feedback of IQSE form","Wanna Try Some new Forms ? ","Wanna Chat with Others Around ? " ,"Takes u to ur beutiful gallery ! ","Take a Note of Current Event "};


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
                            Toast.makeText(StudentDashboard.this, "Clicked " + index, Toast.LENGTH_LONG).show();


if(index ==0)
{
    //register
//startActivity(new Intent(StudentDashboard.this, Studentsefutureevents.class));

    sweetalert();

}else if(index  == 1)
{
    startActivity(new Intent(StudentDashboard.this,Dynamicform.class));
    //feedback  IQSE one
}else if(index == 2)
{
    // dynamic form select ..
//    startActivity(new Intent(StudentDashboard.this,SelectDynamicForm.class));
}else if(index == 3)
{
    // dynamic form select ..
 //   startActivity(new Intent(StudentDashboard.this,LiveChat4.class));
}else if(index == 4)
{
    // Gallery ev select  ..
//    startActivity(new Intent(StudentDashboard.this,ChooseEventforGallery.class));
}
else if(index == 5)
{
    //Note

    Intent i =  new Intent(StudentDashboard.this, TakeNotes.class);
    startActivity(i);
}



                        }
                    });
            bmb.addBuilder(builder);
        }


        oncreate2();


    }


    int nmit  =0;

    public void changename(View v)
    {
        ScaleTextView s =  (ScaleTextView)v;
        if(nmit++%2 ==1)
        s.animateText("Name");
        else
            s.animateText(name);
    }

    int crit  =0;
    public void changecourse(View v)
    {

        ScaleTextView s =  (ScaleTextView)v;
        if(crit++%2 ==1)
            s.animateText("Course");
        else
            s.animateText(course);
    }

    int rlit  =0;
    public void changerollno(View v)
    {

        ScaleTextView s =  (ScaleTextView)v;
        if(rlit++%2 ==1)
            s.animateText("Roll No");
        else
            s.animateText(rollno);
    }




    //>>

    TextView fn , em , ph , wel;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();

    }

    String TAG ="Main";
    FirebaseFirestore fstore;

    FirebaseAuth fauth  ;

    //>>
    public void oncreate2()
    {
        fauth = FirebaseAuth.getInstance();


        fstore = FirebaseFirestore.getInstance();



        String uid= fauth.getCurrentUser().getUid();



        final String eml = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        tost("got email : "+ eml);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.document("register/"+eml).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {




                if(documentSnapshot != null && documentSnapshot.exists())
                {

                    Map<String , Object> hm =  documentSnapshot.getData() ;

                    String namel =  ""+hm.get("fname");

name =  namel;

course =  ""+hm.get("course");

rollno = ""+hm.get("rollno");




                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tost("failed data retrieval ");
            }
        });




    }

    public void logoutfun(View view) {

        FirebaseAuth.getInstance().signOut();;

        startActivity(new Intent(getApplicationContext(),UserLogin.class));

        finish();

    }
}
