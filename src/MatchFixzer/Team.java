package MatchFixzer;

import java.util.ArrayList;

// A team is a collection of players
public class Team {
    /* FIELDS */
    // List of all the players
    private ArrayList<Player> team = new ArrayList<>();
    private double averageELO;
    private int totalELO;

    /* CONSTRUCTORS */
    // Team of 2 players
    public Team(Player p1, Player p2){
        team.add(p1);
        team.add(p2);
        calTotalELO();
        calAverageELO();
    }
    // Team of 3 players
    public Team(Player p1, Player p2, Player p3){
        team.add(p1);
        team.add(p2);
        team.add(p3);
        calTotalELO();
        calAverageELO();
    }
    // Team of 4 players
    public Team(Player p1, Player p2, Player p3, Player p4){
        team.add(p1);
        team.add(p2);
        team.add(p3);
        team.add(p4);
        calTotalELO();
        calAverageELO();
    }
    public Team(ArrayList<Player> customTeam){
        team = customTeam;
        calTotalELO();
        calAverageELO();
    }

    /* GETTERS/SETTERS */
    public int getTotalELO() {return totalELO;}
    public double getAverageELO() {return averageELO;}

    /* METHODS */
    // Calculate the average ELO on the team
    private void calAverageELO(){
        int totalPlayers = team.size();
        calTotalELO();
        averageELO = (double)totalELO / (double)totalPlayers;
    }

    // Calculate the total ELO of the team
    private void calTotalELO(){
        totalELO = 0;
        for(Player p: team){
            totalELO += p.getELO_Score();
        }
    }
}
