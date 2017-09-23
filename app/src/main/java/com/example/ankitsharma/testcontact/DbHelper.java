package com.example.ankitsharma.testcontact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ankitsharma on 23/09/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper dbHelperInstance;
    Context context;

    private DbHelper(Context context) {
        super(context, "hyv.db", null, 1);
        this.context = context;
    }

    public static DbHelper getInstance(Context context) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DbHelper(context);
        }
        return dbHelperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        ContactsTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        ContactsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
