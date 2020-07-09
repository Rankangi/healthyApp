package fr.pcmaintenance.healthy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.HashSet;

public class CalendarViewApp extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view_app);

        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        LocalDate cal1;
        cal1 = LocalDate.of(2020,8,1);
        LocalDate cal2 = LocalDate.of(2020,9,1);

        HashSet<CalendarDay> setDays = getCalendarDaysSet(cal1, cal2);
        int myColor = Color.rgb(255,0,0);
        calendarView.addDecorator(new DateDecorator(myColor, setDays, getApplicationContext()));

    }


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