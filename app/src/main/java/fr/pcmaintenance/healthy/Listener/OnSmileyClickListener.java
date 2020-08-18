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
        int oldHealth = db.getHealth(calendarView.getSelectedDate().getDate().toString());
        switch (color){
            case ("smileyRouge"):
                app.update(calendarView.getSelectedDate().getDate().toString(), 0, oldHealth);
                break;
            case ("smileyOrange"):
                app.update(calendarView.getSelectedDate().getDate().toString(), 1, oldHealth);
                break;
            case ("smileyJaune"):
                app.update(calendarView.getSelectedDate().getDate().toString(), 2, oldHealth);
                break;
            case ("smileyVertClaire"):
                app.update(calendarView.getSelectedDate().getDate().toString(), 3, oldHealth);
                break;
            case ("smileyVert"):
                app.update(calendarView.getSelectedDate().getDate().toString(), 4, oldHealth);
                break;
            default:
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), -1);
        }

    }
}
