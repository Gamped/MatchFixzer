package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchHistoryTest {

    MatchHistory mh = null;

    @BeforeEach
    void setup(){
        mh = new MatchHistory();
        mh.addMatchResult(MatchResult.WON);
        mh.addMatchResult(MatchResult.WON);
        mh.addMatchResult(MatchResult.TIE);
        mh.addMatchResult(MatchResult.WON);
        mh.addMatchResult(MatchResult.LOST);
        mh.addMatchResult(MatchResult.WON);
        mh.addMatchResult(MatchResult.LOST);
    }

    @Test
    void testResult0(){assertEquals(mh.getMatchResult(0), MatchResult.WON);}

    @Test
    void testResult1(){assertEquals(mh.getMatchResult(1), MatchResult.WON);}

    @Test
    void testResult2(){assertEquals(mh.getMatchResult(2), MatchResult.TIE);}

    @Test
    void testResult3(){assertEquals(mh.getMatchResult(3), MatchResult.WON);}

    @Test
    void testResult4(){assertEquals(mh.getMatchResult(4), MatchResult.LOST);}

    @Test
    void testResult5(){assertEquals(mh.getMatchResult(5), MatchResult.WON);}

    @Test
    void testResult6(){assertEquals(mh.getMatchResult(6), MatchResult.LOST);}

    @Test
    void testRemove(){
        mh.removeMatchResult(4);
        assertEquals(mh.getMatchResult(4), MatchResult.WON);
    }

    @Test
    void testAdd(){
        mh.addMatchResult(MatchResult.TIE);
        assertEquals(mh.getMatchResult(7), MatchResult.TIE);
    }

    @Test
    void testCustomCreation(){
        MatchHistory mh2 = new MatchHistory();
        mh2.addMatchResult(MatchResult.WON);
        mh2.addMatchResult(MatchResult.LOST);
        mh2.addMatchResult(MatchResult.TIE);
        mh2.addMatchResult(MatchResult.WON);

        mh = new MatchHistory(mh2.getMatchHistory());

        assertEquals(mh.getMatchResult(2), MatchResult.TIE);
    }
}
