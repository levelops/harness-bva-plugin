package io.harness.plugins.harness_bva.utils;

import java.lang.invoke.MethodHandles;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static final String getDateFormattedDirName(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String formattedDirName = String.format("data-%04d-%02d-%02d",year,month,day);
        LOGGER.log(Level.FINEST, "formattedDirName = {0}", formattedDirName);
        return formattedDirName;
    }

    public static final String getDateFormattedDirName(){
        return getDateFormattedDirName(new Date());
    }
}
