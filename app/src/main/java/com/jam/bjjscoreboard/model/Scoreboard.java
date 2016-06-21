package com.jam.bjjscoreboard.model;

import com.jam.bjjscoreboard.eventListener.OnScoreboardChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 6/16/2016.
 */
public class Scoreboard {


    public enum ScoreType {
        OVERALL, ADVANTAGE, PENALTY
    }

    public enum MoveType {
        GENERIC_4,GENERIC_3,GENERIC_2,REAR_MOUNT, MOUNT, BACK, GUARD_PASS, SWEEP, KNEE_ON_BELLY, TAKE_DOWN, ADVANTAGE, PENALTY
    }

    final private static Map<MoveType, Integer> MOVE_TYPE_TO_SCORE_MAP = new HashMap<MoveType, Integer>() {{
        put(MoveType.GENERIC_4, 4);
        put(MoveType.GENERIC_3, 3);
        put(MoveType.GENERIC_2, 2);
        put(MoveType.REAR_MOUNT, 4);
        put(MoveType.MOUNT, 4);
        put(MoveType.BACK, 4);
        put(MoveType.GUARD_PASS, 3);
        put(MoveType.SWEEP, 2);
        put(MoveType.KNEE_ON_BELLY, 2);
        put(MoveType.TAKE_DOWN, 2);
        put(MoveType.ADVANTAGE, 1);
        put(MoveType.PENALTY, 1);
    }};


    final private static Map<MoveType, ScoreType> MOVE_TYPE_TO_SCORE_TYPE_MAP = new HashMap<MoveType, ScoreType>() {{
        put(MoveType.GENERIC_4, ScoreType.OVERALL);
        put(MoveType.GENERIC_3, ScoreType.OVERALL);
        put(MoveType.GENERIC_2, ScoreType.OVERALL);
        put(MoveType.REAR_MOUNT, ScoreType.OVERALL);
        put(MoveType.MOUNT, ScoreType.OVERALL);
        put(MoveType.BACK, ScoreType.OVERALL);
        put(MoveType.GUARD_PASS, ScoreType.OVERALL);
        put(MoveType.SWEEP, ScoreType.OVERALL);
        put(MoveType.KNEE_ON_BELLY, ScoreType.OVERALL);
        put(MoveType.TAKE_DOWN, ScoreType.OVERALL);
        put(MoveType.ADVANTAGE, ScoreType.ADVANTAGE);
        put(MoveType.PENALTY, ScoreType.PENALTY);
    }};

    public enum Practitioner {
        LEFT, RIGHT
    }

    final private static Map<Practitioner, Map<ScoreType, Integer>> PRACTITIONER_SCORE_MAP = new HashMap<>();
    final private static Map<Practitioner, Map<MoveType, List<Long>>> PRACTITIONER_TIMELINE_MAP = new HashMap<>();


    // -------------------------------
    // OnScoreboardChangeListener
    // -------------------------------

    private OnScoreboardChangeListener onScoreboardChangeListener;


    public void setOnScoreboardChangeListener(OnScoreboardChangeListener onScoreboardChangeListener) {
        this.onScoreboardChangeListener = onScoreboardChangeListener;
    }

    public void dispatchOnTick(final long millisUntilFinished) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownTick(millisUntilFinished);
        }
    }

    public void dispatchOnFinish() {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownFinish();
        }
    }

    public void dispatchOnScoreUpdate(final Practitioner practitioner, final ScoreType scoreType, final int score, final boolean cancel) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onScoreUpdate(practitioner, scoreType, score, cancel);
        }
    }

    // -------------------------------
    // Count down timer
    // -------------------------------

    private CountDownTimerWithPause countDownTimer;

    public void startTimer(final long totalMilliSeconds) {

        //Clear everything and dispatch scores of 0
        PRACTITIONER_SCORE_MAP.clear();
        PRACTITIONER_TIMELINE_MAP.clear();

        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.OVERALL, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.OVERALL, 0, true);
        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.ADVANTAGE, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.ADVANTAGE, 0, true);
        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.PENALTY, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.PENALTY, 0, true);

        countDownTimer = new CountDownTimerWithPause(totalMilliSeconds, 1000, true) {
            @Override
            public void onTick(final long millisUntilFinished) {
                dispatchOnTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                dispatchOnFinish();
            }
        };
        countDownTimer.create();
    }

    public void restartTimer(final long totalSeconds) {
        stopTimer();
        startTimer(totalSeconds);
    }

    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.pause();
        }
    }

    public void resumeTimer() {
        if (countDownTimer != null) {
            countDownTimer.resume();
        }
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = null;
    }

    // -------------------------------
    // Score
    // -------------------------------

    public int getScore(final Practitioner practitioner, final ScoreType scoreType) {

        if (PRACTITIONER_SCORE_MAP.get(practitioner) == null) {
            PRACTITIONER_SCORE_MAP.put(practitioner, new HashMap<ScoreType, Integer>());
        }

        final Map<ScoreType, Integer> score_map = PRACTITIONER_SCORE_MAP.get(practitioner);

        return score_map.get(scoreType) == null ? 0 : score_map.get(scoreType);

    }

    // -------------------------------
    // Move type
    // -------------------------------

    public void moveType_action(final Practitioner practitioner, final MoveType moveType, final boolean cancel) {
        if (PRACTITIONER_TIMELINE_MAP.get(practitioner) == null) {
            PRACTITIONER_TIMELINE_MAP.put(practitioner, new HashMap<MoveType, List<Long>>());
        }

        final Map<MoveType, List<Long>> timeline_map = PRACTITIONER_TIMELINE_MAP.get(practitioner);

        if (timeline_map.get(moveType) == null) {
            timeline_map.put(moveType, new ArrayList<Long>());
        }
        final List<Long> timeList = timeline_map.get(moveType);

        if (!cancel)
            timeList.add(countDownTimer != null ? countDownTimer.timePassed() : -1);
        else if (timeList.size() > 0) {
            timeList.remove(timeList.size() - 1);
        } else {
            //trying to cancel a move that doesn't exist in the timeline.  dont allow the score to update
            return;
        }

        //adding the score
        final Map<ScoreType, Integer> score_map = PRACTITIONER_SCORE_MAP.get(practitioner);
        final ScoreType scoreType = MOVE_TYPE_TO_SCORE_TYPE_MAP.get(moveType);
        final int score = MOVE_TYPE_TO_SCORE_MAP.get(moveType);

        int currentScore = score_map.get(scoreType);

        final int factor = cancel ? -1 : 1;
        currentScore += score * factor;
        currentScore = Math.max(0, currentScore);

        score_map.put(scoreType, currentScore);

        dispatchOnScoreUpdate(practitioner, scoreType, currentScore, cancel);
    }

    public int getCount(final Practitioner practitioner, final MoveType moveType) {
        if (PRACTITIONER_TIMELINE_MAP.get(practitioner) == null) {
            PRACTITIONER_TIMELINE_MAP.put(practitioner, new HashMap<MoveType, List<Long>>());
        }

        final Map<MoveType, List<Long>> timeline_map = PRACTITIONER_TIMELINE_MAP.get(practitioner);

        return timeline_map.get(moveType) == null ? 0 : timeline_map.get(moveType).size();
    }

    // -------------------------------
    // Time line
    // -------------------------------

    public List<TimeLineItem> getTimeline(final Practitioner practitioner) {
        if (PRACTITIONER_TIMELINE_MAP.get(practitioner) == null) {
            PRACTITIONER_TIMELINE_MAP.put(practitioner, new HashMap<MoveType, List<Long>>());
        }

        final Map<MoveType, List<Long>> timeline_map = PRACTITIONER_TIMELINE_MAP.get(practitioner);

        final List<TimeLineItem> timeLineList = new ArrayList<>();

        for (final MoveType moveType : timeline_map.keySet()) {
            final List<Long> timeList = timeline_map.get(moveType);
            if (timeList != null) {
                for (final long timeInMilli : timeList) {
                    timeLineList.add(new TimeLineItem(moveType, timeInMilli));
                }
            }
        }

        Collections.sort(timeLineList);

        return timeLineList;
    }

    public class TimeLineItem implements Comparable<TimeLineItem> {

        private MoveType moveType;
        private long timeInMilli;
        public TimeLineItem(final MoveType moveType, final long timeInMilli) {
            this.moveType = moveType;
            this.timeInMilli = timeInMilli;
        }

        public MoveType getMoveType() {
            return moveType;
        }

        public long getTimeInMilli() {
            return timeInMilli;
        }

        @Override
        public int compareTo(TimeLineItem another) {
            return (int) (getTimeInMilli() - another.getTimeInMilli());
        }
    }
}