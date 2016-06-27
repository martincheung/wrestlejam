package com.jam.bjjscoreboard.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 6/27/2016.
 */
public class TimeLineItem implements Comparable<TimeLineItem>, Parcelable {

    private Scoreboard.TimeLineType moveType;
    private long timeInMilli;

    public TimeLineItem(final Scoreboard.TimeLineType moveType, final long timeInMilli) {
        this.moveType = moveType;
        this.timeInMilli = timeInMilli;
    }

    public Scoreboard.TimeLineType getMoveType() {
        return moveType;
    }

    public long getTimeInMilli() {
        return timeInMilli;
    }

    @Override
    public int compareTo(TimeLineItem another) {
        return (int) (getTimeInMilli() - another.getTimeInMilli());
    }

    protected TimeLineItem(Parcel in) {
        moveType = Scoreboard.MoveType.valueOf(in.readString());
        timeInMilli = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moveType.name());
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