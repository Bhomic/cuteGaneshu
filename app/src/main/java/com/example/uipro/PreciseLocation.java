package com.example.uipro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hanks.htextview.rainbow.RainbowTextView;

import org.jetbrains.annotations.NotNull;

import mumayank.com.airlocationlibrary.AirLocation;

public class PreciseLocation extends AppCompatActivity {

    private AirLocation airLocation;

RainbowTextView tv  ;
    public  void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precise_location);
tv  = findViewById(R.id.rtv);
       getlocation();
    }


    public void getlocation()
    {
        // Fetch location simply like this whenever you need
        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NotNull Location location) {
                // do something


                double lat  = location.getLatitude();

                double lon =  location.getLongitude();

                tost("lat : "+lat+" long : "+lon);

                tv.setText("lat : "+lat+" long : "+lon);


                 double lat2 = 28.5100486;
                double long2 = 77.024602;


              double  distance = distance(lat, lon, lat2, long2)*1000;

            tost(" distance : "+distance+" metres ");

            }

            @Override
            public void onFailed(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
            }
        });
    }

    // override and call airLocation object's method by the same name
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);

    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void getloc(View view) {

        getlocation();
    }



     double toRadians( double degree)
    {

         double one_deg = (Math.PI) / 180;
        return (one_deg * degree);
    }

     double distance(double lat1,  double long1,
                        double lat2,  double long2)
    {
       // Haversine Formula


        double dlong = long2 - long1;
         double dlat = lat2 - lat1;

        double ans = Math.pow(Math.sin(dlat / 2), 2) +
            Math.cos(lat1) * Math.cos(lat2) *
                    Math.pow(Math.sin(dlong / 2), 2);

        ans = 2 * Math.asin(Math.sqrt(ans));


        double R = 6371;


        ans = ans * R;

        return ans;
    }



}
