package example.metadesignassignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kumargaurav on 29/04/16.
 */
public class Employee_Database extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Employee_Data.db";
    public static final String TABLE_NAME1 = "signup_data";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";
    public static final String COLUMN_NAME_LAST_NAME = "last_name";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String TABLE_NAME2 = "profile_data";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String COLUMN_NAME_PHONE = "phone_number";
    public static final String COLUMN_NAME_DESIGNATION = "designation";
    public static final String COLUMN_NAME_DEPARTMENT = "department";
    public static final String COLUMN_NAME_INCOME = "income";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = " INTEGER";





    public Employee_Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1="CREATE TABLE " + TABLE_NAME1 + " (" +
                COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_EMAIL+TEXT_TYPE+" )";

        String CREATE_TABLE2="CREATE TABLE " + TABLE_NAME2 + " (" +
                COLUMN_NAME_ID + " TEXT ," +
                COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DESIGNATION + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DEPARTMENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_INCOME+TEXT_TYPE+COMMA_SEP+
                " FOREIGN KEY ("+COLUMN_NAME_ID+") REFERENCES "+TABLE_NAME1+" ("+COLUMN_NAME_ID+") "+
                " )";

        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
