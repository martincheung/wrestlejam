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

    public interface TimeLineType {
        public String name();
    }

    public enum MoveType implements TimeLineType {
        GENERIC_4, GENERIC_3, GENERIC_2, REAR_MOUNT, MOUNT, BACK, GUARD_PASS, SWEEP, KNEE_ON_BELLY, TAKE_DOWN, ADVANTAGE, PENALTY
    }

    public enum WinType implements TimeLineType {
        TAP_OUT, POINTS, DQ
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

    private Map<Practitioner, Map<ScoreType, Integer>> practitioner_score_map = new HashMap<>();
    private Map<Practitioner, Map<MoveType, List<Long>>> practitioner_timeline_map = new HashMap<>();

    public Scoreboard() {
        setUpPractitionerMaps();

    }

    private void setUpPractitionerMaps() {
        practitioner_score_map.clear();
        practitioner_score_map.put(Practitioner.LEFT, new HashMap<ScoreType, Integer>());
        practitioner_score_map.put(Practitioner.RIGHT, new HashMap<ScoreType, Integer>());

        practitioner_timeline_map.clear();
        practitioner_timeline_map.put(Practitioner.LEFT, new HashMap<MoveType, List<Long>>());
        practitioner_timeline_map.put(Practitioner.RIGHT, new HashMap<MoveType, List<Long>>());

    }

    // -------------------------------
    // OnScoreboardChangeListener
    // -------------------------------

    private OnScoreboardChangeListener onScoreboardChangeListener;


    public void setOnScoreboardChangeListener(OnScoreboardChangeListener onScoreboardChangeListener) {
        this.onScoreboardChangeListener = onScoreboardChangeListener;
    }

    private void dispatchOnCountDownTick(final long millisUntilFinished) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownTick(millisUntilFinished);
        }
    }

    private void dispatchOnCountDownFinish(final Practitioner winner, final WinType winType, final long timePassed) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownFinish(winner, winType, timePassed);
        }
    }

    private void dispatchOnCountDownPaused() {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownPaused();
        }
    }

    private void dispatchOnCountDownResume() {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownResume();
        }
    }

    private void dispatchOnCountDownStart() {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onCountDownStart();
        }
    }

    private void dispatchOnScoreUpdate(final Practitioner practitioner, final ScoreType scoreType, final int score, final boolean cancel) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onScoreUpdate(practitioner, scoreType, score, cancel);
        }
    }

    private void dispatchOnMoveActionStatusUpdate(final Practitioner practitioner, final MoveType moveType, final boolean success) {
        if (onScoreboardChangeListener != null) {
            onScoreboardChangeListener.onMoveActionStatusUpdate(practitioner, moveType, success);
        }
    }


    public void reset() {
        stopTimer(null, null);

        setUpPractitionerMaps();

        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.OVERALL, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.OVERALL, 0, true);
        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.ADVANTAGE, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.ADVANTAGE, 0, true);
        dispatchOnScoreUpdate(Practitioner.LEFT, ScoreType.PENALTY, 0, true);
        dispatchOnScoreUpdate(Practitioner.RIGHT, ScoreType.PENALTY, 0, true);
    }

    // -------------------------------
    // Count down timer
    // -------------------------------

    private CountDownTimerWithPause countDownTimer;

    public void startTimer(final long totalMilliSeconds) {

        reset();
        countDownTimer = new CountDownTimerWithPause(totalMilliSeconds, 1, true) {
            @Override
            public void onTick(final long millisUntilFinished) {
                dispatchOnCountDownTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {


                stopTimer(getWinnerByPoints(), WinType.POINTS);


            }
        }.create();

        dispatchOnCountDownStart();

    }

    public boolean isTimerPaused() {

        if (countDownTimer != null) {
            return countDownTimer.isPaused();
        }
        return false;
    }

    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.pause();
            dispatchOnCountDownPaused();
        }
    }

    public boolean hasCountDownStarted() {

        if (countDownTimer != null) {
            return countDownTimer.hasBeenStarted();
        }
        return false;
    }

    public void resumeTimer() {
        if (countDownTimer != null) {
            countDownTimer.resume();
            dispatchOnCountDownResume();
        }
    }

    public void stopTimer(final Practitioner winner, final WinType winType) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            dispatchOnCountDownFinish(winner, winType,getCountDownTimePassed());
        }
        countDownTimer = null;
    }


    public long getCountDownTimePassed() {

        if (countDownTimer != null) {
            return countDownTimer.timePassed();
        }
        return -1;
    }

    // -------------------------------
    // Score
    // -------------------------------

    public int getScore(final Practitioner practitioner, final ScoreType scoreType) {

        final Map<ScoreType, Integer> score_map = practitioner_score_map.get(practitioner);
        return score_map.get(scoreType) == null ? 0 : score_map.get(scoreType);
    }

    public Practitioner getWinnerByPoints() {
        final int overall_score_left = getScore(Practitioner.LEFT, ScoreType.OVERALL);
        final int overall_score_right = getScore(Practitioner.RIGHT, ScoreType.OVERALL);

        final int adv_score_left = getScore(Practitioner.LEFT, ScoreType.ADVANTAGE);
        final int adv_score_right = getScore(Practitioner.RIGHT, ScoreType.ADVANTAGE);

        final int pen_score_left = getScore(Practitioner.LEFT, ScoreType.PENALTY);
        final int pen_score_right = getScore(Practitioner.RIGHT, ScoreType.PENALTY);

        if (overall_score_left > overall_score_right) {
            return Practitioner.LEFT;
        } else if (overall_score_left < overall_score_right) {
            return Practitioner.RIGHT;
        } else if (adv_score_left > adv_score_right) {
            return Practitioner.LEFT;
        } else if (adv_score_left < adv_score_right) {
            return Practitioner.RIGHT;
        } else if (pen_score_right > pen_score_left) {
            return Practitioner.LEFT;
        } else if (pen_score_right < pen_score_left) {
            return Practitioner.RIGHT;
        } else {
            return null;
        }
    }
    // -------------------------------
    // Move type
    // -------------------------------

    public void moveType_action(final Practitioner practitioner, final MoveType moveType, final boolean cancel) {

        moveType_action(practitioner, moveType, getCountDownTimePassed(), cancel);
    }

    //For unit testing
    public void moveType_action(final Practitioner practitioner, final MoveType moveType, final long timePassed, final boolean cancel) {

        final Map<MoveType, List<Long>> timeline_map = practitioner_timeline_map.get(practitioner);

        if (timeline_map.get(moveType) == null) {
            timeline_map.put(moveType, new ArrayList<Long>());
        }
        final List<Long> timeList = timeline_map.get(moveType);

        if (!cancel)
            timeList.add(timePassed);
        else if (timeList.size() > 0) {
            timeList.remove(timeList.size() - 1);
        } else {
            //trying to cancel a move that doesn't exist in the timeline.  dont allow the score to update
            dispatchOnMoveActionStatusUpdate(practitioner, moveType, false);
            return;
        }

        //adding the score
        final Map<ScoreType, Integer> score_map = practitioner_score_map.get(practitioner);
        final ScoreType scoreType = MOVE_TYPE_TO_SCORE_TYPE_MAP.get(moveType);
        final int score = MOVE_TYPE_TO_SCORE_MAP.get(moveType);

        int currentScore = score_map.get(scoreType) != null ? score_map.get(scoreType) : 0;

        final int factor = cancel ? -1 : 1;
        currentScore += score * factor;
        currentScore = Math.max(0, currentScore);

        score_map.put(scoreType, currentScore);

        dispatchOnScoreUpdate(practitioner, scoreType, currentScore, cancel);
        dispatchOnMoveActionStatusUpdate(practitioner, moveType, true);
    }

    public int getCount(final Practitioner practitioner, final MoveType moveType) {


        final Map<MoveType, List<Long>> timeline_map = practitioner_timeline_map.get(practitioner);

        return timeline_map.get(moveType) == null ? 0 : timeline_map.get(moveType).size();
    }

    // -------------------------------
    // Time line
    // -------------------------------

    public List<TimeLineItem> getTimeline(final Practitioner practitioner, final WinType winType, final long finalTimeInMilli) {


        final Map<MoveType, List<Long>> timeline_map = practitioner_timeline_map.get(practitioner);

        final List<TimeLineItem> timeLineList = new ArrayList<>();

        //always add start event
        timeLineList.add(new TimeLineItem(null, 0));

        for (final MoveType moveType : timeline_map.keySet()) {
            final List<Long> timeList = timeline_map.get(moveType);
            if (timeList != null) {
                for (final long timeInMilli : timeList) {
                    timeLineList.add(new TimeLineItem(moveType, timeInMilli));
                }
            }
        }

        //the last event
        timeLineList.add(new TimeLineItem(winType, finalTimeInMilli));

        Collections.sort(timeLineList);

        return timeLineList;
    }


}
