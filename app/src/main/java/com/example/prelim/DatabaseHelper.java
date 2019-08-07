package com.example.prelim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/*
    created by Beverly May Castillo on July 31,2019
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE = "dbnames";
    static String NAMESTBL = "namestbl";
    static String COL_ID = "id";
    static String COL_IMAGE = "image";
    static String COL_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    private static final String CREATE_TABLE_NAMES = " CREATE TABLE " + NAMESTBL + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_IMAGE + " TEXT, " + COL_NAME +" TEXT)";

    //calling the create table query
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NAMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + NAMESTBL + "'");
    }

    public long addNames(String image, String myname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_IMAGE, image);
        cv.put(COL_NAME, myname);

        long insert = db.insert(NAMESTBL,null,cv);
        return insert;
    }


    public ArrayList<Names> getAll(){
        ArrayList<Names> list = new ArrayList<Names>();

        String selectQuery = "SELECT * FROM " + NAMESTBL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do{
                Names names = new Names();
                names.setId(c.getInt(c.getColumnIndex(COL_ID)));
                names.setImageUri(Uri.parse(c.getString(c.getColumnIndex(COL_IMAGE))));
                names.setName(c.getString(c.getColumnIndex(COL_NAME)));
                list.add(names);
            }while (c.moveToNext());
        }
        return list;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + NAMESTBL;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int updateNames(int id,String image, String myname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_IMAGE, image);
        cv.put(COL_NAME, myname);

        return db.update(NAMESTBL,cv,COL_ID +" = ? ", new String[]{String.valueOf(id)});
    }

    public void deleteNames(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NAMESTBL, COL_ID + " = ? ", new String[]{String.valueOf(id)});
    }
}
