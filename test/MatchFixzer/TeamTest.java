package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    Team t = null;

    @BeforeEach
    void setup() {
        t = new Team(new Player(), new Player(2000), new Player(1500));
    }

    @Test
    void testTotalELO() {
        assertEquals(t.getTotalELO(), 4500);
    }

    @Test
    void testAverageELO() {
        assertEquals(t.getAverageELO(), 1500);
    }

    @Test
    void testNameGeneration(){
        t.getPlayer(0).setPlayerName("TESTER");
        t.getPlayer(1).setPlayerName("KESTER");
        t.getPlayer(2).setPlayerName("SESTER");
        t.makeDefaultName();

        assertEquals(t.getTeamName(), "TESKESSES");
    }

    @Test
    void testELORatio(){
        Double totalRatio = new Double(0);
        for (Double d: t.getTeamELORatio()){
            totalRatio += d;
        }
       assertEquals(totalRatio, new Double(1));
    }
}