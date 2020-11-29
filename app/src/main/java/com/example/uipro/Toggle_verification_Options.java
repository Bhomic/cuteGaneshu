package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.HashMap;
import java.util.Map;

public class Toggle_verification_Options extends AppCompatActivity {

    FirebaseFirestore ff;

JellyToggleButton jtb , jtb2,jtb3,jtb4;
    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), ""+msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_verification__options);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ff = FirebaseFirestore.getInstance();

        /*
        isChecked() Whether the JTB is checked.
setChecked(boolean checked) Set the JTB to checked or not with animation. If the listener has been set, it will be called.
setChecked(boolean checked, boolean callListener) Same as above, but you can choose whether call the listener(if not null).
setCheckedImmediately(boolean checked) Set the JTB to checked or not immediately without animation. This method will call the listener if it's not null.
setCheckedImmediately(boolean checked, boolean callListener) Same as above, and you can choose whether call the listener.
toggle() Change the JTB to another state and call the listener.
toggle(boolean callListener) Same as above and you can choose whether call the listener.
toggleImmediately() Toggle, without animation and call the listener.
toggleImmediately(boolean callListener) Toggle, without animation and you can choose not to call the listener


    setLeftBackgroundColor(int color)
    setLeftBackgroundColor(String color)
    ```setLeftBackgroundColorRes(int res)``

jtb.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
    @Override
    public void onStateChange(float process, State state, JellyToggleButton jtb) {
        // process - current process of JTB, between [0, 1]
        // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
        // jtb     - the JTB
    }
});
         */


         jtb  = findViewById(R.id.jtb);

        jtb.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {

            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                if(process == 0.0 || process == 1.0)
                tost("1 "+process);

            }
        });


         jtb2  = findViewById(R.id.jtb2);

        jtb2.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {

            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                if(process == 0.0 || process == 1.0)
                    tost("2 "+process);

            }
        });



         jtb3  = findViewById(R.id.jtb3);

        jtb3.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {

            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                if(process == 0.0 || process == 1.0)
                    tost("3 "+process);

            }
        });



         jtb4  = findViewById(R.id.jtb4);

        jtb4.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {

            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // process - current process of JTB, between [0, 1]
                // state   - current state of JTB, it is one of State.LEFT, State.LEFT_TO_RIGHT, State.RIGHT and State.RIGHT_TO_LEFT
                // jtb     - the JTB

                if(process == 0.0 || process == 1.0)
                    tost("4 "+process);

            }
        });

syncoptions();

    }



    public void checkallbuttons()
    {
        boolean op1 = false ,op2 =  false,op3 = false,op4 = false;

        if(jtb.isChecked())
        {
            op1 =  true;
        }

        if(jtb2.isChecked())
        {
            op2= true;
        }

        if(jtb3.isChecked())
        {
            op3 =  true;
        }

        if(jtb4.isChecked())
        {
            op4 = true;
        }

        tost("selections : "+op1+op2+op3+op4);

        Map<String,Object> op  = new HashMap<>();
        op.put("exp",op1);
        op.put("reg",op2);
        op.put("qr",op3);
        op.put("ots",op4);

        ff.collection("VERIFICATIONSELECTION").document("IQSE").set(op).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                tost("data uploaded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tost(" failed to upload data ");
            }
        });
    }

    public void showselectionsfun(View view) {
        checkallbuttons();
    }


    public void syncoptions()
    {
        ff.collection("VERIFICATIONSELECTION").document("IQSE").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot !=  null  && documentSnapshot.exists())
                {
                    Map<String, Object> op =  documentSnapshot.getData();

                    boolean op1 =  (boolean)op.get("exp");
                    boolean op2 =  (boolean)op.get("reg");
                    boolean op3 =  (boolean)op.get("qr");
                    boolean op4 =  (boolean)op.get("ots");

                    if(op1)jtb.setChecked(true); else jtb.setChecked(false);
                    if(op2)jtb2.setChecked(true); else jtb2.setChecked(false);
                    if(op3)jtb2.setChecked(true); else jtb3.setChecked(false);
                    if(op4)jtb4.setChecked(true); else jtb4.setChecked(false);

                }
            }
        });
    }

}
