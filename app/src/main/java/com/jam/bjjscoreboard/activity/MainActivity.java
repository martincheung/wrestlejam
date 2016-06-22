package com.jam.bjjscoreboard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jam.bjjscoreboard.R;
import com.jam.bjjscoreboard.eventListener.OnScoreboardChangeListener;
import com.jam.bjjscoreboard.model.Scoreboard;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements OnScoreboardChangeListener, View.OnClickListener {


    private Scoreboard scoreboard;

    // ------------------
    // Left player IDs
    // ------------------

    //Controls

    private View rearMount_add_left;
    private View rearMount_subtract_left;

    private View mount_add_left;
    private View mount_subtract_left;

    private View back_add_left;
    private View back_subtract_left;

    private View guardPass_add_left;
    private View guardPass_subtract_left;

    private View takeDown_add_left;
    private View takeDown_subtract_left;

    private View sweep_add_left;
    private View sweep_subtract_left;

    private View kneeOnBelly_add_left;
    private View kneeOnBelly_subtract_left;

    private View adv_add_left;
    private View adv_subtract_left;

    private View pen_add_left;
    private View pen_subtract_left;

    //Score

    private TextView leftPlayerOverallScore;
    private TextView adv_left;
    private TextView pen_left;

    //Name

    private TextView leftPlayerName;

    // ------------------
    // Right player IDs
    // ------------------

    //Controls

    private View rearMount_add_right;
    private View rearMount_subtract_right;

    private View mount_add_right;
    private View mount_subtract_right;

    private View back_add_right;
    private View back_subtract_right;

    private View guardPass_add_right;
    private View guardPass_subtract_right;

    private View takeDown_add_right;
    private View takeDown_subtract_right;

    private View sweep_add_right;
    private View sweep_subtract_right;

    private View kneeOnBelly_add_right;
    private View kneeOnBelly_subtract_right;

    private View adv_add_right;
    private View adv_subtract_right;

    private View pen_add_right;
    private View pen_subtract_right;

    //Score

    private TextView rightPlayerOverallScore;
    private TextView adv_right;
    private TextView pen_right;

    //Name

    private TextView rightPlayerName;

    // ------------------
    // Center buttons
    // ------------------

    private Button tap;
    private Button timer;

    // -------------------
    // Other stuff
    // -------------------

    private SharedPreferences sharedPreferences;

    // timer values
    public final static long DEFAULT_MATCH_LENGTH_IN_MILLI = 300000; //5 minutes
    public final static String MATCH_LENGTH_PREFERENCE_KEY = "matchLengthPreference";

    // name values
    public final static String LEFT_PLAYER_NAME_PREFERENCE_KEY = "leftPlayerNamePreference";
    public final static String RIGHT_PLAYER_NAME_PREFERENCE_KEY = "rightPlayerNamePreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Left player IDs
        rearMount_add_left = findViewById(R.id.rearMount_add_left);
        rearMount_add_left.setOnClickListener(this);
        rearMount_subtract_left = findViewById(R.id.rearMount_subtract_left);
        rearMount_subtract_left.setOnClickListener(this);

        mount_add_left = findViewById(R.id.mount_add_left);
        mount_add_left.setOnClickListener(this);
        mount_subtract_left = findViewById(R.id.mount_subtract_left);
        mount_subtract_left.setOnClickListener(this);

        back_add_left = findViewById(R.id.back_add_left);
        back_add_left.setOnClickListener(this);
        back_subtract_left = findViewById(R.id.back_subtract_left);
        back_subtract_left.setOnClickListener(this);

        guardPass_add_left = findViewById(R.id.guardPass_add_left);
        guardPass_add_left.setOnClickListener(this);
        guardPass_subtract_left = findViewById(R.id.guardPass_subtract_left);
        guardPass_subtract_left.setOnClickListener(this);

        takeDown_add_left = findViewById(R.id.takeDown_add_left);
        takeDown_add_left.setOnClickListener(this);
        takeDown_subtract_left = findViewById(R.id.takeDown_subtract_left);
        takeDown_subtract_left.setOnClickListener(this);

        sweep_add_left = findViewById(R.id.sweep_add_left);
        sweep_add_left.setOnClickListener(this);
        sweep_subtract_left = findViewById(R.id.sweep_subtract_left);
        sweep_subtract_left.setOnClickListener(this);

        kneeOnBelly_add_left = findViewById(R.id.kneeOnBelly_add_left);
        kneeOnBelly_add_left.setOnClickListener(this);
        kneeOnBelly_subtract_left = findViewById(R.id.kneeOnBelly_subtract_left);
        kneeOnBelly_subtract_left.setOnClickListener(this);

        adv_add_left = findViewById(R.id.adv_add_left);
        adv_add_left.setOnClickListener(this);
        adv_subtract_left = findViewById(R.id.adv_subtract_left);
        adv_subtract_left.setOnClickListener(this);

        pen_add_left = findViewById(R.id.pen_add_left);
        pen_add_left.setOnClickListener(this);
        pen_subtract_left = findViewById(R.id.pen_subtract_left);
        pen_subtract_left.setOnClickListener(this);

        leftPlayerOverallScore = (TextView) findViewById(R.id.leftPlayerOverallScore);
        adv_left = (TextView) findViewById(R.id.adv_left);
        pen_left = (TextView) findViewById(R.id.pen_left);

        leftPlayerName = (TextView) findViewById(R.id.leftPlayerName);
        leftPlayerName.setOnClickListener(this);
        leftPlayerName.setText(sharedPreferences.getString(LEFT_PLAYER_NAME_PREFERENCE_KEY, getString(R.string.defaultLeftPlayerName)));

        // Right player IDs
        rearMount_add_right = findViewById(R.id.rearMount_add_right);
        rearMount_add_right.setOnClickListener(this);
        rearMount_subtract_right = findViewById(R.id.rearMount_subtract_right);
        rearMount_subtract_right.setOnClickListener(this);

        mount_add_right = findViewById(R.id.mount_add_right);
        mount_add_right.setOnClickListener(this);
        mount_subtract_right = findViewById(R.id.mount_subtract_right);
        mount_subtract_right.setOnClickListener(this);

        back_add_right = findViewById(R.id.back_add_right);
        back_add_right.setOnClickListener(this);
        back_subtract_right = findViewById(R.id.back_subtract_right);
        back_subtract_right.setOnClickListener(this);

        guardPass_add_right = findViewById(R.id.guardPass_add_right);
        guardPass_add_right.setOnClickListener(this);
        guardPass_subtract_right = findViewById(R.id.guardPass_subtract_right);
        guardPass_subtract_right.setOnClickListener(this);

        takeDown_add_right = findViewById(R.id.takeDown_add_right);
        takeDown_add_right.setOnClickListener(this);
        takeDown_subtract_right = findViewById(R.id.takeDown_subtract_right);
        takeDown_subtract_right.setOnClickListener(this);

        sweep_add_right = findViewById(R.id.sweep_add_right);
        sweep_add_right.setOnClickListener(this);
        sweep_subtract_right = findViewById(R.id.sweep_subtract_right);
        sweep_subtract_right.setOnClickListener(this);

        kneeOnBelly_add_right = findViewById(R.id.kneeOnBelly_add_right);
        kneeOnBelly_add_right.setOnClickListener(this);
        kneeOnBelly_subtract_right = findViewById(R.id.kneeOnBelly_subtract_right);
        kneeOnBelly_subtract_right.setOnClickListener(this);

        adv_add_right = findViewById(R.id.adv_add_right);
        adv_add_right.setOnClickListener(this);
        adv_subtract_right = findViewById(R.id.adv_subtract_right);
        adv_subtract_right.setOnClickListener(this);

        pen_add_right = findViewById(R.id.pen_add_right);
        pen_add_right.setOnClickListener(this);
        pen_subtract_right = findViewById(R.id.pen_subtract_right);
        pen_subtract_right.setOnClickListener(this);

        rightPlayerOverallScore = (TextView) findViewById(R.id.rightPlayerOverallScore);
        adv_right = (TextView) findViewById(R.id.adv_right);
        pen_right = (TextView) findViewById(R.id.pen_right);

        rightPlayerName = (TextView) findViewById(R.id.rightPlayerName);
        rightPlayerName.setOnClickListener(this);
        rightPlayerName.setText(sharedPreferences.getString(RIGHT_PLAYER_NAME_PREFERENCE_KEY, getString(R.string.defaultRightPlayerName)));

        //Buttons

        tap = (Button) findViewById(R.id.tap);
        tap.setOnClickListener(this);
        timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(this);


        // Score board

        scoreboard = new Scoreboard();
        scoreboard.setOnScoreboardChangeListener(this);

        resetState();
    }


    private void resetState() {
        if (timer != null && tap != null) {
            timer.setText(milliToTimeFormat(sharedPreferences.getLong(MATCH_LENGTH_PREFERENCE_KEY, DEFAULT_MATCH_LENGTH_IN_MILLI)));
            tap.setText(getString(R.string.combate));
        }
    }

    private static String milliToTimeFormat(final long milli) {
        return String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milli),
                TimeUnit.MILLISECONDS.toSeconds(milli) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milli)));
    }

    @Override
    public void onCountDownTick(long millisUntilFinished) {

        timer.setText(milliToTimeFormat(millisUntilFinished));
    }

    @Override
    public void onCountDownFinish(final Scoreboard.Practitioner winner, final Scoreboard.WinType winType) {

        resetState();
        if (winType == Scoreboard.WinType.POINTS) {
            if (winner == Scoreboard.Practitioner.LEFT) {

            } else if (winner == Scoreboard.Practitioner.RIGHT) {

            } else {//Tie

            }
        } else if (winner != null) {
            if (winType == Scoreboard.WinType.TAP_OUT) {
                if (winner == Scoreboard.Practitioner.LEFT) {

                } else {//RIGHT

                }
            }
            else if (winType == Scoreboard.WinType.DQ) {
                if (winner == Scoreboard.Practitioner.LEFT) {

                } else {//RIGHT

                }
            }
        }
    }

    @Override
    public void onCountDownPaused() {
        tap.setText(getString(R.string.reset));
    }

    @Override
    public void onCountDownResume() {
        tap.setText(getString(R.string.tap));

    }

    @Override
    public void onCountDownStart() {

        tap.setText(getString(R.string.tap));
    }

    @Override
    public void onScoreUpdate(Scoreboard.Practitioner practitioner, Scoreboard.ScoreType
            scoreType, int score, boolean cancel) {
        if (practitioner == Scoreboard.Practitioner.LEFT) {
            if (scoreType == Scoreboard.ScoreType.OVERALL) {
                leftPlayerOverallScore.setText(String.valueOf(score));
            } else if (scoreType == Scoreboard.ScoreType.ADVANTAGE) {
                adv_left.setText(String.valueOf(score));
            } else { //Penalty
                pen_left.setText(String.valueOf(score));
            }
        } else {// RIGHT
            if (scoreType == Scoreboard.ScoreType.OVERALL) {
                rightPlayerOverallScore.setText(String.valueOf(score));
            } else if (scoreType == Scoreboard.ScoreType.ADVANTAGE) {
                adv_right.setText(String.valueOf(score));
            } else { //Penalty
                pen_right.setText(String.valueOf(score));
            }
        }

    }

    @Override
    public void onMoveActionStatusUpdate(Scoreboard.Practitioner
                                                 practitioner, Scoreboard.MoveType moveType, boolean success) {

    }

    @Override
    public void onClick(View v) {

        //Center buttons
        if (v.getId() == R.id.timer) {
            if (!scoreboard.hasCountDownStarted())
                scoreboard.startTimer(sharedPreferences.getLong(MATCH_LENGTH_PREFERENCE_KEY, DEFAULT_MATCH_LENGTH_IN_MILLI));
            else if (scoreboard.isTimerPaused())
                scoreboard.resumeTimer();
            else
                scoreboard.pauseTimer();
        }
        if (v.getId() == R.id.tap) {
            if (!scoreboard.hasCountDownStarted())
                scoreboard.startTimer(sharedPreferences.getLong(MATCH_LENGTH_PREFERENCE_KEY, DEFAULT_MATCH_LENGTH_IN_MILLI));
            else if (scoreboard.isTimerPaused())
                scoreboard.reset();
            else
                scoreboard.stopTimer(Scoreboard.Practitioner.LEFT, Scoreboard.WinType.TAP_OUT);
        }

        if (!scoreboard.isTimerPaused()) {
            //Move actions
            switch (v.getId()) {
                case R.id.rearMount_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.REAR_MOUNT, false);
                    break;
                case R.id.rearMount_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.REAR_MOUNT, true);
                    break;
                case R.id.mount_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.MOUNT, false);
                    break;
                case R.id.mount_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.MOUNT, true);
                    break;
                case R.id.back_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.BACK, false);
                    break;
                case R.id.back_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.BACK, true);
                    break;
                case R.id.guardPass_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, false);
                    break;
                case R.id.guardPass_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, true);
                    break;
                case R.id.takeDown_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.TAKE_DOWN, false);
                    break;
                case R.id.takeDown_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.TAKE_DOWN, true);
                    break;
                case R.id.sweep_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.SWEEP, false);
                    break;
                case R.id.sweep_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.SWEEP, true);
                    break;
                case R.id.kneeOnBelly_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.KNEE_ON_BELLY, false);
                    break;
                case R.id.kneeOnBelly_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.KNEE_ON_BELLY, true);
                    break;
                case R.id.adv_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.ADVANTAGE, false);
                    break;
                case R.id.adv_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.ADVANTAGE, true);
                    break;
                case R.id.pen_add_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.PENALTY, false);
                    break;
                case R.id.pen_subtract_left:
                    scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.PENALTY, true);
                    break;

                case R.id.rearMount_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.REAR_MOUNT, false);
                    break;
                case R.id.rearMount_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.REAR_MOUNT, true);
                    break;
                case R.id.mount_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.MOUNT, false);
                    break;
                case R.id.mount_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.MOUNT, true);
                    break;
                case R.id.back_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.BACK, false);
                    break;
                case R.id.back_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.BACK, true);
                    break;
                case R.id.guardPass_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GUARD_PASS, false);
                    break;
                case R.id.guardPass_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GUARD_PASS, true);
                    break;
                case R.id.takeDown_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.TAKE_DOWN, false);
                    break;
                case R.id.takeDown_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.TAKE_DOWN, true);
                    break;
                case R.id.sweep_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.SWEEP, false);
                    break;
                case R.id.sweep_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.SWEEP, true);
                    break;
                case R.id.kneeOnBelly_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.KNEE_ON_BELLY, false);
                    break;
                case R.id.kneeOnBelly_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.KNEE_ON_BELLY, true);
                    break;
                case R.id.adv_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.ADVANTAGE, false);
                    break;
                case R.id.adv_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.ADVANTAGE, true);
                    break;
                case R.id.pen_add_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.PENALTY, false);
                    break;
                case R.id.pen_subtract_right:
                    scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.PENALTY, true);
                    break;
            }
        }
    }
}
