package com.example.uipro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;






public class NoteDatabase extends SQLiteOpenHelper {


    public SQLiteDatabase db  ;
    private static final String DATABASE_NAME = "NoteDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "NoteTable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "NoteText";
  /*
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOIN_DATE = "joiningdate";
    private static final String COLUMN_SALARY = "salary";
*/

    NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }





    private SQLiteDatabase mDefaultWritableDatabase = null;

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultWritableDatabase != null){
            db = mDefaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }


    @Override
    public SQLiteDatabase getReadableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultWritableDatabase != null){
            db = mDefaultWritableDatabase;
        } else {
            db = super.getReadableDatabase();
        }
        return db;
    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY  AUTOINCREMENT ,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL " +
          /*       "    " + COLUMN_DEPT + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_JOIN_DATE + " datetime NOT NULL,\n" +
                "    " + COLUMN_SALARY + " double NOT NULL\n" +

            */
                ");";

        String tableq = "CREATE TABLE "+TABLE_NAME + " ( "+ COLUMN_ID +" VARCHAR(40) PRIMARY KEY "+" , "+COLUMN_NAME +" VARCHAR(65533) )";


        sqLiteDatabase.execSQL(tableq);

        this.mDefaultWritableDatabase = sqLiteDatabase;


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);


        this.mDefaultWritableDatabase = sqLiteDatabase;
        onCreate(sqLiteDatabase);

    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
    }


    boolean addNote(String id , String note )
    {
        ContentValues c  = new ContentValues();
        c.put(COLUMN_ID, id);
        c.put(COLUMN_NAME , note);

        mDefaultWritableDatabase =  getWritableDatabase();

        return mDefaultWritableDatabase.insert(TABLE_NAME, null, c) != -1;
    }


    boolean delnote(String id )
    {
        mDefaultWritableDatabase =  getWritableDatabase();
        return mDefaultWritableDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;

    }

    Cursor getallnotes()
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    boolean updatenote(String id , String note)
    {

        ContentValues c = new ContentValues();
        c.put(COLUMN_ID,id);
        c.put(COLUMN_NAME, note);

        mDefaultWritableDatabase =  getWritableDatabase();
        return mDefaultWritableDatabase.update(TABLE_NAME, c, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;

    }

    Cursor getnote(String noteid)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COLUMN_ID +" = ? ",new String[]{noteid});

    }


    //===========================================================
    //-----------------------------------------------------------

    boolean addEmployee(int id ,String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
    /*
        contentValues.put(COLUMN_DEPT, dept);
        contentValues.put(COLUMN_JOIN_DATE, joiningdate);
        contentValues.put(COLUMN_SALARY, salary);


     */

        mDefaultWritableDatabase =  getWritableDatabase();

        return mDefaultWritableDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }



    Cursor getAllEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    boolean updateEmployee(int id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
  /*
        contentValues.put(COLUMN_DEPT, dept);
        contentValues.put(COLUMN_SALARY, salary);


   */

        mDefaultWritableDatabase =  getWritableDatabase();
        return mDefaultWritableDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }


    boolean deleteEmployee(int id) {

        mDefaultWritableDatabase =  getWritableDatabase();
        return mDefaultWritableDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    public  void deleteall()
    {

        mDefaultWritableDatabase =  getWritableDatabase();
        mDefaultWritableDatabase.execSQL("DELETE FROM "+TABLE_NAME);
    }
}