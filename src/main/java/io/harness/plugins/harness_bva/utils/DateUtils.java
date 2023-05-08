package io.harness.plugins.harness_bva.utils;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

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

    public static Instant toStartOfDay(Instant dt) {
        LocalDateTime d = LocalDateTime.ofInstant(dt, UTC_ZONE_ID);
        return d.with(LocalTime.MIN).atZone(UTC_ZONE_ID).toInstant();
    }

    public static Instant toEndOfDay(Instant dt) {
        LocalDateTime d = LocalDateTime.ofInstant(dt, UTC_ZONE_ID);
        return d.with(LocalTime.MAX).atZone(UTC_ZONE_ID).toInstant();
    }
}
