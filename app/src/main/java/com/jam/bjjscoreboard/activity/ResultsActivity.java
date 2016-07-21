package com.jam.bjjscoreboard.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jam.bjjscoreboard.R;
import com.jam.bjjscoreboard.model.Scoreboard;
import com.jam.bjjscoreboard.model.TimeLineItem;
import com.jam.bjjscoreboard.util.PreferenceUtil;
import com.jam.bjjscoreboard.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    public static final String INTENT_TIMELINE_LEFT = "timelineLeft";
    public static final String INTENT_TIMELINE_RIGHT = "timelineRight";
    public static final String INTENT_WINNER = "winner";
    public static final String INTENT_WIN_TYPE = "winType";
    public static final String INTENT_OVERALL_SCORE_LEFT = "overallScoreLeft";
    public static final String INTENT_OVERALL_SCORE_RIGHT = "overallScoreRight";
    public static final String INTENT_ADV_SCORE_LEFT = "advScoreLeft";
    public static final String INTENT_ADV_SCORE_RIGHT = "advScoreRight";
    public static final String INTENT_PEN_SCORE_LEFT = "penScoreLeft";
    public static final String INTENT_PEN_SCORE_RIGHT = "penScoreRight";


    private SharedPreferences sharedPreferences;

    final private static Map<Scoreboard.TimeLineType, Integer> TIME_LINE_TYPE_TO_STRING_ID_MAP = new HashMap<Scoreboard.TimeLineType, Integer>() {{
        put(Scoreboard.MoveType.GENERIC_4, R.string.scored4);
        put(Scoreboard.MoveType.GENERIC_3, R.string.scored3);
        put(Scoreboard.MoveType.GENERIC_2, R.string.scored2);
        put(Scoreboard.MoveType.REAR_MOUNT, R.string.rearMount);
        put(Scoreboard.MoveType.MOUNT, R.string.mount);
        put(Scoreboard.MoveType.BACK, R.string.back);
        put(Scoreboard.MoveType.GUARD_PASS, R.string.guardPass);
        put(Scoreboard.MoveType.SWEEP, R.string.sweep);
        put(Scoreboard.MoveType.KNEE_ON_BELLY, R.string.kneeOnBelly);
        put(Scoreboard.MoveType.TAKE_DOWN, R.string.takeDown);
        put(Scoreboard.MoveType.ADVANTAGE, R.string.advantage);
        put(Scoreboard.MoveType.PENALTY, R.string.penalty);
        put(Scoreboard.WinType.DQ, R.string.matchEnd);
        put(Scoreboard.WinType.POINTS, R.string.matchEnd);
        put(Scoreboard.WinType.TAP_OUT, R.string.matchEnd);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Scoreboard.Practitioner winner = (Scoreboard.Practitioner) getIntent().getSerializableExtra(INTENT_WINNER);
        final Scoreboard.WinType winType = (Scoreboard.WinType) getIntent().getSerializableExtra(INTENT_WIN_TYPE);

        final int overallScore_left = getIntent().getIntExtra(INTENT_OVERALL_SCORE_LEFT, 0);
        final int overallScore_right = getIntent().getIntExtra(INTENT_OVERALL_SCORE_RIGHT, 0);

        final TextView leftPlayerOverallScore = (TextView) findViewById(R.id.leftPlayerOverallScore);
        leftPlayerOverallScore.setText(String.valueOf(overallScore_left));
        final TextView rightPlayerOverallScore = (TextView) findViewById(R.id.rightPlayerOverallScore);
        rightPlayerOverallScore.setText(String.valueOf(overallScore_right));

        final int advScore_left = getIntent().getIntExtra(INTENT_ADV_SCORE_LEFT, 0);
        final int advScore_right = getIntent().getIntExtra(INTENT_ADV_SCORE_RIGHT, 0);

        final TextView adv_left = (TextView) findViewById(R.id.adv_left);
        adv_left.setText(String.valueOf(advScore_left));
        final TextView adv_right = (TextView) findViewById(R.id.adv_right);
        adv_right.setText(String.valueOf(advScore_right));

        final int penScore_left = getIntent().getIntExtra(INTENT_PEN_SCORE_LEFT, 0);
        final int penScore_right = getIntent().getIntExtra(INTENT_PEN_SCORE_RIGHT, 0);

        final TextView pen_left = (TextView) findViewById(R.id.pen_left);
        pen_left.setText(String.valueOf(penScore_left));
        final TextView pen_right = (TextView) findViewById(R.id.pen_right);
        pen_right.setText(String.valueOf(penScore_right));

        final String leftPlayerName = PreferenceUtil.getLeftPlayerName(sharedPreferences, this);
        final String rightPlayerName = PreferenceUtil.getRightPlayerName(sharedPreferences, this);

        final TextView leftPlayerDisplayName = (TextView) findViewById(R.id.leftPlayerName);
        leftPlayerDisplayName.setText(leftPlayerName);
        final TextView rightPlayerDisplayName = (TextView) findViewById(R.id.rightPlayerName);
        rightPlayerDisplayName.setText(rightPlayerName);

        String leftResultMessage = "";
        String rightResultMessage = "";

        if (winType == Scoreboard.WinType.POINTS) {
            if (winner == Scoreboard.Practitioner.LEFT) {
                leftResultMessage = getString(R.string.winnerByPointsMsg);
            } else if (winner == Scoreboard.Practitioner.RIGHT) {
                rightResultMessage = getString(R.string.winnerByPointsMsg);
            } else {//Tie
                leftResultMessage = getString(R.string.tieMsg);
                rightResultMessage = getString(R.string.tieMsg);
            }
        } else if (winner != null) {
            if (winType == Scoreboard.WinType.TAP_OUT) {
                if (winner == Scoreboard.Practitioner.LEFT) {
                    leftResultMessage = getString(R.string.winnerBySubmissionMsg);
                } else {//RIGHT
                    rightResultMessage = getString(R.string.winnerBySubmissionMsg);
                }
            } else {//Scoreboard.WinType.DQ
                if (winner == Scoreboard.Practitioner.LEFT) {
                    leftResultMessage = getString(R.string.winnerByDQMsg);
                } else {//RIGHT
                    rightResultMessage = getString(R.string.winnerByDQMsg);
                }
            }
        }
        final Typeface major_shift = Typeface.createFromAsset(getAssets(), "fonts/major_shift.ttf");

        final TextView leftResultStatus = (TextView) findViewById(R.id.leftResultStatus);
        leftResultStatus.setText(leftResultMessage);
        leftResultStatus.setTypeface(major_shift);

        final TextView rightResultStatus = (TextView) findViewById(R.id.rightResultStatus);
        rightResultStatus.setText(rightResultMessage);
        rightResultStatus.setTypeface(major_shift);

        final ViewGroup leftPlayerDisplay = (ViewGroup) findViewById(R.id.leftPlayerDisplay);
        leftPlayerDisplay.setBackgroundColor(PreferenceUtil.getLeftPlayerColor(sharedPreferences, this));

        final ViewGroup rightPlayerDisplay = (ViewGroup) findViewById(R.id.rightPlayerDisplay);
        rightPlayerDisplay.setBackgroundColor(PreferenceUtil.getRightPlayerColor(sharedPreferences, this));

        final ViewGroup leftTimeLineContainer = (ViewGroup) findViewById(R.id.leftTimeLineContainer);
        leftTimeLineContainer.setBackgroundColor(PreferenceUtil.getLeftPlayerColor(sharedPreferences, this));

        final ViewGroup rightTimeLineContainer = (ViewGroup) findViewById(R.id.rightTimeLineContainer);
        rightTimeLineContainer.setBackgroundColor(PreferenceUtil.getRightPlayerColor(sharedPreferences, this));

        final List<TimeLineItem> timeline_left = getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_LEFT);
        final List<TimeLineItem> timeline_right = getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_RIGHT);

        buildTabledTimeline(timeline_left, (ViewGroup) findViewById(R.id.leftTimeColumn), (ViewGroup) findViewById(R.id.leftMoveColumn));
        buildTabledTimeline(timeline_right, (ViewGroup) findViewById(R.id.rightTimeColumn), (ViewGroup) findViewById(R.id.rightMoveColumn));

        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        final ScrollView scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);
        scrollContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        final int screenHeight = displaymetrics.heightPixels;

                        final int height = scrollContainer.getHeight();
                        final float y = scrollContainer.getY();

                        if (y + height < screenHeight) {
                            leftTimeLineContainer.getLayoutParams().height = (int) (screenHeight - y);
                            rightTimeLineContainer.getLayoutParams().height = (int) (screenHeight - y);
                        }
                    }
                });
    }

    private void buildTabledTimeline(final List<TimeLineItem> timeLineItemList, final ViewGroup timeColumn, final ViewGroup moveColumn) {
        for (final TimeLineItem timeLineItem : timeLineItemList) {


            final long timeInMilli = timeLineItem.getTimeInMilli();
            final Scoreboard.TimeLineType timeLineType = timeLineItem.getTimeLineType();

            if (timeLineType == null)
                continue;

            final TextView timeInMilli_tv = new TextView(this);
            timeInMilli_tv.setText(TimeUtil.milliToTimeFormat(timeInMilli));
            timeInMilli_tv.setTextColor(Color.WHITE);
            timeInMilli_tv.setGravity(Gravity.CENTER_HORIZONTAL);
            timeInMilli_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.result_timeline_text_size));

            final TextView move_tv = new TextView(this);
            move_tv.setText(getString(TIME_LINE_TYPE_TO_STRING_ID_MAP.get(timeLineType)));
            move_tv.setTextColor(Color.WHITE);
            move_tv.setGravity(Gravity.CENTER_HORIZONTAL);
            move_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.result_timeline_text_size));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            timeColumn.addView(timeInMilli_tv, layoutParams);
            moveColumn.addView(move_tv, layoutParams);
        }
    }

}
