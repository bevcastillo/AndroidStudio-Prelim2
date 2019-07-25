package com.example.prelim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE = "namedb";
    static String NAMES = "namestbl";
    static String COL_ID = "id";
    static String COL_NAME = "name";
    static String COL_IMAGE = "image";

    public DatabaseHelper(Context context){
        super(context, DATABASE, null, 1);
    }

    private static final String CREATE_TABLE_NAMES = "CREATE TABLE "
            + NAMES + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IMAGE + " TEXT, "
            + COL_NAME + " URI);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NAMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + NAMES + "' ");
        onCreate(db);
    }

    //method to insert the data into the database
    public void addNames(Uri image, String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(COL_IMAGE, image);
        cv.put(COL_NAME, name);
        database.insert(DATABASE, null, cv);
    }

    //method to display all data from the database to the listvie
    public ArrayList<Names> getAll(){
        ArrayList<Names> namesArrayList = new ArrayList<Names>();

        String selectQuery = "SELECT * FROM " + NAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        //loop
        if(c.moveToFirst()){
            do{
//                Names names = new Names();
//                names.setId(c.getInt(c.getColumnIndex(COL_ID)));
//                names.setImageUri(c.getBlob(c.getColumnIndex(COL_IMAGE)));
//                names.setName(c.getString(c.getColumnIndex(COL_NAME)));
            }while (c.moveToNext());
        }
        return namesArrayList;
    }

}
