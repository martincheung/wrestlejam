package com.jam.bjjscoreboard.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jam.bjjscoreboard.R;
import com.jam.bjjscoreboard.eventListener.OnScoreboardChangeListener;
import com.jam.bjjscoreboard.model.Scoreboard;
import com.jam.bjjscoreboard.model.TimeLineItem;
import com.jam.bjjscoreboard.util.PreferenceUtil;
import com.jam.bjjscoreboard.util.TimeUtil;
import com.jam.bjjscoreboard.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

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

    //Groupings

    private ViewGroup rearMount_group_left;
    private ViewGroup mount_group_left;
    private ViewGroup back_group_left;
    private ViewGroup guardPass_group_left;
    private ViewGroup takeDown_group_left;
    private ViewGroup sweep_group_left;
    private ViewGroup kneeOnBelly_group_left;

    private ViewGroup leftPlayerDisplay;
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

    //Groupings

    private ViewGroup rearMount_group_right;
    private ViewGroup mount_group_right;
    private ViewGroup back_group_right;
    private ViewGroup guardPass_group_right;
    private ViewGroup takeDown_group_right;
    private ViewGroup sweep_group_right;
    private ViewGroup kneeOnBelly_group_right;

    private ViewGroup rightPlayerDisplay;

    // ------------------
    // Center buttons
    // ------------------

    private Button combate;
    private Button timer;
    private View menu_button;

    // -------------------
    // Other stuff
    // -------------------

    private SharedPreferences sharedPreferences;

    // timer
    public final static long DEFAULT_MATCH_LENGTH_IN_MILLI = 300000; //5 minutes

    // feedback
    private Vibrator vibrator;
    public final static int VIBRATION_LENGTH_IN_MILLI = 50;

    public final static int TEXT_HIGHLIGHT_DURATION_IN_MILLI = 500;

    // color
    private PlayerColorAdapter playerColorAdapter;

    // -------------------
    // Ads
    // -------------------
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Left player IDs
        rearMount_add_left = findViewById(R.id.rearMount_add_left);
        rearMount_add_left.setOnClickListener(this);
        rearMount_subtract_left = findViewById(R.id.rearMount_subtract_left);
        rearMount_subtract_left.setOnClickListener(this);
        rearMount_group_left = (ViewGroup) findViewById(R.id.rearMount_group_left);

        mount_add_left = findViewById(R.id.mount_add_left);
        mount_add_left.setOnClickListener(this);
        mount_subtract_left = findViewById(R.id.mount_subtract_left);
        mount_subtract_left.setOnClickListener(this);
        mount_group_left = (ViewGroup) findViewById(R.id.mount_group_left);

        back_add_left = findViewById(R.id.back_add_left);
        back_add_left.setOnClickListener(this);
        back_subtract_left = findViewById(R.id.back_subtract_left);
        back_subtract_left.setOnClickListener(this);
        back_group_left = (ViewGroup) findViewById(R.id.back_group_left);

        guardPass_add_left = findViewById(R.id.guardPass_add_left);
        guardPass_add_left.setOnClickListener(this);
        guardPass_subtract_left = findViewById(R.id.guardPass_subtract_left);
        guardPass_subtract_left.setOnClickListener(this);
        guardPass_group_left = (ViewGroup) findViewById(R.id.guardPass_group_left);

        takeDown_add_left = findViewById(R.id.takeDown_add_left);
        takeDown_add_left.setOnClickListener(this);
        takeDown_subtract_left = findViewById(R.id.takeDown_subtract_left);
        takeDown_subtract_left.setOnClickListener(this);
        takeDown_group_left = (ViewGroup) findViewById(R.id.takeDown_group_left);

        sweep_add_left = findViewById(R.id.sweep_add_left);
        sweep_add_left.setOnClickListener(this);
        sweep_subtract_left = findViewById(R.id.sweep_subtract_left);
        sweep_subtract_left.setOnClickListener(this);
        sweep_group_left = (ViewGroup) findViewById(R.id.sweep_group_left);

        kneeOnBelly_add_left = findViewById(R.id.kneeOnBelly_add_left);
        kneeOnBelly_add_left.setOnClickListener(this);
        kneeOnBelly_subtract_left = findViewById(R.id.kneeOnBelly_subtract_left);
        kneeOnBelly_subtract_left.setOnClickListener(this);
        kneeOnBelly_group_left = (ViewGroup) findViewById(R.id.kneeOnBelly_group_left);

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

        leftPlayerDisplay = (ViewGroup) findViewById(R.id.leftPlayerDisplay);

        // Right player IDs
        rearMount_add_right = findViewById(R.id.rearMount_add_right);
        rearMount_add_right.setOnClickListener(this);
        rearMount_subtract_right = findViewById(R.id.rearMount_subtract_right);
        rearMount_subtract_right.setOnClickListener(this);
        rearMount_group_right = (ViewGroup) findViewById(R.id.rearMount_group_right);

        mount_add_right = findViewById(R.id.mount_add_right);
        mount_add_right.setOnClickListener(this);
        mount_subtract_right = findViewById(R.id.mount_subtract_right);
        mount_subtract_right.setOnClickListener(this);
        mount_group_right = (ViewGroup) findViewById(R.id.mount_group_right);

        back_add_right = findViewById(R.id.back_add_right);
        back_add_right.setOnClickListener(this);
        back_subtract_right = findViewById(R.id.back_subtract_right);
        back_subtract_right.setOnClickListener(this);
        back_group_right = (ViewGroup) findViewById(R.id.back_group_right);

        guardPass_add_right = findViewById(R.id.guardPass_add_right);
        guardPass_add_right.setOnClickListener(this);
        guardPass_subtract_right = findViewById(R.id.guardPass_subtract_right);
        guardPass_subtract_right.setOnClickListener(this);
        guardPass_group_right = (ViewGroup) findViewById(R.id.guardPass_group_right);

        takeDown_add_right = findViewById(R.id.takeDown_add_right);
        takeDown_add_right.setOnClickListener(this);
        takeDown_subtract_right = findViewById(R.id.takeDown_subtract_right);
        takeDown_subtract_right.setOnClickListener(this);
        takeDown_group_right = (ViewGroup) findViewById(R.id.takeDown_group_right);

        sweep_add_right = findViewById(R.id.sweep_add_right);
        sweep_add_right.setOnClickListener(this);
        sweep_subtract_right = findViewById(R.id.sweep_subtract_right);
        sweep_subtract_right.setOnClickListener(this);
        sweep_group_right = (ViewGroup) findViewById(R.id.sweep_group_right);

        kneeOnBelly_add_right = findViewById(R.id.kneeOnBelly_add_right);
        kneeOnBelly_add_right.setOnClickListener(this);
        kneeOnBelly_subtract_right = findViewById(R.id.kneeOnBelly_subtract_right);
        kneeOnBelly_subtract_right.setOnClickListener(this);
        kneeOnBelly_group_right = (ViewGroup) findViewById(R.id.kneeOnBelly_group_right);

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

        rightPlayerDisplay = (ViewGroup) findViewById(R.id.rightPlayerDisplay);

        //Buttons

        combate = (Button) findViewById(R.id.combate);
        combate.setOnClickListener(this);
        timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(this);
        menu_button = findViewById(R.id.menu_button);
        menu_button.setOnClickListener(this);


        // Score board

        scoreboard = new Scoreboard();
        scoreboard.setOnScoreboardChangeListener(this);

        //Color

        final int[] colors_arr = getResources().getIntArray(R.array.playerColors);
        List<Integer> colorList = new ArrayList<Integer>();
        for (int index = 0; index < colors_arr.length; index++) {
            colorList.add(colors_arr[index]);
        }
        playerColorAdapter = new PlayerColorAdapter(this, R.layout.layout_jam_spinner_item, colorList);

        resetState();

        // Intersistal Ad setup
        mInterstitialAd = new InterstitialAd(this);

        //Replace this with real ID from Ad Mob
        //This is the Test Ad - use it for testing: ca-app-pub-3940256099942544/1033173712
        // This is the real one, use it for prod: ca-app-pub-3985981761358091/5836770966
        mInterstitialAd.setAdUnitId("ca-app-pub-3985981761358091/5836770966");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        //Make sure you add test devices as more are being used.
        //1. Run the app as normal
        //2. Go to logcat and put adRequest as the filter string
        //3. Look for this msg "To get test ads on this device, call adRequest.addTestDevice("D9XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")"
        //4. Get the hash id and add the test device with that id.

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("20F25CE6BD544C548D75E79DFE95B078")  //Andrew's Galaxy Prime
                .addTestDevice("F4C8C07D7FD2DA367E1CCC6EE0B99E7F")  //Jonathan's Nexus 7 2013
                .addTestDevice("D9E60E8355A420B28265C56D517A4470")  //Martin's Nexus 7
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    private void resetState() {
        if (timer != null) {
            timer.setText(TimeUtil.milliToTimeFormat(PreferenceUtil.getMatchLength(sharedPreferences)));
        }
        if (combate != null) {
            combate.setText(getString(R.string.combate));
        }
        if (menu_button != null) {
            menu_button.setAlpha(1.0f);
        }
        if (leftPlayerDisplay != null) {
            leftPlayerDisplay.setBackgroundColor(PreferenceUtil.getPreferredLeftPlayerColor(sharedPreferences, this));
        }
        if (rightPlayerDisplay != null) {
            rightPlayerDisplay.setBackgroundColor(PreferenceUtil.getPreferredRightPlayerColor(sharedPreferences, this));
        }
        if (leftPlayerName != null) {
            leftPlayerName.setText(PreferenceUtil.getLeftPlayerName(sharedPreferences, this));
        }
        if (rightPlayerName != null) {
            rightPlayerName.setText(PreferenceUtil.getRightPlayerName(sharedPreferences, this));
        }
    }

    @Override
    public void onCountDownTick(long millisUntilFinished) {

        timer.setText(TimeUtil.milliToTimeFormat(millisUntilFinished));
    }

    @Override
    public void onCountDownFinish(final Scoreboard.Practitioner winner, final Scoreboard.WinType winType, final long finalTimeInMilli) {

        int currentAdCounter = PreferenceUtil.getAdCounter(sharedPreferences);
        currentAdCounter++;
        if (currentAdCounter >= PreferenceUtil.AD_COUNTER_MAX) {
            currentAdCounter = 0;
        }
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(PreferenceUtil.AD_COUNTER_PREFERENCE_KEY, currentAdCounter);
        editor.commit();

        if (currentAdCounter == 0) {
            //This is where the ad comes up. The ad only comes up if it is loaded
            //If it is not loaded, we can do an else to go through with whatever other action
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        resetState();
        final List<TimeLineItem> timeLineList_left = scoreboard.getTimeline(Scoreboard.Practitioner.LEFT, winType, finalTimeInMilli);
        final List<TimeLineItem> timeLineList_right = scoreboard.getTimeline(Scoreboard.Practitioner.RIGHT, winType, finalTimeInMilli);
        gotoResults(winner, winType, timeLineList_left, timeLineList_right);

    }

    private void gotoResults(Scoreboard.Practitioner winner, Scoreboard.WinType winType, final List<TimeLineItem> timeLineList_left, final List<TimeLineItem> timeLineList_right) {

        if (winner == null && winType == null) {
            return;
        }
        final Intent intent = new Intent(this, ResultsActivity.class);

        intent.putParcelableArrayListExtra(ResultsActivity.INTENT_TIMELINE_LEFT, (ArrayList<TimeLineItem>) timeLineList_left);
        intent.putParcelableArrayListExtra(ResultsActivity.INTENT_TIMELINE_RIGHT, (ArrayList<TimeLineItem>) timeLineList_right);
        intent.putExtra(ResultsActivity.INTENT_WINNER, winner);
        intent.putExtra(ResultsActivity.INTENT_WIN_TYPE, winType);
        startActivity(intent);


    }

    @Override
    public void onCountDownPaused() {

    }

    @Override
    public void onCountDownResume() {
        onCountDownStart();

    }

    @Override
    public void onCountDownStart() {

        combate.setText(getString(R.string.end));
        menu_button.setAlpha(0.5f);

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
    public void onMoveActionStatusUpdate(final Scoreboard.Practitioner practitioner, final Scoreboard.MoveType moveType, boolean success) {
        if (success) {
            if (PreferenceUtil.isVibrationPreferred(sharedPreferences))
                vibrator.vibrate(VIBRATION_LENGTH_IN_MILLI);

            final int textHighlight_color = getResources().getColor(R.color.textHighlight);
            final int destination_color;
            switch (moveType) {
                case REAR_MOUNT:
                case MOUNT:
                case BACK:
                    destination_color = getResources().getColor(R.color.score4);
                    break;
                case GUARD_PASS:
                    destination_color = getResources().getColor(R.color.score3);
                    break;
                default:
                    destination_color = getResources().getColor(R.color.score2);
                    break;
            }
            final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), textHighlight_color, destination_color);
            colorAnimation.setDuration(TEXT_HIGHLIGHT_DURATION_IN_MILLI);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(final ValueAnimator animator) {
                    switch (practitioner) {
                        case LEFT:
                            switch (moveType) {
                                case REAR_MOUNT:
                                    rearMount_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case MOUNT:
                                    mount_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case BACK:
                                    back_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case GUARD_PASS:
                                    guardPass_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case TAKE_DOWN:
                                    takeDown_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case SWEEP:
                                    sweep_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case KNEE_ON_BELLY:
                                    kneeOnBelly_group_left.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                            }
                            break;
                        case RIGHT:
                            switch (moveType) {
                                case REAR_MOUNT:
                                    rearMount_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case MOUNT:
                                    mount_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case BACK:
                                    back_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case GUARD_PASS:
                                    guardPass_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case TAKE_DOWN:
                                    takeDown_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case SWEEP:
                                    sweep_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                                case KNEE_ON_BELLY:
                                    kneeOnBelly_group_right.setBackgroundColor((Integer) animator.getAnimatedValue());
                                    break;
                            }
                            break;
                    }
                }

            });
            colorAnimation.start();
        }
    }

    @Override
    public void onClick(View v) {

        //Center buttons
        if (v.getId() == R.id.timer) {
            if (!scoreboard.hasCountDownStarted())
                scoreboard.startTimer(PreferenceUtil.getMatchLength(sharedPreferences));
            else if (scoreboard.isTimerPaused())
                scoreboard.resumeTimer();
            else
                scoreboard.pauseTimer();
        } else if (v.getId() == R.id.combate) {
            if (!scoreboard.hasCountDownStarted())
                scoreboard.startTimer(PreferenceUtil.getMatchLength(sharedPreferences));
            else
                openEndingOptions();
        } else if (v.getId() == R.id.menu_button) {
            openMenu();
        } else if (v.getId() == R.id.leftPlayerName) {
            openPlayerNameEditor(true);
        } else if (v.getId() == R.id.rightPlayerName) {
            openPlayerNameEditor(false);
        } else if (!scoreboard.isTimerPaused()) {
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
    // --------------------
    // ending results
    // --------------------

    private void openEndingOptions() {


        scoreboard.pauseTimer();

        final String leftPlayerName = PreferenceUtil.getLeftPlayerName(sharedPreferences, this);
        final String rightPlayerName = PreferenceUtil.getRightPlayerName(sharedPreferences, this);

        final RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);

        final RadioButton leftTappedRight_rb = new RadioButton(this);
        leftTappedRight_rb.setText(getString(R.string.winByTap, leftPlayerName, rightPlayerName));
        rg.addView(leftTappedRight_rb);

        final RadioButton rightTappedLeft_rb = new RadioButton(this);
        rightTappedLeft_rb.setText(getString(R.string.winByTap, rightPlayerName, leftPlayerName));
        rg.addView(rightTappedLeft_rb);

        final RadioButton leftWinByDQ_rb = new RadioButton(this);
        leftWinByDQ_rb.setText(getString(R.string.winByDQ, leftPlayerName, rightPlayerName));
        rg.addView(leftWinByDQ_rb);

        final RadioButton rightWinByDQ_rb = new RadioButton(this);
        rightWinByDQ_rb.setText(getString(R.string.winByDQ, rightPlayerName, leftPlayerName));
        rg.addView(rightWinByDQ_rb);

        final RadioButton falseStart_rb = new RadioButton(this);
        falseStart_rb.setText(getString(R.string.falseStart));
        rg.addView(falseStart_rb);

        falseStart_rb.setChecked(true);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.matchEnd));
        alertBuilder.setView(rg);
        alertBuilder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {

                int radioButtonID = rg.getCheckedRadioButtonId();
                View radioButton = rg.findViewById(radioButtonID);
                int idx = rg.indexOfChild(radioButton);

                switch (idx) {
                    case 0:
                        scoreboard.stopTimer(Scoreboard.Practitioner.LEFT, Scoreboard.WinType.TAP_OUT);
                        break;
                    case 1:
                        scoreboard.stopTimer(Scoreboard.Practitioner.RIGHT, Scoreboard.WinType.TAP_OUT);
                        break;
                    case 2:
                        scoreboard.stopTimer(Scoreboard.Practitioner.LEFT, Scoreboard.WinType.DQ);
                        break;
                    case 3:
                        scoreboard.stopTimer(Scoreboard.Practitioner.RIGHT, Scoreboard.WinType.DQ);
                        break;
                    case 4:
                    default:
                        scoreboard.reset();
                        break;
                }

            }
        });
        alertBuilder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                scoreboard.resumeTimer();
            }
        });
        alertBuilder.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        scoreboard.resumeTimer();
                    }
                }
        );
        alertBuilder.create().show();
    }

    // --------------------
    // Player name editor
    // --------------------

    private void openPlayerNameEditor(final boolean isLeft) {
        if (scoreboard.hasCountDownStarted()) {
            return;
        }

        final String playerName = isLeft ? PreferenceUtil.getLeftPlayerName(sharedPreferences, this) : PreferenceUtil.getRightPlayerName(sharedPreferences, this);

        final EditText playerName_et = new EditText(this);
        playerName_et.setText(playerName);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.editing, playerName));
        alertBuilder.setView(playerName_et);
        alertBuilder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {


                final SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putString(isLeft ? PreferenceUtil.LEFT_PLAYER_NAME_PREFERENCE_KEY : PreferenceUtil.RIGHT_PLAYER_NAME_PREFERENCE_KEY, playerName_et.getText().toString());

                editor.commit();

                resetState();

            }
        });
        alertBuilder.setNegativeButton(getString(android.R.string.cancel), null);
        alertBuilder.create().show();
    }

    // --------------------
    // Menu
    // --------------------


    private void openMenu() {
        if (scoreboard.hasCountDownStarted()) {
            return;
        }
        final View layout_menu_options = LayoutInflater.from(this).inflate(R.layout.layout_menu_options, (ScrollView) findViewById(R.id.layout_menu_options_rootLayout));

        final EditText playerLeft_et = (EditText) layout_menu_options.findViewById(R.id.playerLeft_et);
        playerLeft_et.setText(PreferenceUtil.getLeftPlayerName(sharedPreferences, this));
        final EditText playerRight_et = (EditText) layout_menu_options.findViewById(R.id.playerRight_et);
        playerRight_et.setText(PreferenceUtil.getRightPlayerName(sharedPreferences, this));

        final int minutes = (int) TimeUtil.toMinutes(PreferenceUtil.getMatchLength(sharedPreferences));
        final int seconds = (int) TimeUtil.toSeconds(PreferenceUtil.getMatchLength(sharedPreferences));

        final NumberPicker minutes_numberPicker = (NumberPicker) layout_menu_options.findViewById(R.id.minutes_numberPicker);
        minutes_numberPicker.setValue(minutes);
        final NumberPicker seconds_numberPicker = (NumberPicker) layout_menu_options.findViewById(R.id.seconds_numberPicker);
        seconds_numberPicker.setValue(seconds);

        //loop for 2 players
        for (int i = 0; i < 2; i++) {

            final boolean isLeft = i == 0;

            final Spinner playerColor_spinner = (Spinner) (isLeft ? layout_menu_options.findViewById(R.id.playerColorLeft_spinner) : layout_menu_options.findViewById(R.id.playerColorRight_spinner));
            playerColor_spinner.setAdapter(playerColorAdapter);
            playerColor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            final int leftPlayerColor = PreferenceUtil.getPreferredLeftPlayerColor(sharedPreferences, this);
            final int rightPlayerColor = PreferenceUtil.getPreferredRightPlayerColor(sharedPreferences, this);

            int spinnerPosition = playerColorAdapter.getPosition(isLeft ? leftPlayerColor : rightPlayerColor);
            playerColor_spinner.setSelection(spinnerPosition, false);

        }

        final CheckBox vibrate_cb = (CheckBox) layout_menu_options.findViewById(R.id.vibrate_cb);
        vibrate_cb.setChecked(PreferenceUtil.isVibrationPreferred(sharedPreferences));

        final CheckBox buzzer_cb = (CheckBox) layout_menu_options.findViewById(R.id.buzzer_cb);
        buzzer_cb.setChecked(PreferenceUtil.isBuzzerPreferred(sharedPreferences));

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.menuTitle));
        alertBuilder.setView(layout_menu_options);
        alertBuilder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                final int resultingMins = minutes_numberPicker.getValue();
                final int resultingSeconds = seconds_numberPicker.getValue();

                final long matchLength_milli = (resultingMins * 60 + resultingSeconds) * 1000;

                final SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putLong(PreferenceUtil.MATCH_LENGTH_PREFERENCE_KEY, matchLength_milli);

                final Spinner playerColorLeft_spinner = (Spinner) layout_menu_options.findViewById(R.id.playerColorLeft_spinner);
                final int resultingColor_left = (Integer) playerColorLeft_spinner.getSelectedItem();
                editor.putInt(PreferenceUtil.LEFT_PLAYER_COLOR_PREFERENCE_KEY, resultingColor_left);

                final Spinner playerColorRight_spinner = (Spinner) layout_menu_options.findViewById(R.id.playerColorRight_spinner);
                final int resultingColor_right = (Integer) playerColorRight_spinner.getSelectedItem();
                editor.putInt(PreferenceUtil.RIGHT_PLAYER_COLOR_PREFERENCE_KEY, resultingColor_right);

                editor.putBoolean(PreferenceUtil.VIBRATION_PREFERENCE_KEY, vibrate_cb.isChecked());

                editor.putBoolean(PreferenceUtil.BUZZER_PREFERENCE_KEY, buzzer_cb.isChecked());

                editor.putString(PreferenceUtil.LEFT_PLAYER_NAME_PREFERENCE_KEY, playerLeft_et.getText().toString());
                editor.putString(PreferenceUtil.RIGHT_PLAYER_NAME_PREFERENCE_KEY, playerRight_et.getText().toString());

                editor.commit();

                resetState();

            }
        });
        alertBuilder.setNegativeButton(getString(android.R.string.cancel), null);
        alertBuilder.create().show();
    }

    public class PlayerColorAdapter extends ArrayAdapter<Integer> {
        public PlayerColorAdapter(Context context, int resource, List<Integer> items) {
            super(context, resource, items);
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                final LayoutInflater vi = LayoutInflater.from(getContext());
                convertView = vi.inflate(R.layout.layout_jam_spinner_item, null);
            }


            final int color = getItem(position);

            TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
            text1.setBackgroundColor(color);

            return convertView;
        }
    }

}
