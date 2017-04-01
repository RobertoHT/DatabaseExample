package micromaster.galileo.edu.databaseexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import micromaster.galileo.edu.databaseexample.model.Contact;

import static micromaster.galileo.edu.databaseexample.database.DataBaseHelper.COLUMN_EMAIL;
import static micromaster.galileo.edu.databaseexample.database.DataBaseHelper.COLUMN_LAST_NAME;
import static micromaster.galileo.edu.databaseexample.database.DataBaseHelper.COLUMN_NAME;
import static micromaster.galileo.edu.databaseexample.database.DataBaseHelper.COLUMN_PHONE_NUMBER;
import static micromaster.galileo.edu.databaseexample.database.DataBaseHelper.TABLE_CONTACTS;

/**
 * Created by Byron on 3/26/2017.
 */

public class DataBaseDAO {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBaseDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }

    public void addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_LAST_NAME, contact.getLastName());
        values.put(COLUMN_EMAIL, contact.getEmail());
        values.put(COLUMN_PHONE_NUMBER, contact.getPhoneNumber());

        int result = (int) database.insert(TABLE_CONTACTS, null, values);
        Log.i("DB", "insert: " + result);
    }
    
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(TABLE_CONTACTS, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));

                contacts.add(new Contact(name, lastName, email, phoneNumber));
            }while (cursor.moveToNext());
        }

        return contacts;
    }

}
