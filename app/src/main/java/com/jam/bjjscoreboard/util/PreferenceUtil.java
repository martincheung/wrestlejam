package com.jam.bjjscoreboard.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jam.bjjscoreboard.R;
import com.jam.bjjscoreboard.activity.MainActivity;

/**
 * Created by Owner on 6/27/2016.
 */
public class PreferenceUtil {
    public final static String MATCH_LENGTH_PREFERENCE_KEY = "matchLengthPreference";
    public final static String LEFT_PLAYER_NAME_PREFERENCE_KEY = "leftPlayerNamePreference";
    public final static String RIGHT_PLAYER_NAME_PREFERENCE_KEY = "rightPlayerNamePreference";
    public final static String VIBRATION_PREFERENCE_KEY = "vibrationPreference";
    public final static String BUZZER_PREFERENCE_KEY = "buzzerPreference";
    public final static String LEFT_PLAYER_COLOR_PREFERENCE_KEY = "leftPlayerColorPreference";
    public final static String RIGHT_PLAYER_COLOR_PREFERENCE_KEY = "rightPlayerColorPreference";
    public final static String AD_COUNTER_PREFERENCE_KEY = "adCounterPreference";
    public final static int AD_COUNTER_MAX = 10;

    public static long getMatchLength(final SharedPreferences sharedPreferences) {
        return sharedPreferences.getLong(MATCH_LENGTH_PREFERENCE_KEY, MainActivity.DEFAULT_MATCH_LENGTH_IN_MILLI);
    }

    public static String getLeftPlayerName(final SharedPreferences sharedPreferences, final Context context) {
        return sharedPreferences.getString(LEFT_PLAYER_NAME_PREFERENCE_KEY, context.getString(R.string.defaultLeftPlayerName));
    }

    public static String  getRightPlayerName(final SharedPreferences sharedPreferences, final Context context) {
        return sharedPreferences.getString(RIGHT_PLAYER_NAME_PREFERENCE_KEY, context.getString(R.string.defaultRightPlayerName));
    }

    public static boolean isVibrationPreferred(final SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(VIBRATION_PREFERENCE_KEY, true);
    }

    public static boolean isBuzzerPreferred(final SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(BUZZER_PREFERENCE_KEY, true);
    }

    public static int getPreferredLeftPlayerColor(final SharedPreferences sharedPreferences, final Context context) {
        return sharedPreferences.getInt(LEFT_PLAYER_COLOR_PREFERENCE_KEY, context.getResources().getColor(android.R.color.holo_blue_dark));
    }

    public static int getPreferredRightPlayerColor(final SharedPreferences sharedPreferences, final Context context) {
        return sharedPreferences.getInt(RIGHT_PLAYER_COLOR_PREFERENCE_KEY, context.getResources().getColor(android.R.color.holo_red_dark));
    }

    public static int getAdCounter(final SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(AD_COUNTER_PREFERENCE_KEY, 0);
    }
}
