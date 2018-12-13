package hoanglong.thesis.graduation.juncomputer.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hoanglong.thesis.graduation.juncomputer.R;

public class FormatDate {
    public static String nextDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, day);
        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(c);
    }

    public static String nextHour(int hourNext) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hourNext);
        Date hour = calendar.getTime();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        return hourFormat.format(hour);
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(c);
    }

    public static String getCurrentDateWithHour() {
        Calendar cal = Calendar.getInstance();
        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return df.format(c);
    }
}
