package com.gmail.mateendev3.androiddatabasev2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //declaring members
    private static final String dbName = "STUDENT";


    //public constructor
    public DBHelper(Context context) {
        super(context, dbName, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAGG", "onCreate: ");

        //query to create table and create database
        String query = "create table Student (ID Integer primary key autoIncrement, Name Text, Age Integer, Email Text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAGG", "onUpgrade: ");
    }

    /**
     * method to add data to created table
     *
     * @param name  name of the student to add
     * @param age   age of the student to add
     * @param email email of the student to add
     * @return true if data added successfully else false
     */
    public boolean insertData(String name, int age, String email) {
        //getting instance of DB
        SQLiteDatabase database = getWritableDatabase();
        //creating instance of ContentValues
        ContentValues values = new ContentValues();
        //adding values to values
        values.put("Name", name);
        values.put("Age", age);
        values.put("Email", email);

        //member to get result if value is added
        long isAdded = database.insert("Student", null, values);
        return isAdded != -1;
    }

    /**
     * Method to getData from the table
     *
     * @return Cursor object pointing the table rows
     */
    public Cursor getData() {
        //getting db instance
        SQLiteDatabase database = getReadableDatabase();
        //query
        String query = "select * from Student";
        //returning instance of Cursor using db
        return database.rawQuery(query, null);
    }

    /**
     * method to deleted data from the table
     *
     * @param email to check if record exist
     * @return true if record deleted and false if not
     */
    public boolean deleteData(String email) {
        //getting instance of DB
        SQLiteDatabase database = getWritableDatabase();
        //query
        String query = "select * from Student where Email = ?";
        //getting cursor instance with query value
        Cursor cursor = database.rawQuery(query, new String[]{email});
        //condition to check if cursor has the row (value/data in it)
        if (cursor.getCount() > 0) {
            long isDeleted = database.delete("Student", "Email = ?",
                    new String[]{email});
            return isDeleted == 1;
        } else
            return false;
    }

    public boolean updateData(String name, String email, int age) {
        //getting instance of db
        SQLiteDatabase db = getWritableDatabase();
        //getting cursor instance with given query
        Cursor cursor = db.rawQuery(
                "select * from Student where Email = ?", new String[]{email}
        );
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("Name", name);
            values.put("Age", age);
            int isUpdated = db.update("Student", values, "Email = ?", new String[]{email});
            return isUpdated == 1;
        } else
            return false;
    }
}
