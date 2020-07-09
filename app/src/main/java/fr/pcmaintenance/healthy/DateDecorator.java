package fr.pcmaintenance.healthy;

import android.content.Context;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

public class DateDecorator implements DayViewDecorator {
    private int mColor;
    private HashSet<CalendarDay> mCalendarDayCollection;
    private Context mcontext;

    public DateDecorator(int color, HashSet<CalendarDay> calendarDayCollection, Context context) {
        mColor = color;
        mCalendarDayCollection = calendarDayCollection;
        mcontext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mCalendarDayCollection.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(mColor));
//        view.addSpan(new BackgroundColorSpan(Color.BLUE));
        view.setBackgroundDrawable(ContextCompat.getDrawable(mcontext,R.drawable.home_gradient_rouge));
    }
}
