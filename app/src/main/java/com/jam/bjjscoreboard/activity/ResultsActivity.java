package com.jam.bjjscoreboard.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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


    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Scoreboard.Practitioner winner = (Scoreboard.Practitioner) getIntent().getSerializableExtra(INTENT_WINNER);
        final Scoreboard.WinType winType = (Scoreboard.WinType) getIntent().getSerializableExtra(INTENT_WIN_TYPE);

        final List<TimeLineItem> timeline_left =  getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_LEFT);
        final List<TimeLineItem> timeline_right =  getIntent().getParcelableArrayListExtra(INTENT_TIMELINE_RIGHT);

        final String leftPlayerName = PreferenceUtil.getLeftPlayerName(sharedPreferences, this);
        final String rightPlayerName = PreferenceUtil.getRightPlayerName(sharedPreferences, this);

        String resultMessage = "";

        if (winType == Scoreboard.WinType.POINTS) {
            if (winner == Scoreboard.Practitioner.LEFT) {
                resultMessage = getString(R.string.winByPoints, leftPlayerName, rightPlayerName);
            } else if (winner == Scoreboard.Practitioner.RIGHT) {
                resultMessage = getString(R.string.winByPoints, rightPlayerName, leftPlayerName);
            } else {//Tie
                resultMessage = getString(R.string.tieMsg);
            }
        } else if (winner != null) {
            if (winType == Scoreboard.WinType.TAP_OUT) {
                if (winner == Scoreboard.Practitioner.LEFT) {
                    resultMessage = getString(R.string.winByTap, leftPlayerName, rightPlayerName);
                } else {//RIGHT
                    resultMessage = getString(R.string.winByTap, rightPlayerName, leftPlayerName);
                }
            } else {//Scoreboard.WinType.DQ
                if (winner == Scoreboard.Practitioner.LEFT) {
                    resultMessage = getString(R.string.winByDQ, leftPlayerName, rightPlayerName);
                } else {//RIGHT
                    resultMessage = getString(R.string.winByDQ, rightPlayerName, leftPlayerName);
                }
            }
        }

        final TextView winResultMsg = (TextView) findViewById(R.id.winResultMsg);
        winResultMsg.setText(Html.fromHtml(resultMessage));

    }
}
