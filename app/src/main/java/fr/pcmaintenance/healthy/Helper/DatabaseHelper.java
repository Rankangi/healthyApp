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
import fr.pcmaintenance.healthy.Modele.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DIM";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "./databases/";
    private static final String DATABASE_NAME = "HealthyApp";

    private static final String TABLE_DATES = "dates";
    private static final String TABLE_NAMES = "names";

    // NOTES Dates - column names
    private static final String KEY_DATES_DATE = "date";
    private static final String KEY_DATES_HEALTH = "date_health";


    // NOTES Names - column names
    private static final String KEY_NAMES_NAME = "name";
    private static final String KEY_NAMES_BIRTHDAY = "date_de_naissance";
    private static final String KEY_NAMES_SEXE = "sexe";
    private static final String KEY_NAMES_TAILLE = "taille";
    private static final String KEY_NAMES_POIDS = "poids";
    private static final String KEY_NAMES_ACTIVITY = "activité";
    private static final String KEY_NAMES_OBJECTIF = "objectif";

    private static final String CREATE_TABLE_DATES = "CREATE TABLE "
            + TABLE_DATES + "("
            + KEY_DATES_DATE + " DATETIME PRIMARY KEY,"
            + KEY_DATES_HEALTH + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_NAMES = "CREATE TABLE "
            + TABLE_NAMES + "("
            + KEY_NAMES_NAME + " STRING PRIMARY KEY,"
            + KEY_NAMES_BIRTHDAY + " DATETIME,"
            + KEY_NAMES_SEXE + " INTEGER,"
            + KEY_NAMES_TAILLE + " INTEGER,"
            + KEY_NAMES_POIDS + " FLOAT,"
            + KEY_NAMES_ACTIVITY + " INTEGER,"
            + KEY_NAMES_OBJECTIF + " INTEGER"
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
            Log.w(LOG,"CREATE_TABLE_NAMES   : ");
            db.execSQL(CREATE_TABLE_NAMES);
        }

    }

    //Check database already exist or not
    public boolean checkDataBase(){

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

    public int getHealth(String date){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuerry = "SELECT " + KEY_DATES_HEALTH + " FROM " + TABLE_DATES
                + " WHERE " + KEY_DATES_DATE + " = '" + date + "'";
        Cursor c = db.rawQuery(selectQuerry, null);
        if (c.moveToFirst()){
            return c.getInt(c.getColumnIndex(KEY_DATES_HEALTH));
        }
        return -1;
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

    public User getUser(){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuerry = "SELECT  * FROM " + TABLE_NAMES;
        Cursor c = db.rawQuery(selectQuerry, null);
        User user = new User();
        if (c.moveToFirst()){
            user.setName(c.getString(c.getColumnIndex(KEY_NAMES_NAME)));
            user.setBirthday(c.getString(c.getColumnIndex(KEY_NAMES_BIRTHDAY)));
            user.setSexe(c.getInt(c.getColumnIndex(KEY_NAMES_SEXE)));
            user.setTaille(c.getInt(c.getColumnIndex(KEY_NAMES_TAILLE)));
            user.setPoids(c.getFloat(c.getColumnIndex(KEY_NAMES_POIDS)));
            user.setActivité(c.getInt(c.getColumnIndex(KEY_NAMES_ACTIVITY)));
            user.setObjectif(c.getInt(c.getColumnIndex(KEY_NAMES_OBJECTIF)));
            return user;
        }
        return null;
    }
}
