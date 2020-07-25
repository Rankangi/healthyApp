package fr.pcmaintenance.healthy;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.format.TextStyle;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import fr.pcmaintenance.healthy.Decorator.GreenDateDecorator;
import fr.pcmaintenance.healthy.Decorator.LightGreenDateDecorator;
import fr.pcmaintenance.healthy.Decorator.OrangeDateDecorator;
import fr.pcmaintenance.healthy.Decorator.RedDateDecorator;
import fr.pcmaintenance.healthy.Decorator.YellowDateDecorator;
import fr.pcmaintenance.healthy.Helper.DatabaseHelper;
import fr.pcmaintenance.healthy.Listener.OnSmileyClickListener;
import fr.pcmaintenance.healthy.Modele.Date;

public class CalendarViewApp extends Activity {

    private DatabaseHelper db;
    private TextView dateText;
    private LinearLayout smiley;
    private LinearLayout button;
    private MaterialCalendarView calendarView;
    private HashSet<CalendarDay> redListe = new HashSet<CalendarDay>();
    private HashSet<CalendarDay> orangeListe  = new HashSet<CalendarDay>();
    private HashSet<CalendarDay> yellowListe  = new HashSet<CalendarDay>();
    private HashSet<CalendarDay> lightGreenListe  = new HashSet<CalendarDay>();
    private HashSet<CalendarDay> greenListe = new HashSet<CalendarDay>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the database
        db = new DatabaseHelper(getApplicationContext());

        // Get the CalendarView
        setContentView(R.layout.activity_calendar_view_app);
        calendarView = findViewById(R.id.calendarView);


        // Set listener on calendarView when the date changed
        calendarView.setOnDateChangedListener(onDateChanged);

        // Get date TextView and the Layout of the smiley
        dateText = findViewById(R.id.date);
        smiley = findViewById(R.id.smiley);
        button = findViewById(R.id.layout_button);

        // Get each smiley
        RelativeLayout smileyRouge = findViewById(R.id.smileyRouge);
        RelativeLayout smileyOrange = findViewById(R.id.smileyOrange);
        RelativeLayout smileyJaune = findViewById(R.id.smileyJaune);
        RelativeLayout smileyVertClaire = findViewById(R.id.smileyVertClaire);
        RelativeLayout smileyVert = findViewById(R.id.smileyVert);

        // Set listener for each smiley
        smileyRouge.setOnClickListener(new OnSmileyClickListener(smileyRouge,db,calendarView,this));
        smileyOrange.setOnClickListener(new OnSmileyClickListener(smileyOrange,db,calendarView,this));
        smileyJaune.setOnClickListener(new OnSmileyClickListener(smileyJaune,db,calendarView,this));
        smileyVertClaire.setOnClickListener(new OnSmileyClickListener(smileyVertClaire,db,calendarView,this));
        smileyVert.setOnClickListener(new OnSmileyClickListener(smileyVert,db,calendarView,this));

        // Load the decorator for the calendarView
        loadDecorator();

    }

    private void loadDecorator() {
        List<Date> listeDate = db.getAllDate();
        for (Date date :listeDate) {
            switch (date.getHealth()){
                case 0:
                    redListe.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                    break;
                case 1:
                    orangeListe.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                    break;
                case 2:
                    yellowListe.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                    break;
                case 3:
                    lightGreenListe.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                    break;
                case 4:
                    greenListe.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                    break;
                default:
                    continue;
            }
        }
        setDecorator();
    }

    private void setDecorator() {
        if (!redListe.isEmpty()){
            calendarView.addDecorator(new RedDateDecorator(redListe, getApplicationContext()));
        }
        if (!orangeListe.isEmpty()){
            calendarView.addDecorator(new OrangeDateDecorator(orangeListe, getApplicationContext()));
        }
        if (!yellowListe.isEmpty()){
            calendarView.addDecorator(new YellowDateDecorator(yellowListe, getApplicationContext()));
        }
        if (!lightGreenListe.isEmpty()){
            calendarView.addDecorator(new LightGreenDateDecorator(lightGreenListe, getApplicationContext()));
        }
        if (!greenListe.isEmpty()){
            calendarView.addDecorator(new GreenDateDecorator(greenListe, getApplicationContext()));
        }
    }


    // On date Changed listener
    private OnDateSelectedListener onDateChanged = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            dateText.setText(date.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH) + ", " + date.getDate().getDayOfMonth() + " " + date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE) + " " + date.getYear());
            smiley.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    };

    // Update a date Health
    public void update(String date, int health, int oldHealth) {
        if (oldHealth == -2){
            return;
        }
        Date newDate = new Date();
        newDate.setHealth(health);
        newDate.setDate(date);
        CalendarDay newCalendarDay = CalendarDay.from(newDate.getYear(),newDate.getMonth(),newDate.getDay());
        switch (health){
            case 0:
                redListe.add(newCalendarDay);
                break;
            case 1:
                orangeListe.add(newCalendarDay);
                break;
            case 2:
                yellowListe.add(newCalendarDay);
                break;
            case 3:
                lightGreenListe.add(newCalendarDay);
                break;
            case 4:
                greenListe.add(newCalendarDay);
                break;
        }
        if (oldHealth != -1){
            removeListe(date, oldHealth);
        }
        setDecorator();
    }

    // remove a CalendarDay in a list
    private void removeListe(String date, int health) {
        HashSet<CalendarDay> liste = null;
        switch (health){
            case 0:
                liste = redListe;
                break;
            case 1:
                liste = orangeListe;
                break;
            case 2:
                liste = yellowListe;
                break;
            case 3:
                liste = lightGreenListe;
                break;
            case 4:
                liste = greenListe;
                break;
        }
        if (liste == null){ return;}
        CalendarDay mDay = null;
        for (CalendarDay day:liste) {
            if (day.getDate().toString().equals(date)){
                mDay = day;
                break;
            }
        }
        liste.remove(mDay);
    }
}