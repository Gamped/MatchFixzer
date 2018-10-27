package MatchFixzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MatchMakerTest {
    ArrayList<Player> p = new ArrayList<>();
    ArrayList<Team> teamOutput = null;
    MatchMaker mm = null;

    @BeforeEach
    void setup(){
        mm = new MatchMaker(50, 0.01);
        p.add(new Player(1500));
        p.add(new Player(925));
        p.add(new Player(1750));
        p.add(new Player());
        p.add(new Player(2000));
        p.add(new Player(2500));
        p.add(new Player(1500));
        p.add(new Player(900));
        p.add(new Player(3000));
        p.add(new Player(500));
    }


    @Test
    void printTeamTotalEloAndTestSize(){
        teamOutput = mm.generateTeams(p, 50);
        assertEquals(teamOutput.size(), 2);
        System.out.print(teamOutput.get(0).getAverageELO() + " - " + teamOutput.get(1).getAverageELO());
    }

}
