package MatchFixzer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// A maker of matches using a simple implementation of genetic algorithm
public class MatchMaker {
    /* FIELDS */
    private int maxGenAlgRuns;
    private ArrayList<Team> teams = null;

    /* CONSTRUCTORS */
    public MatchMaker(int maximumGeneticAlgorithmRuns){maxGenAlgRuns = maximumGeneticAlgorithmRuns;}

    /* GETTERS/SETTERS */
    public ArrayList<Team> getTeams(){return new ArrayList<Team>(teams);}

    /* METHODS */
    // Generates teams based on the genetic algorithm
    public void generateTeams(ArrayList<Player> allPlayersInMatch, int desiredTeams, int teamsPrRun){
        LinkedList<Player> pList = null;
        ArrayList<Team> doubleTeam = new ArrayList<>();
        LinkedList<ArrayList<Team>> tempTeams = new LinkedList<>();
        int nextIndex;
        boolean switcher = true;

        // Run though as long as maximum runs is not met
        for (int i = 0; i < maxGenAlgRuns; i++){
            //
            // INITIALIZATION OF TEAMS
            //

            // Default variables
            tempTeams.clear();

            // Generate a list of tempTeams
            for (int j = 0; j < teamsPrRun; j++){
                // Generate new linkedList with all of the players
                pList = fillList(allPlayersInMatch);

                //Empty the double team and then fill it with two blank teams
                doubleTeam.clear();
                doubleTeam.add(new Team()); doubleTeam.add(new Team());

                // Loop through every player and add to teams
                while (pList.size() > 0){
                    nextIndex = ThreadLocalRandom.current().nextInt(0, pList.size());

                    // Flip between assigning to each team
                    if (switcher){
                        doubleTeam.get(0).addPlayer(pList.get(nextIndex));
                        pList.remove(nextIndex);
                        switcher = false;
                    } else {
                        doubleTeam.get(1).addPlayer(pList.get(nextIndex));
                        pList.remove(nextIndex);
                        switcher = true;
                    }
                }
                // Add
                tempTeams.add(doubleTeam);
            }

        //
        //
        // NEED TO ADD THE REST OF THE GENETIC ALGORITHM
        //
        //

        }
    }

    // Fills an arrayList into a linked list
    private LinkedList<Player> fillList(ArrayList<Player> al){
        LinkedList<Player> tempList = new LinkedList<>();
        for (Player p: al){
            tempList.add(p);
        }
        return tempList;
    }
}
