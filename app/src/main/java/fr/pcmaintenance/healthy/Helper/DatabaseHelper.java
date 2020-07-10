package fr.pcmaintenance.healthy.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DIM";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "./databases/";
    private static final String DATABASE_NAME = "HealthyApp";

    private static final String TABLE_DATES = "dates";

    // NOTES Dates - column names
    private static final String KEY_DATES_DATE = "date";
    private static final String KEY_DATES_HEALTH = "date_health";


    private static final String CREATE_TABLE_DATES = "CREATE TABLE "
            + TABLE_DATES + "("
            + KEY_DATES_DATE + " DATETIME PRIMARY KEY,"
            + KEY_DATES_HEALTH + " INTEGER"
            + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        boolean dbExist = checkDataBase();

        if (dbExist){
            Log.w(LOG,"LA BDD EXISTE");
        }else{
            Log.w(LOG,"LA BDD N'EXISTE PAS");
            Log.w(LOG,"CREATE_TABLE_DATES   : ");
            db.execSQL(CREATE_TABLE_DATES);
        }

    }

    //Check database already exist or not
    private boolean checkDataBase(){

        boolean checkDB = false;

        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            Log.v(LOG, "myPath : " + myPath);

            File dbfile = new File(myPath);
            Log.v(LOG, "dbfile : " + dbfile);
            checkDB = dbfile.exists();
            Log.v(LOG, "checkDB : " + checkDB);
        }
        catch(SQLiteException e){
            Log.v(LOG, "PAS SUPPOSE PASSER ICI JAMAIS");
        }
        return checkDB;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);
    }
}
