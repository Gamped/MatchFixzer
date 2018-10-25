package MatchFixzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player p = null;

    @Test
    void testDefaultPlayerELO(){
        p = new Player();
        assertEquals(p.getELO_Score(), 1000);
    }

    @Test
    void testDefaultPlayerELO_Impact1(){
        p = new Player();
        p.addMatchResult(MatchResult.WON, 100);
        assertEquals(p.getELO_Score(), 1100);
    }

    @Test
    void testDefaultPlayerELO_Impact2(){
        p = new Player();
        p.addMatchResult(MatchResult.WON, -100);
        assertEquals(p.getELO_Score(), 900);
    }
}
