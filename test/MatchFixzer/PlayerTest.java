package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player p = null;

    @BeforeEach
    void setup(){p = new Player();}

    @Test
    void testDefaultPlayerELO(){assertEquals(p.getELO_Score(), 2000);}

    @Test
    void testDefaultPlayerELO_Impact1(){
        p.addMatchResult(MatchResult.WON, 100);
        assertEquals(p.getELO_Score(), 2100);
    }

    @Test
    void testDefaultPlayerELO_Impact2(){
        p.addMatchResult(MatchResult.WON, -100);
        assertEquals(p.getELO_Score(), 1900);
    }

    @Test
    void testDefaultName(){assertEquals(p.getPlayerName(), "Un-named");}

    @Test
    void testCustomName(){
        p.setPlayerName("TEST");
        assertEquals(p.getPlayerName(), "TEST");
    }
}
