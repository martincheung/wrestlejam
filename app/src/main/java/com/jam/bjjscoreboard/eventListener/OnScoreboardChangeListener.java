package com.jam.bjjscoreboard.eventListener;

import com.jam.bjjscoreboard.model.Scoreboard;

/**
 * Created by Andrew on 6/16/2016.
 */
public interface OnScoreboardChangeListener {

    public void onCountDownTick(final long millisUntilFinished);
    public void onCountDownFinish();
    public void onScoreUpdate(final Scoreboard.Practitioner practitioner, final Scoreboard.ScoreType scoreType, final int score, final boolean cancel);
    public void onMoveActionStatusUpdate(final Scoreboard.Practitioner practitioner, final Scoreboard.MoveType moveType, final boolean success);
}
