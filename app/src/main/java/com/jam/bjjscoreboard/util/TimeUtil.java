package com.jam.bjjscoreboard.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Owner on 6/27/2016.
 */
public class TimeUtil {
    public static String milliToTimeFormat(final long milli) {
        return String.format("%d:%02d", toMinutes(milli), toSeconds(milli));
    }

    public static long toMinutes(final long milli) {
        return TimeUnit.MILLISECONDS.toMinutes(milli);
    }

    public static long toSeconds(final long milli) {
        return TimeUnit.MILLISECONDS.toSeconds(milli) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milli));
    }
}
