package com.jam.bjjscoreboard.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jam.bjjscoreboard.R;
import com.jam.bjjscoreboard.model.Scoreboard;
import com.jam.bjjscoreboard.model.TimeLineItem;
import com.jam.bjjscoreboard.util.PreferenceUtil;

import org.w3c.dom.Text;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Scoreboard.Practitioner winner = (Scoreboard.Practitioner) getIntent().getSerializableExtra(INTENT_WINNER);
        final Scoreboard.WinType winType = (Scoreboard.WinType) getIntent().getSerializableExtra(INTENT_WIN_TYPE);

        final List<TimeLineItem> timeline_left = getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_LEFT);
        final List<TimeLineItem> timeline_right = getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_RIGHT);

        final int overallScore_left = getIntent().getIntExtra(INTENT_OVERALL_SCORE_LEFT, 0);
        final int overallScore_right = getIntent().getIntExtra(INTENT_OVERALL_SCORE_RIGHT, 0);

        final TextView leftPlayerOverallScore = (TextView) findViewById(R.id.leftPlayerOverallScore_result);
        leftPlayerOverallScore.setText(String.valueOf(overallScore_left));
        final TextView rightPlayerOverallScore = (TextView) findViewById(R.id.rightPlayerOverallScore_result);
        rightPlayerOverallScore.setText(String.valueOf(overallScore_right));

        final int advScore_left = getIntent().getIntExtra(INTENT_ADV_SCORE_LEFT, 0);
        final int advScore_right = getIntent().getIntExtra(INTENT_ADV_SCORE_RIGHT, 0);

        final TextView adv_left = (TextView) findViewById(R.id.adv_left_result);
        adv_left.setText(String.valueOf(advScore_left));
        final TextView adv_right = (TextView) findViewById(R.id.adv_right_result);
        adv_right.setText(String.valueOf(advScore_right));

        final int penScore_left = getIntent().getIntExtra(INTENT_PEN_SCORE_LEFT, 0);
        final int penScore_right = getIntent().getIntExtra(INTENT_PEN_SCORE_RIGHT, 0);

        final TextView pen_left = (TextView) findViewById(R.id.pen_left_result);
        pen_left.setText(String.valueOf(penScore_left));
        final TextView pen_right = (TextView) findViewById(R.id.pen_right_result);
        pen_right.setText(String.valueOf(penScore_right));

        final String leftPlayerName = PreferenceUtil.getLeftPlayerName(sharedPreferences, this);
        final String rightPlayerName = PreferenceUtil.getRightPlayerName(sharedPreferences, this);

        final TextView leftPlayerDisplayName = (TextView) findViewById(R.id.leftPlayerName_result);
        leftPlayerDisplayName.setText(leftPlayerName);
        final TextView rightPlayerDisplayName = (TextView) findViewById(R.id.rightPlayerName_result);
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

        final TextView leftResultStatus = (TextView) findViewById(R.id.leftResultStatus);
        leftResultStatus.setText(leftResultMessage);

        final TextView rightResultStatus = (TextView) findViewById(R.id.rightResultStatus);
        rightResultStatus.setText(rightResultMessage);

    }
}
