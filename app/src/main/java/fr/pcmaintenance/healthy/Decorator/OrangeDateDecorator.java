package fr.pcmaintenance.healthy.Decorator;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

import fr.pcmaintenance.healthy.R;

public class OrangeDateDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> mCalendarDayCollection;
    private Context mcontext;

    public OrangeDateDecorator(HashSet<CalendarDay> calendarDayCollection, Context context) {
        mCalendarDayCollection = calendarDayCollection;
        mcontext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mCalendarDayCollection.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(ContextCompat.getDrawable(mcontext, R.drawable.home_gradient_orange));
    }
}
