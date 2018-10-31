package MatchFixzer;

import java.util.ArrayList;

// A class used to calculate ELO rankings
// ELO ranking primarily based upon: https://liquipedia.net/starcraft/Elo_rating
public class ELO_Calculator {
    /* CONSTRUCTORS */
    public ELO_Calculator(){}

    /* METHODS */
    // Returns the likely outcome of a match given t's perspective
    public MatchResult getLikelyResult(Team t, Team opponent){
        if (t.getTotalELO() == opponent.getTotalELO()){
            return MatchResult.TIE;
        } else if (t.getTotalELO() < opponent.getTotalELO()){
            return MatchResult.LOST;
        } else return MatchResult.WON;
    }

    // Affect the ELO for all players upon receiving a result of a match
    public void affectELOAfterResult(ArrayList<Team> teams, MatchResult rForFirstTeam){
        // Make sure that the input is valid
        if (teams.size() != 2){ throw new IllegalArgumentException("WRONG TEAM INPUT (" + teams.size() + ")");}

        // Switch based on result & impact teams ELO
        switch (rForFirstTeam){
            case WON:
                impactTeamPlayers(teams.get(0), teams.get(1), MatchResult.WON);
                impactTeamPlayers(teams.get(1), teams.get(0), MatchResult.LOST);
                break;
            case LOST:
                impactTeamPlayers(teams.get(1), teams.get(0), MatchResult.WON);
                impactTeamPlayers(teams.get(0), teams.get(1), MatchResult.LOST);
                break;
            case TIE:
                // Do nothing to their ELO, since they tied
                for (Team t: teams){
                    for (Player p : t.getTeamPlayers()){
                        p.addMatchResult(MatchResult.TIE, 0);
                    }
                }
        }
    }

    // Calculates the impact for a given team
    private int calELOImpact(double teamToImpactAvg, double opponentTeamAvg, MatchResult r){
        double impact;

        // If ELO is equal, then impact is 80
        if (teamToImpactAvg != opponentTeamAvg){
            // Splitting the formula up for better readability :)
            impact = 10 * (opponentTeamAvg - teamToImpactAvg);
            impact = 1 / impact;
            impact = 1 - impact;
            // Since this is a shared ELO impact for all members of the team I will use a higher
            // factor than commonly used in 1 on 1 ranking ELO system (usually 40 for new and 20 for old players)
            impact = 80 * impact;

            return (int) impact;
        } else return 80;
    }

    // Add the result to all team players and impact their ELO rating
    private void impactTeamPlayers(Team t, Team opponent, MatchResult r){
        int i = 0, impactELO;

        impactELO = calELOImpact(t.getAverageELO(), opponent.getAverageELO(), r);
        for (Player p: t.getTeamPlayers()){
            // Negate if lost
            if (r == MatchResult.LOST){
                p.addMatchResult(r, ((int)((double)impactELO * t.getPlayerELORatio(i))) * -1);
            } else { p.addMatchResult(r, (int)((double)impactELO * t.getPlayerELORatio(i)));}
            i++;
        }
    }
}
