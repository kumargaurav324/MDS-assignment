package example.metadesignassignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kumargaurav on 29/04/16.
 */
public class Use_Database {
    Employee_Database employee_database;

    public Use_Database(Context context) {
        employee_database=new Employee_Database(context);
    }

    public long save_table1(SignUp_Data signUp_data)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = employee_database.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Employee_Database.COLUMN_NAME_ID, signUp_data.getId());
        values.put(Employee_Database.COLUMN_NAME_FIRST_NAME, signUp_data.getFirst_name());
        values.put(Employee_Database.COLUMN_NAME_LAST_NAME, signUp_data.getLast_name());
        values.put(Employee_Database.COLUMN_NAME_EMAIL, signUp_data.getEmail());


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Employee_Database.TABLE_NAME1,null,values);
        return newRowId;
    }
    public long save_table2(Profile_Data profile_data)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = employee_database.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Employee_Database.COLUMN_NAME_ID, profile_data.getId());
        values.put(Employee_Database.COLUMN_NAME_ADDRESS, profile_data.getAddress());
        values.put(Employee_Database.COLUMN_NAME_PHONE, profile_data.getPhone());
        values.put(Employee_Database.COLUMN_NAME_DESIGNATION, profile_data.getDesignation());
        values.put(Employee_Database.COLUMN_NAME_DEPARTMENT, profile_data.getDepartment());
        values.put(Employee_Database.COLUMN_NAME_INCOME, profile_data.getIncome());


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Employee_Database.TABLE_NAME2,null,values);

        return newRowId;
    }

    public Cursor getdata_table1(String username)
    {
        SQLiteDatabase db = employee_database.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                Employee_Database.COLUMN_NAME_ID,
                Employee_Database.COLUMN_NAME_EMAIL
        };

        Cursor c = db.query(
                Employee_Database.TABLE_NAME1,  // The table to query
                projection,                               // The columns to return
                Employee_Database.COLUMN_NAME_EMAIL+ "=?",                                // The columns for the WHERE clause
                new String[]{username},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return c;
    }
    public Cursor getdata_table2(String id)
    {
        SQLiteDatabase db = employee_database.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                Employee_Database.COLUMN_NAME_ID,
                Employee_Database.COLUMN_NAME_ADDRESS,
                Employee_Database.COLUMN_NAME_PHONE,
                Employee_Database.COLUMN_NAME_DESIGNATION,
                Employee_Database.COLUMN_NAME_DEPARTMENT,
                Employee_Database.COLUMN_NAME_INCOME
        };

        Cursor c = db.query(
                Employee_Database.TABLE_NAME2,  // The table to query
                projection,                               // The columns to return
                Employee_Database.COLUMN_NAME_ID+ " = ?",                                // The columns for the WHERE clause
                new String[]{id},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return c;
    }

    public int update_table2(String id,Profile_Data profile_data)
    {
        SQLiteDatabase db = employee_database.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(Employee_Database.COLUMN_NAME_ID, profile_data.getId());
        values.put(Employee_Database.COLUMN_NAME_ADDRESS, profile_data.getAddress());
        values.put(Employee_Database.COLUMN_NAME_PHONE, profile_data.getPhone());
        values.put(Employee_Database.COLUMN_NAME_DESIGNATION, profile_data.getDesignation());
        values.put(Employee_Database.COLUMN_NAME_DEPARTMENT, profile_data.getDepartment());
        values.put(Employee_Database.COLUMN_NAME_INCOME, profile_data.getIncome());
// Which row to update, based on the ID
        String selection = Employee_Database.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { id };

        int count = db.update(
                Employee_Database.TABLE_NAME2,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
