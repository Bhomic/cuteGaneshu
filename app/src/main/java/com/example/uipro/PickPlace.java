package com.example.uipro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.igalata.bubblepicker.model.PickerItem;
import com.rtchagas.pingplacepicker.PingPlacePicker;
import com.sucho.placepicker.AddressData;
import com.sucho.placepicker.Constants;
import com.sucho.placepicker.MapType;
import com.sucho.placepicker.PlacePicker;

public class PickPlace extends AppCompatActivity {

    private static final int REQUEST_PLACE_PICKER = 100;

    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(), msg  , Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_place);

        showPlacePicker();


       // suchopick();

    }


    /*

    public void suchopick()
    {
        PlacePicker.IntentBuilder intentb = new PlacePicker.IntentBuilder();

        intentb.setLatLong(40.748672, -73.985628)  // Initial Latitude and Longitude the Map will load into
            .showLatLong(true)  // Show Coordinates in the Activity
            .setMapZoom(12.0f)  // Map Zoom Level. Default: 14.0
            .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
            .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
            .setMarkerDrawable(R.drawable.logo) // Change the default Marker Image
            .setMarkerImageImageColor(R.color.colorPrimary)
            .setFabColor(Color.CYAN)

            .setPrimaryTextColor(R.color.colorOnPrimary) // Change text color of Shortened Address
            .setSecondaryTextColor(Color.BLACK) // Change text color of full Address
           // .setMapRawResourceStyle(R.raw.map_style)  //Set Map Style (https://mapstyle.withgoogle.com/)
            .setMapType(MapType.NORMAL)
            .onlyCoordinates(true);

        Intent intent=  intentb.build(this);

        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData = data.getParcelableExtra(Constants.ADDRESS_INTENT);

                tost(""+addressData);


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



     */


    private void showPlacePicker() {
        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();


        builder.setAndroidApiKey("AIzaSyCCYtP1zCFdSK09DJM9wTh5cs-R9JHliQ4");

        builder.setMapsApiKey("AIzaSyCCYtP1zCFdSK09DJM9wTh5cs-R9JHliQ4");

        // If you want to set a initial location rather then the current device location.
        // NOTE: enable_nearby_search MUST be true.
         //builder.setLatLng(new LatLng(28.7041, 77.1025));

        try {
            Intent placeIntent = builder.build(this);
            startActivityForResult(placeIntent, REQUEST_PLACE_PICKER);
        } catch (Exception ex) {

            tost("ex: "+ex.getMessage());

            // Google Play services is not available...
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if ((requestCode == REQUEST_PLACE_PICKER) && (resultCode == RESULT_OK)) {
                Place place = PingPlacePicker.getPlace(data);
                if (place != null) {
                    Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);

            }

        }catch(Exception e)
        {
            tost(""+e.getMessage());
        }
    }



}