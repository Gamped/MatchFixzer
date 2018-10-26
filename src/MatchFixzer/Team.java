package MatchFixzer;

import java.util.ArrayList;

// A team is a collection of players
public class Team {
    /* FIELDS */
    // List of all the players
    private ArrayList<Player> team = new ArrayList<>();
    private double averageELO;
    private int totalELO;
    private String teamName = "Un-named";

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
    public Player getPlayer(int index){
        if (index < team.size() && index >= 0){
            return team.get(index);
        } else throw new IllegalArgumentException("INVALID PLAYER INDEX!");
    }
    public String getTeamName(){return teamName;}

    /* METHODS */
    // Calculate the average ELO on the team
    public void calAverageELO(){
        int totalPlayers = team.size();
        calTotalELO();
        averageELO = (double)totalELO / (double)totalPlayers;
    }

    // Calculate the total ELO of the team
    public void calTotalELO(){
        totalELO = 0;
        for(Player p: team){
            totalELO += p.getELO_Score();
        }
    }

    // Make team name based on current names
    public void makeDefaultName(){
        String newName = "", s = "";
        for (Player p: team){
            s = p.getPlayerName().substring(0,3);
            newName += s;
        }
        teamName = newName;
    }
}
