package com.example.uipro;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SQLite extends AppCompatActivity {

    String data ="";


    DatabaseManager mDatabase;


    public void tost(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        mDatabase = new DatabaseManager(SQLite.this);



    }

    private void loadEmployeesFromDatabase() {

        if (mDatabase.addEmployee(10,"login"))
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add employee", Toast.LENGTH_SHORT).show();



        Cursor cursor = mDatabase.getAllEmployees();

        if (cursor.moveToFirst()) {
            do {

                data+="\n"+
                        cursor.getInt(0)+"  "+
                        cursor.getString(1);

            } while (cursor.moveToNext());

        }

        tost(""+data);

        TextView t  = findViewById(R.id.head);

        t.setText(data);


    }


    public void showdata(View view) {

        data = "";
   mDatabase.deleteall();

   /*
        if (mDatabase.addEmployee(10,"login"))
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add employee", Toast.LENGTH_SHORT).show();



        if(mDatabase.updateEmployee(10,"Logout"))
        {
            tost("updated ");
        }else
        {
            tost("not updated ");
        }
*/
   try {
       Cursor cursor = mDatabase.getAllEmployees();

       tost("" + cursor);


       if (cursor != null && cursor.getCount() > 0) {

           cursor.moveToFirst();

           data += "\n" +


                   cursor.getInt(0) + "  " +
                   cursor.getString(1);
           tost("" + data);


           tost("" + data);

           cursor.close();
       }else
       {
          if( mDatabase.addEmployee(10,"testing"))
          {
              tost(" data added ! ");
          }else {
              tost("data add failed ");
          }


       }

   }catch(Exception e)
   {
       tost("exp: "+e.getMessage());

   }finally {

   }

    }
}
