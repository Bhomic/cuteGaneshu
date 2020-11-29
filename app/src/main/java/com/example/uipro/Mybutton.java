package com.example.uipro;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class Mybutton extends MaterialFancyButton {
   public String qrpass;

    public Mybutton(Context context) {
        super(context);

        qrpass  = "";

        super.setBackgroundColor(Color.parseColor("#034472"));

        super.setBackgroundColor(Color.parseColor("#034472"));
        super.setBorderColor(Color.parseColor("#fffdd0"));
        super.setBorderWidth(2);
        super.setText("Default Text");
        super.setTextColor(Color.parseColor("#fffdd0"));
        super.setPadding(10,10,10,10);
        super.setRadius(10);
        super.setFocusBackgroundColor(Color.parseColor("#ffffff"));


        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,20,0,0);
        super.setLayoutParams(lp);

    }


    public String getqrpass()
    {
        return qrpass;
    }
}
