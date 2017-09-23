package com.example.ankitsharma.testcontact;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    String[] cols = new String[]{ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveAllContactstoDb(){



        //fetch contacts here
        Cursor contactsCursor = MainActivity.this.getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI, cols, null, null, null);

        try{

            if(contactsCursor != null && contactsCursor.getCount() > 0){

                contactsCursor.moveToFirst();

                while (!contactsCursor.isAfterLast()){
                    String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));
                    String number = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactsTable.init(MainActivity.this).insertContact(name, number);
                }
            }

        } finally {
            contactsCursor.close();
        }

    }

    public void getLatestContacts(){

        //get last contacts synced time here from shared preferences,
        //update this time whenever server is successfully updated with new contacts
        long lastSyncedTime = 0L;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastSyncedTime);//use last synced time instead of this

        String whereClause = ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + " >= " + lastSyncedTime;

        //fetch contacts whose modified time is greater or equal to last synced time
        Cursor contactsCursor = MainActivity.this.getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI, cols, whereClause, null, null);

        //this will return all the contacts modified or added after our last synced time
        //use this to get all new contacts, sync with server and update last synced time
    }

}
