package com.jam.bjjscoreboard.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 6/27/2016.
 */
public class TimeLineItem implements Comparable<TimeLineItem>, Parcelable {

    private Scoreboard.TimeLineType timeLineType;
    private long timeInMilli;

    public TimeLineItem(final Scoreboard.TimeLineType timeLineType, final long timeInMilli) {
        this.timeLineType = timeLineType;
        this.timeInMilli = timeInMilli;
    }

    public Scoreboard.TimeLineType getTimeLineType() {
        return timeLineType;
    }

    public long getTimeInMilli() {
        return timeInMilli;
    }

    private static Scoreboard.TimeLineType getProperTimeLineTypeFromString(final String timeLineTypeAsString) {

        final Scoreboard.MoveType[] moveTypes = Scoreboard.MoveType.values();
        for (final Scoreboard.MoveType moveType : moveTypes) {
            if (moveType.name().equals(timeLineTypeAsString)) {
                return moveType;
            }
        }

        final Scoreboard.WinType[] winTypes = Scoreboard.WinType.values();
        for (final Scoreboard.WinType winType : winTypes) {
            if (winType.name().equals(timeLineTypeAsString)) {
                return winType;
            }
        }

        return null;

    }

    @Override
    public int compareTo(TimeLineItem another) {
        return (int) (getTimeInMilli() - another.getTimeInMilli());
    }

    protected TimeLineItem(Parcel in) {
        timeLineType = getProperTimeLineTypeFromString(in.readString());
        timeInMilli = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timeLineType == null ? "" : timeLineType.name());
        dest.writeLong(timeInMilli);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TimeLineItem> CREATOR = new Parcelable.Creator<TimeLineItem>() {
        @Override
        public TimeLineItem createFromParcel(Parcel in) {
            return new TimeLineItem(in);
        }

        @Override
        public TimeLineItem[] newArray(int size) {
            return new TimeLineItem[size];
        }
    };
}