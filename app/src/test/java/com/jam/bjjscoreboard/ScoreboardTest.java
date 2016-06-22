package com.jam.bjjscoreboard;

import com.jam.bjjscoreboard.model.Scoreboard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Owner on 6/21/2016.
 */
public class ScoreboardTest {
    private Scoreboard scoreboard;

    @Before
    public void setup() throws Exception {

        scoreboard = new Scoreboard();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScoreCalculation() throws Exception {
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GENERIC_2, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GENERIC_3, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.LEFT, Scoreboard.ScoreType.OVERALL), 5);

        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GENERIC_4, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.RIGHT, Scoreboard.ScoreType.OVERALL), 4);


        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GUARD_PASS, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.RIGHT, Scoreboard.ScoreType.OVERALL), 7);

        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.KNEE_ON_BELLY, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.KNEE_ON_BELLY, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.RIGHT, Scoreboard.ScoreType.OVERALL), 11);

        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.BACK, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.LEFT, Scoreboard.ScoreType.OVERALL), 9);

        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.ADVANTAGE, false);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.LEFT, Scoreboard.ScoreType.ADVANTAGE), 1);


        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.REAR_MOUNT, true);
        assertEquals(scoreboard.getScore(Scoreboard.Practitioner.RIGHT, Scoreboard.ScoreType.OVERALL), 11);
    }
    @Test
    public void testWinnerByPoints() throws Exception {
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GENERIC_2, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GENERIC_3, false);
        assertEquals(scoreboard.getWinnerByPoints(), Scoreboard.Practitioner.LEFT);

        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GENERIC_4, false);
        assertEquals(scoreboard.getWinnerByPoints(), Scoreboard.Practitioner.LEFT);


        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.GUARD_PASS, false);
        assertEquals(scoreboard.getWinnerByPoints(), Scoreboard.Practitioner.RIGHT);

        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.KNEE_ON_BELLY, false);
        assertEquals(scoreboard.getWinnerByPoints(), null);


        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.ADVANTAGE, false);
        assertEquals(scoreboard.getWinnerByPoints(), Scoreboard.Practitioner.LEFT);

        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.ADVANTAGE, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.PENALTY, false);
        assertEquals(scoreboard.getWinnerByPoints(), Scoreboard.Practitioner.LEFT);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.PENALTY, false);
        assertEquals(scoreboard.getWinnerByPoints(),null);

    }


    @Test
    public void testCount() throws Exception {
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.SWEEP, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, false);
        assertEquals(scoreboard.getCount(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.SWEEP), 1);
        assertEquals(scoreboard.getCount(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS), 2);


        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, true);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, true);
        assertEquals(scoreboard.getCount(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS), 0);

        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, true);
        assertEquals(scoreboard.getCount(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS), 0);


    }


    @Test
    public void testTimeline() throws Exception {
        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.TAKE_DOWN, 5000, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.SWEEP, 8000, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, 11500, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, 11600, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, 12000, true);
        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.BACK, 20000, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.RIGHT, Scoreboard.MoveType.MOUNT, 30000, false);
        scoreboard.moveType_action(Scoreboard.Practitioner.LEFT, Scoreboard.MoveType.GUARD_PASS, 40000, false);

        final List<Scoreboard.TimeLineItem> timeLine_left = scoreboard.getTimeline(Scoreboard.Practitioner.LEFT);
        assertEquals(timeLine_left.get(0).getMoveType(), Scoreboard.MoveType.SWEEP);
        assertEquals(timeLine_left.get(0).getTimeInMilli(), 8000);
        assertEquals(timeLine_left.get(1).getMoveType(), Scoreboard.MoveType.GUARD_PASS);
        assertEquals(timeLine_left.get(1).getTimeInMilli(), 11500);
        assertEquals(timeLine_left.get(2).getMoveType(), Scoreboard.MoveType.GUARD_PASS);
        assertEquals(timeLine_left.get(2).getTimeInMilli(), 40000);

        final List<Scoreboard.TimeLineItem> timeLine_right = scoreboard.getTimeline(Scoreboard.Practitioner.RIGHT);
        assertEquals(timeLine_right.get(0).getMoveType(), Scoreboard.MoveType.TAKE_DOWN);
        assertEquals(timeLine_right.get(0).getTimeInMilli(), 5000);
        assertEquals(timeLine_right.get(1).getMoveType(), Scoreboard.MoveType.BACK);
        assertEquals(timeLine_right.get(1).getTimeInMilli(), 20000);
        assertEquals(timeLine_right.get(2).getMoveType(), Scoreboard.MoveType.MOUNT);
        assertEquals(timeLine_right.get(2).getTimeInMilli(), 30000);
    }
}
