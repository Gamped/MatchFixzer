package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}