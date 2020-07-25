package fr.pcmaintenance.healthy.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.pcmaintenance.healthy.Modele.Date;

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

    /*
        Renvoie -1 si on ajoute une date
        -2 si on ne modifie pas de date
        sinon renvoie l'humeur de la date (0-5)
     */
    public int setHealthToDate(String date, int health) {
        Date currentDate = getDate(date);
        if (currentDate.getDate() == null){
            addDate(date,health);
            return -1;
        }else if (currentDate.getHealth() != health){
            updateDate(date,health);
            return currentDate.getHealth();
        }
        return -2;
    }

    private void updateDate(String date, int health) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dateBDD = new ContentValues();
        dateBDD.put(KEY_DATES_DATE, date);
        dateBDD.put(KEY_DATES_HEALTH, health);
        db.update(TABLE_DATES, dateBDD, KEY_DATES_DATE + " = ?", new String[] {date});
    }

    private void addDate(String date, int health) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dateBDD = new ContentValues();
        dateBDD.put(KEY_DATES_DATE, date);
        dateBDD.put(KEY_DATES_HEALTH, health);
        db.insert(TABLE_DATES, null, dateBDD);
    }

    public Date getDate(String date){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_DATES
                + " WHERE " + KEY_DATES_DATE + " = '" + date + "'";
        Cursor c = db.rawQuery(selectQuerry, null);
        Date mdate = new Date();
        if (c.moveToFirst()){
            mdate.setDate(date);
            mdate.setHealth(c.getInt(c.getColumnIndex(KEY_DATES_HEALTH)));
        }
        c.close();
        return mdate;
    }


    public List<Date> getAllDate(){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuerry = "SELECT  * FROM " + TABLE_DATES;
        Cursor c = db.rawQuery(selectQuerry, null);
        List<Date> listDate = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                Date mdate = new Date();
                mdate.setDate(c.getString(c.getColumnIndex(KEY_DATES_DATE)));
                mdate.setHealth(c.getInt(c.getColumnIndex(KEY_DATES_HEALTH)));
                listDate.add(mdate);
            } while (c.moveToNext());
        }
        c.close();
        return listDate;
    }

}
