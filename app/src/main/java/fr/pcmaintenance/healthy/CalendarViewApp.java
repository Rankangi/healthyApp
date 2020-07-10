package fr.pcmaintenance.healthy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.HashSet;

import fr.pcmaintenance.healthy.Decorator.DateDecorator;
import fr.pcmaintenance.healthy.Helper.DatabaseHelper;
import fr.pcmaintenance.healthy.Listener.OnSmileyClickListener;

public class CalendarViewApp extends Activity {

    DatabaseHelper db;
    TextView dateText;
    LinearLayout smiley;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_calendar_view_app);
        MaterialCalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangedListener(onDateChaned);
        dateText = findViewById(R.id.date);
        smiley = findViewById(R.id.smiley);
        RelativeLayout smileyRouge = findViewById(R.id.smileyRouge);
        RelativeLayout smileyOrange = findViewById(R.id.smileyOrange);
        RelativeLayout smileyJaune = findViewById(R.id.smileyJaune);
        RelativeLayout smileyVertClaire = findViewById(R.id.smileyVertClaire);
        RelativeLayout smileyVert = findViewById(R.id.smileyVert);
        smileyRouge.setOnClickListener(new OnSmileyClickListener(smileyRouge));
        smileyOrange.setOnClickListener(new OnSmileyClickListener(smileyOrange));
        smileyJaune.setOnClickListener(new OnSmileyClickListener(smileyJaune));
        smileyVertClaire.setOnClickListener(new OnSmileyClickListener(smileyVertClaire));
        smileyVert.setOnClickListener(new OnSmileyClickListener(smileyVert));



        LocalDate cal1;
        cal1 = LocalDate.of(2020,8,1);
        LocalDate cal2 = LocalDate.of(2020,9,1);

        HashSet<CalendarDay> setDays = getCalendarDaysSet(cal1, cal2);
        int myColor = Color.rgb(255,0,0);
        calendarView.addDecorator(new DateDecorator(myColor, setDays, getApplicationContext()));

    }

    private OnDateSelectedListener onDateChaned = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            dateText.setText(date.getDate().toString());
            smiley.setVisibility(2);
        }
    };


    private HashSet<CalendarDay> getCalendarDaysSet(LocalDate cal1, LocalDate cal2) {
        HashSet<CalendarDay> setDays = new HashSet<>();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        while (CalendarDay.from(cal1).isBefore(CalendarDay.from(cal2))) {
            CalendarDay calDay = CalendarDay.from(cal1);
            setDays.add(calDay);
            cal1 = cal1.plusDays(1);
        }
        return setDays;
    }
}