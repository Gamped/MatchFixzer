package MatchFixzer;

// This is the class of an individual player
public class Player {
    /* FIELDS */
    // The players ELO score
    private int ELO_Score = 1000;
    // The history of the players matches
    private MatchHistory mh = null;

    /* CONSTRUCTORS */
    // Default creation of player
    public Player(){
        mh = new MatchHistory();
    }
    // Custom start ELO
    public Player(int ELO){
        ELO_Score = ELO;
    }
    // Custom match results
    public Player(MatchHistory priorMH){
        mh = priorMH;
    }
    // Custom match results & ELO
    public Player(MatchHistory priorMH, int ELO){
        mh = priorMH;
        ELO_Score = ELO;
    }

    /* GETTERS/SETTERS */
    public int getELO_Score(){return ELO_Score;}

    /* METHODS */
    public void addMatchResult(MatchResult r){
        mh.addMatchResult(r);

        //////////////////////////////////////////////// ADD ELO IMPACT
    }

}
