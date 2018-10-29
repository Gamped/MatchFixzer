package MatchFixzer;

// This is the class of an individual player
public class Player {
    /* FIELDS */
    // The players ELO score
    private int ELO_Score = 2000;
    // The history of the players matches
    private MatchHistory mh = null;
    private String playerName = "Un-named";
    private int playerID = -1;

    /* CONSTRUCTORS */
    // Default creation of player
    public Player(){mh = new MatchHistory();}
    // Name only creation of player
    public Player(String name){
        mh = new MatchHistory();
        playerName = name;
    }
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
    // Name, ELO & ID
    public Player(int ELO, int ID, String name){
        mh = new MatchHistory();
        ELO_Score = ELO;
        playerID = ID;
        playerName = name;
    }

    /* GETTERS/SETTERS */
    public int getELO_Score(){return ELO_Score;}
    public void setPlayerName(String s){playerName = s;}
    public String getPlayerName(){return  playerName;}
    public MatchHistory getMatchHistory() {return mh;}
    public void setPlayerID(int ID){playerID = ID;}
    public int getPlayerID() {return playerID;}

    /* METHODS */
    public void addMatchResult(MatchResult r, int ELO_Impact){
        mh.addMatchResult(r);
        ELO_Score += ELO_Impact;
    }
}
