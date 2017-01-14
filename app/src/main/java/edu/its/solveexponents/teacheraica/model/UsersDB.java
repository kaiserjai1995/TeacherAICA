package edu.its.solveexponents.teacheraica.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jairus on 1/14/17.
 */

public class UsersDB extends SQLiteOpenHelper {
    private static UsersDB sInstanceUsers;
    public static TeacherAICADB teacheraicadb;

    private SQLiteDatabase db;

    private DateFormat dateFormat;
    private Date dateCreated;

    private static final String DB_NAME = "teacheraicausersdb";
    private static final int DB_VERSION = 1;

    private Context context;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_USERNAME =  "username";
    public static final String COLUMN_PASSWORD =  "password";
    public static final String COLUMN_LASTNAME =  "lastname";
    public static final String COLUMN_FIRSTNAME =  "firstname";
    public static final String COLUMN_MIDDLENAME = "middlename";
    public static final String COLUMN_SECTION =  "section";
    public static final String COLUMN_AGE =  "age";
    public static final String COLUMN_GENDER =  "gender";
    public static final String COLUMN_DATECREATED =  "datecreated";


    public static synchronized UsersDB getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx

        if (sInstanceUsers == null) {
            sInstanceUsers = new UsersDB(context.getApplicationContext());
        }

        return sInstanceUsers;
    }

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_LASTNAME + " TEXT, "+
                    COLUMN_FIRSTNAME + " TEXT, " +
                    COLUMN_MIDDLENAME + " TEXT, " +
                    COLUMN_SECTION + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_DATECREATED + " DATE" +
                    ");";

    public UsersDB(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacheraica/" + DB_NAME, null, DB_VERSION);

        Log.d("USERSDB", Environment.getExternalStorageDirectory().getPath());

        this.context = context;
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        initializeDateTimeFormat();

        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void initializeDateTimeFormat() {
        this.dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
        this.dateFormat.setTimeZone(TimeZone.getDefault());
    }

    public void addUser(String username, String password, String lastname, String firstname, String middlename,
                        String section, String age, String gender) {
        initializeDateTimeFormat();
        this.dateCreated = new Date();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_LASTNAME, lastname);
        values.put(COLUMN_FIRSTNAME, firstname);
        values.put(COLUMN_MIDDLENAME, middlename);
        values.put(COLUMN_SECTION, section);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_DATECREATED, this.dateFormat.format(this.dateCreated));

        long id = db.insert(TABLE_NAME, null, values);
    }

    public String getUsername(String username, String password) {
        String _username = "";

        String sql = "SELECT *" +
                " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USERNAME + " = '" + username + "'" +
                " AND " + COLUMN_PASSWORD + " = '" + password + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                _username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                teacheraicadb.setDB_NAME(_username);
            }
        }
        return _username;
    }
}
