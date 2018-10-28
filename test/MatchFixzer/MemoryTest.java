package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryTest {
    Memory mem = new Memory();
    Player p = null;
    boolean readResult;

    @BeforeEach
    void setup(){
        p = new Player();
        p.addMatchResult(MatchResult.WON, 120);
        p.addMatchResult(MatchResult.LOST, 460);
        p.setPlayerName("TESTER");
    }

    @Test
    void testPlayerSave(){
        readResult = mem.savePlayer(p);
        assertEquals(readResult, true);
    }

    @Test
    void testLoadAll(){
        ArrayList<Player> pAl = null;
        pAl = mem.loadAllPlayers();
        assertTrue(pAl.size() > 0);
    }
}
