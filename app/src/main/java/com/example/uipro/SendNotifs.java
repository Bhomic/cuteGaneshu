package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotifs extends AppCompatActivity {

EditText ettitle;
EditText etbody;

    RequestQueue rq;

String url = "https://fcm.googleapis.com/fcm/send";

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifs);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rq =  Volley.newRequestQueue(this);

        ettitle = findViewById(R.id.notiftitle);
        etbody = findViewById(R.id.notifbody);


        FirebaseMessaging.getInstance().subscribeToTopic("CHATS").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void SendNotiffun(View view) {

        //https://fcm.googleapis.com/fcm/send

        String titlestr = "";
        String bodystr  = "";

        titlestr = ""+ettitle.getText();
        bodystr = ""+etbody.getText();

        if(titlestr.equals("") || bodystr.equals("") )
        {
            tost(" plz enter the title and body of notification  ");
;        }
else {
            JSONObject mainobj = new JSONObject();

            try {

                mainobj.put("to", "/topics/" + "CHATS");
                JSONObject notifobj = new JSONObject();

                notifobj.put("title", ""+titlestr);
                notifobj.put("body", ""+bodystr);

                mainobj.put("notification", notifobj);


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, mainobj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // notif successfully sent

                                tost("notif send resp : " + response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // notif error occurred
                        tost("error: " + error);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        header.put("content-type", "application/json");
                        header.put("authorization", "key=AIzaSyAdM4s52OrcsJxUwL837Ke3Db2QtXHLZaE");
                        return header;
                    }
                };

                rq.add(request);
            } catch (Exception e) {
                tost("" + e.getMessage());
            }
        }
    }
}
