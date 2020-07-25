package fr.pcmaintenance.healthy.Listener;
import android.view.View;
import android.widget.RelativeLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import fr.pcmaintenance.healthy.CalendarViewApp;
import fr.pcmaintenance.healthy.Helper.DatabaseHelper;

public class OnSmileyClickListener implements View.OnClickListener {

    private RelativeLayout mlayout;
    private DatabaseHelper db;
    private MaterialCalendarView calendarView;
    private CalendarViewApp app;

    public OnSmileyClickListener(RelativeLayout layout, DatabaseHelper database, MaterialCalendarView calendar, CalendarViewApp currentApp) {
        mlayout = layout;
        db = database;
        calendarView = calendar;
        app = currentApp;
    }

    @Override
    public void onClick(View view) {
        if (calendarView.getSelectedDate().isAfter(CalendarDay.today())){ return;}
        String color = view.getResources().getResourceEntryName(mlayout.getId());
        int oldHealth;
        switch (color){
            case ("smileyRouge"):
                oldHealth = db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 0);
                app.update(calendarView.getSelectedDate().getDate().toString(), 0, oldHealth);
                break;
            case ("smileyOrange"):
                oldHealth = db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 1);
                app.update(calendarView.getSelectedDate().getDate().toString(), 1, oldHealth);
                break;
            case ("smileyJaune"):
                oldHealth = db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 2);
                app.update(calendarView.getSelectedDate().getDate().toString(), 2, oldHealth);
                break;
            case ("smileyVertClaire"):
                oldHealth = db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 3);
                app.update(calendarView.getSelectedDate().getDate().toString(), 3, oldHealth);
                break;
            case ("smileyVert"):
                oldHealth = db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 4);
                app.update(calendarView.getSelectedDate().getDate().toString(), 4, oldHealth);
                break;
            default:
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), -1);
        }

    }
}
