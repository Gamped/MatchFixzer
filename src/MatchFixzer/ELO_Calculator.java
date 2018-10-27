package MatchFixzer;

import java.util.ArrayList;

// A class used to calculate ELO rankings
// ELO ranking primarily based upon: https://liquipedia.net/starcraft/Elo_rating
public class ELO_Calculator {
    /* FIELDS */

    /* CONSTRUCTORS */
    public ELO_Calculator(){}

    /* GETTERS/SETTERS */

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
        int ELOimpact;

        // Make sure that the input is valid
        if (teams.size() != 2){ throw new IllegalArgumentException("WRONG TEAM INPUT (" + teams.size() + ")");}

        // Switch based on result
        switch (rForFirstTeam){
            case WON:
                // Calculate winning team impact & add the impact to them
                ELOimpact = calELOImpact(teams.get(0).getAverageELO(), teams.get(1).getAverageELO(), MatchResult.WON);
                impactTeamPlayers(teams.get(0), ELOimpact, MatchResult.WON);

                // Calculate losing team impact & add the impact to them
                ELOimpact = calELOImpact(teams.get(1).getAverageELO(), teams.get(0).getAverageELO(), MatchResult.LOST);
                impactTeamPlayers(teams.get(1), ELOimpact, MatchResult.LOST);
                break;
            case LOST:
                // Calculate winning team impact & add the impact to them
                ELOimpact = calELOImpact(teams.get(1).getAverageELO(), teams.get(0).getAverageELO(), MatchResult.WON);
                impactTeamPlayers(teams.get(1), ELOimpact, MatchResult.WON);

                // Calculate losing team impact & add the impact to them
                ELOimpact = calELOImpact(teams.get(0).getAverageELO(), teams.get(1).getAverageELO(), MatchResult.LOST);
                impactTeamPlayers(teams.get(0), ELOimpact, MatchResult.LOST);
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

        // Splitting the formula up for better readability :)
        impact = 10 * (opponentTeamAvg - teamToImpactAvg);
        impact = 1 / impact;
        impact = 1 - impact;
        // Since this is a shared ELO impact for all members of the team I will use a higher
        // factor than commonly used in 1 on 1 ranking ELO system (usually 40 for new and 20 for old players)
        impact = 80 * impact;

        // Negate if lost
        if (r == MatchResult.LOST){
            impact = -impact;
        }
        return (int)impact;
    }

    private void impactTeamPlayers(Team t, int impactELO, MatchResult r){
        int i = 0;
        for (Player p: t.getTeamPlayers()){
            p.addMatchResult(MatchResult.WON, (int)((double)impactELO * t.getPlayerELORatio(i)));
            i++;
        }
    }
}
