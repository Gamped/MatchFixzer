package MatchFixzer;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// A class to handle saving and loading data
// This is just a quick save system, which can be improved much and made more secure ;)
public class Memory {
    /* CONSTRUCTORS */
    public Memory(){}

    /* METHODS */
    // Save a players info into a file with their ID
    // Returns true if successful and false if not
    public boolean savePlayer(Player p) {
        String fileName;
        BufferedWriter wr;
        boolean hasValidID = false;

        // Check if ID is set already, else set ID
        while (p.getPlayerID() ==  -1 && !hasValidID){
            File tester;

            // Using random ID, as incremental is easy to guess
            p.setPlayerID(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
            tester = new File("memory/p" + Integer.toString(p.getPlayerID()) + ".txt");

            // Check if it gave an already existing ID, if not then exit loop
            if (!tester.exists()){
                hasValidID = true;
            }
        }

        // Make file for player using their unique player ID
        fileName = "memory/p" + Integer.toString(p.getPlayerID()) + ".csv";

        // Write to the file
        try {
            wr = new BufferedWriter(new FileWriter(fileName));

            // Write info
            wr.write(p.getPlayerID() + ";");
            wr.write(p.getPlayerName() + ";");
            wr.write(p.getELO_Score() + ";");
            wr.write("\n");

            // Write player match history
            for (MatchResult r: p.getMatchHistory().getMatchHistory()){
                wr.write(r + ";");
            }

            wr.close();
            return true;
        } catch (IOException e){
            System.out.print("UNABLE TO SAVE PLAYER!!!");
            return false;
        }
    }

    // Loads all players
    public ArrayList<Player> loadAllPlayers(){
        ArrayList<Player> pAl = new ArrayList<>();
        Player p;
        File memFolder = new File("memory/");
        File[] memList = null;
        BufferedReader br;
        String line;
        String[] lineContent = null;

        // Check for validity
        if (!memFolder.exists() || !memFolder.isDirectory()){
            throw new IllegalArgumentException("WRONG FILE PATH NAME FOR MEMORY FOLDER");
        }

        // Get all files in folder
        memList = memFolder.listFiles();

        // Loop through every file, create a player & save the player
        for (File f: memList){
            // Make sure we read from player files
            if (f.getName().startsWith("p")){
                try {
                    br = new BufferedReader(new FileReader(f.getPath()));

                    // Read first line & add content
                    line = br.readLine();
                    lineContent = line.split(";");
                    p = new Player(Integer.parseInt(lineContent[2]),Integer.parseInt(lineContent[0]), lineContent[1]);

                    // Read match history & add it
                    if (br.lines().count() > 1) {
                        line = br.readLine();
                        lineContent = line.split(";");
                        for (String s : lineContent) {
                            switch (s) {
                                case "WON":
                                    p.addMatchResult(MatchResult.WON, 0);
                                    break;
                                case "LOST":
                                    p.addMatchResult(MatchResult.LOST, 0);
                                    break;
                                case "TIE":
                                    p.addMatchResult(MatchResult.TIE, 0);
                                    break;
                                default:
                                    throw new IllegalArgumentException("WRONG MATCH HISTORY NAME IN FILE!");
                            }
                        }
                    }

                    // Add player to arrayList and close file
                    pAl.add(p);
                    br.close();
                } catch (Exception e){throw new IllegalArgumentException("UNABLE TO READ [" + f.getName() + "]FROM MEMORY FOLDER");}
            }
        }
        return pAl;
    }

    // Save all players in an arrayList
    public boolean saveAllPlayers(ArrayList<Player> pAl){
        boolean outcome;

        // Loop through all players and save them
        for (Player p: pAl){
            outcome = savePlayer(p);

            if (!outcome){
                System.out.print("DID NOT MANAGE TO SAVE ALL PLAYERS");
                return false;
            }
        }

        return true;
    }
}
