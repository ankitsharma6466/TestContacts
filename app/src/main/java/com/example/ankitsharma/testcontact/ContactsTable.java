package com.example.ankitsharma.testcontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ankitsharma on 23/09/17.
 */

public class ContactsTable {

    public static final String TABLE_NAME = "DSTABLE";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "NAME";
    public static final String COL_NUMBER = "NUMBER";

    private static ContactsTable mInstance;
    private SQLiteDatabase database;

    public ContactsTable(Context context) {
        this.database = DbHelper.getInstance(context).getWritableDatabase();
    }

    public static ContactsTable init(Context context) {
        if (mInstance == null) {
            mInstance = new ContactsTable(context);
        }
        return mInstance;
    }


    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                + TABLE_NAME + " ("
                + COL_ID + " integer primary key, "
                + COL_NAME + " text, "
                + COL_NUMBER + " text unique)");
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertContact(String name, String phone){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_NUMBER, phone);

        try{
            database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getName(String phone){

        String[] selectionArgs = { phone };

        String where = COL_NUMBER + " like ?";

        Cursor cursor = database.query(TABLE_NAME, null, where, selectionArgs, null, null, null);

        //this returns the name of the contact for a particular phone number

    }
}
