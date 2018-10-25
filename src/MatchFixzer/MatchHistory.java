package MatchFixzer;

import java.util.ArrayList;

// This is a list of every match result, meant to be owned by a player
public class MatchHistory {
    /* FIELDS */
    // A list of all match results
    private ArrayList<MatchResult> matchHistory = new ArrayList<>();

    /* CONSTRUCTORS */
    // Constructor of blank match history
    public MatchHistory(){}

    // Makes new MatchHistory out of old list of results
    // @param oldMatchHistory: An ArrayList of previous results
    public MatchHistory(ArrayList<MatchResult> oldMatchHistory){
        matchHistory = oldMatchHistory;
    }

    /* GETTERS/SETTERS */
    // Send a copy of the match history
    public ArrayList<MatchResult> getMatchHistory(){
        return new ArrayList<MatchResult>(matchHistory);
    }

    // Get an individual result from the match result list
    // @param index: The wished index to get
    public MatchResult getMatchResult(int index){
        if (index < matchHistory.size() && index >= 0){
            return matchHistory.get(index);
        } else throw new IllegalArgumentException("INVALID MATCH INDEX!");
    }

    /* METHODS */
    // Add a match result to the list
    // @param r: The result wished for
    public void addMatchResult(MatchResult r){
        matchHistory.add(r);
    }

    // Removes a match result to the list
    // @param index: The index wished to be removed
    public void removeMatchResult(int index){
        if (index < matchHistory.size() && index >= 0){
            matchHistory.remove(index);
        }else throw new IllegalArgumentException("INVALID MATCH INDEX!");
    }
}
