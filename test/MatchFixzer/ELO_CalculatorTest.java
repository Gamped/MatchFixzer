package MatchFixzer;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ELO_CalculatorTest {
    ELO_Calculator ec = new ELO_Calculator();

    @Test
    void testPrediction0(){
        Team t1 = new Team(new Player(), new Player(2500));
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
        Team t2 = new Team(new Player(), new Player(2500));
        assertEquals(ec.getLikelyResult(t1, t2), MatchResult.LOST);
    }

    @Test
    void testTieResult(){
        Team t1 = new Team(new Player(), new Player(), new Player());
        Team t2 = new Team(new Player(), new Player());
        ArrayList<Team> tAL = new ArrayList<>();
        tAL.add(t1); tAL.add(t2);

        ec.affectELOAfterResult(tAL, MatchResult.TIE);
        assertEquals(t1.getPlayer(0).getMatchHistory().getMatchResult(0), MatchResult.TIE);
        assertEquals(t1.getPlayer(2).getMatchHistory().getMatchResult(0), MatchResult.TIE);
        assertEquals(t2.getPlayer(0).getMatchHistory().getMatchResult(0), MatchResult.TIE);
    }

    @Test
    void testWonResult(){
        Team t1 = new Team(new Player(2000), new Player(), new Player(2500));
        Team t2 = new Team(new Player(2000), new Player(2000));
        ArrayList<Team> tAL = new ArrayList<>();
        tAL.add(t1); tAL.add(t2);

        ec.affectELOAfterResult(tAL, MatchResult.WON);
        // System.out.print("won: " + t1.getPlayer(0).getELO_Score());
        // System.out.print(" Lost: " + t2.getPlayer(0).getELO_Score());
        assertTrue(t1.getPlayer(0).getELO_Score() > 2000);
        assertTrue(t2.getPlayer(0).getELO_Score() < 2000);
    }

    @Test
    void testLostResult(){
        Team t1 = new Team(new Player(2000), new Player(1500), new Player(2500));
        Team t2 = new Team(new Player(2000), new Player(3000));
        ArrayList<Team> tAL = new ArrayList<>();
        tAL.add(t1); tAL.add(t2);

        ec.affectELOAfterResult(tAL, MatchResult.LOST);
        // System.out.print("won: " + t2.getPlayer(0).getELO_Score());
        // System.out.print(" Lost: " + t1.getPlayer(0).getELO_Score());
        assertTrue(t2.getPlayer(0).getELO_Score() > 2000);
        assertTrue(t1.getPlayer(0).getELO_Score() < 2000);
    }
}
