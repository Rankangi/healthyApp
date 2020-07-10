package fr.pcmaintenance.healthy.Listener;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import fr.pcmaintenance.healthy.Helper.DatabaseHelper;

public class OnSmileyClickListener implements View.OnClickListener {

    private RelativeLayout mlayout;
    private DatabaseHelper db;
    private MaterialCalendarView calendarView;

    public OnSmileyClickListener(RelativeLayout layout, DatabaseHelper database, MaterialCalendarView calendar) {
        mlayout = layout;
        db = database;
        calendarView = calendar;
    }

    @Override
    public void onClick(View view) {
        String color = view.getResources().getResourceEntryName(mlayout.getId());
        switch (color){
            case ("smileyRouge"):
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 0);
                break;
            case ("smileyOrange"):
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 1);
                break;
            case ("smileyJaune"):
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 2);
                break;
            case ("smileyVertClaire"):
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 3);
                break;
            case ("smileyVert"):
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), 4);
                break;
            default:
                db.setHealthToDate(calendarView.getSelectedDate().getDate().toString(), -1);
        }
    }
}
