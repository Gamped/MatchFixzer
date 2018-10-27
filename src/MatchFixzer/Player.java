package MatchFixzer;

// This is the class of an individual player
public class Player {
    /* FIELDS */
    // The players ELO score
    private int ELO_Score = 2000;
    // The history of the players matches
    private MatchHistory mh = null;
    private String playerName = "Un-named";

    /* CONSTRUCTORS */
    // Default creation of player
    public Player(){mh = new MatchHistory();}
    // Custom start ELO
    public Player(int ELO){
        mh = new MatchHistory();
        ELO_Score = ELO;
    }
    // Custom match results
    public Player(MatchHistory priorMH){mh = priorMH;}
    // Custom match results & ELO
    public Player(MatchHistory priorMH, int ELO){
        mh = priorMH;
        ELO_Score = ELO;
    }

    /* GETTERS/SETTERS */
    public int getELO_Score(){return ELO_Score;}
    public void setPlayerName(String s){playerName = s;}
    public String getPlayerName(){return  playerName;}
    public MatchHistory getMatchHistory() {return mh;}

    /* METHODS */
    public void addMatchResult(MatchResult r, int ELO_Impact){
        mh.addMatchResult(r);
        ELO_Score += ELO_Impact;
    }
}
