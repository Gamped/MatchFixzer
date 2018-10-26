package MatchFixzer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ELO_CalculatorTest {
    ELO_Calculator ec = new ELO_Calculator();

    @Test
    void testPrediction0(){
        Team t1 = new Team(new Player(), new Player(1500));
        Team t2 = new Team(new Player(), new Player());
        assertEquals(ec.getLikelyResult(t1, t2), MatchResult.WON);
    }

    @Test
    void testPrediction1(){
        Team t1 = new Team(new Player(), new Player());
        Team t2 = new Team(new Player(), new Player());
        assertEquals(ec.getLikelyResult(t1, t2), MatchResult.TIE);
    }

    @Test
    void testPrediction2(){
        Team t1 = new Team(new Player(), new Player());
        Team t2 = new Team(new Player(), new Player(1500));
        assertEquals(ec.getLikelyResult(t1, t2), MatchResult.LOST);
    }
}
