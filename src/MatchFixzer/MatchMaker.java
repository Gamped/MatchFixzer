package MatchFixzer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

// A maker of matches using a simple implementation of genetic algorithm to create two teams
public class MatchMaker {
    /* FIELDS */
    private int maxGenAlgRuns;
    // The margin is added to the success criteria (0.5) on both sides
    private double margin;

    /* CONSTRUCTORS */
    // NOTE: The margin is added to the success criteria (0.5) on both sides
    // meaning that scores outside this margin will not be considered for selection
    public MatchMaker(int maximumGeneticAlgorithmRuns, double successMargin){
        maxGenAlgRuns = maximumGeneticAlgorithmRuns;
        margin = successMargin;
    }

    /* METHODS */
    // Generates teams based on the genetic algorithm
    public ArrayList<Team> generateTeams(ArrayList<Player> allPlayersInMatch, int teamsGeneratedPrRun){
        LinkedList<Player> pList = null;
        ArrayList<Team> doubleTeam = new ArrayList<>(), bestTeams = new ArrayList<>();
        LinkedList<ArrayList<Team>> tempTeams = new LinkedList<>();
        int nextIndex;
        boolean switcher = true, foundSuccess = false;
        double minSuccess, maxSuccess, score;


        // Calculate minSuccess & maxSuccess
        minSuccess = 0.5 - margin;
        maxSuccess = 0.5 + margin;

        // Run though for the maximum amount of runs
        // Will be broken if success criteria is met
        for (int i = 0; i < maxGenAlgRuns; i++){
            /* INITIALIZATION OF TEAMS */

            // Default variables
            tempTeams.clear();

            // Generate a list of tempTeams
            for (int j = 0; j < teamsGeneratedPrRun; j++){
                // Generate new linkedList with all of the players
                pList = fillList(allPlayersInMatch);

                // Empty the double team and then fill it with two blank teams
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

            /* EVALUATION */

            // Default bestTeams to first result, in case no teams within criteria can be made
            if (bestTeams.size() != 2) {
                bestTeams.clear();
                bestTeams.add(tempTeams.get(0).get(0));
                bestTeams.add(tempTeams.get(0).get(1));
                foundSuccess = false;
            }

            // Calculate score and check if criteria is met
            for (ArrayList<Team> t: tempTeams){
                score = evaluateTeams(t);;
                if (score >= minSuccess && score <= maxSuccess){
                    foundSuccess = true;
                    // Evaluate if it is better than current best teams
                    if (bestTeams.size() != 2){
                        if (score == 0.5){
                            // A PERFECT SCORE!!! So just fill and break loop
                            bestTeams.clear();
                            bestTeams.add(t.get(0));
                            bestTeams.add(t.get(1));
                            break;
                        } else if (score > 0.5 && score <= maxSuccess){
                            // Check if distance is less & change if true
                            if (score < evaluateTeams(bestTeams)){
                                bestTeams.clear();
                                bestTeams.add(t.get(0));
                                bestTeams.add(t.get(1));
                            }
                        } else if (score < 0.5 && score >= minSuccess){
                            // Check if distance is less & change if true
                            if (score > evaluateTeams(bestTeams)){
                                bestTeams.clear();
                                bestTeams.add(t.get(0));
                                bestTeams.add(t.get(1));
                            }
                        }
                    }
                }
            }
        //
        // Crossover & mutation are on purpose left out of "minimum viable product" version
        //
        }
        if (foundSuccess != true){
            System.out.print("Did not manage to find teams within criteria :(");
        }
        return bestTeams;
    }

    // Fills an arrayList into a linked list
    private LinkedList<Player> fillList(ArrayList<Player> al){
        LinkedList<Player> tempList = new LinkedList<>();
        for (Player p: al){
            tempList.add(p);
        }
        return tempList;
    }

    // Checks ELO of both teams and compares them
    // Success criteria would be 0.5, as that would mean equal ELO score
    private double evaluateTeams(ArrayList<Team> t){
        if (t.size() == 2){
            double score = t.get(0).getAverageELO() / (t.get(0).getAverageELO() + t.get(1).getAverageELO());
            return score;
        } else throw new IllegalArgumentException("INVALID AMOUNT OF TEAMS FOR EVALUATION!" + " [Teams = " + t.size() + "]");
    }
}
