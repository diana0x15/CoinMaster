package com.dianapislaru.coinmaster.objects;

/**
 * Created by Diana on 05/10/2016.
 */

public class Day {

    private int mCalendarDay;
    private String mWeekDay;

    public Day(int calendarDay, String weekDay) {
        mCalendarDay = calendarDay;
        mWeekDay = weekDay;
    }

    public int getCalendarDay() {
        return mCalendarDay;
    }

    public void setCalendarDay(int calendarDay) {
        mCalendarDay = calendarDay;
    }

    public String getWeekDay() {
        return mWeekDay;
    }

    public void setWeekDay(String weekDay) {
        mWeekDay = weekDay;
    }
}
