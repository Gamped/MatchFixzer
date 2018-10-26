package MatchFixzer;

// A class used to calculate ELO rankings
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








}
