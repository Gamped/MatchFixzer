package MatchFixzer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

// This is the main class of the program, which also handles the UI
public class UI extends Application {
    /* FIELDS */
    private static int xScreenSize = (int)(Toolkit.getDefaultToolkit().getScreenSize().width / 1.3);
    private static int yScreenSize = (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.3);
    private Memory mem = new Memory();
    private MatchMaker mm = new MatchMaker(50, 0.15);
    private ArrayList<Player> allPlayers = null;

    /* START */
    // Make this the main class
    public static void main(String[] args){
        launch(args);
    }

    // The function called at the beginning of the programs lifetime
    public void start(Stage primaryStage){
        HBox base = new HBox();
        VBox buttonBox = new VBox();

        // Setup the UI
        setupPrimaryBackground(primaryStage, base);
        generateMenuButtons(buttonBox, base);
        base.getChildren().add(buttonBox);
        createMainMenuUI(base);
        primaryStage.show();

        // Get all players
        allPlayers = mem.loadAllPlayers();
    }

    /* METHODS */
    // Generates the menu buttons
    private void generateMenuButtons(VBox bBox, HBox base){
        Button bMakeTeams = new Button("Make teams");
        Button bReportResult = new Button("Report result");
        Button bPlayers = new Button("Add new player");
        Button bRankings = new Button("Rankings");
        Button bSaveAndExit = new Button("Save and exit");
        LinkedList<Button> menuButtons = new LinkedList<>();

        // Fill linkedList
        menuButtons.add(bMakeTeams);
        menuButtons.add(bReportResult);
        menuButtons.add(bPlayers);
        menuButtons.add(bRankings);
        menuButtons.add(bSaveAndExit);

        // Style the box
        bBox.setPadding(new Insets(15, 12, 15, 12));
        bBox.setSpacing(10);
        bBox.setStyle("-fx-background-color: linear-gradient(#090913, #070d13);");

        // Setup buttons
        for (Button b: menuButtons){
            bBox.getChildren().add(b);
            b.setPrefSize((xScreenSize / 6), (yScreenSize / menuButtons.size()));
            styleButton(b, false);
            b.setOnMousePressed(e -> styleButton(b, true));
            b.setOnMouseReleased(e -> styleButton(b, false));
        }

        // Custom button events
        bMakeTeams.setOnMouseClicked(e -> createTeamsUI(base));
        //bReportResult.setOnMouseClicked(e -> [FUNCTION]);
        bPlayers.setOnMouseClicked(e -> createPlayerUI(base));
        bRankings.setOnMouseClicked(e -> createRankingsUI(base));
        bSaveAndExit.setOnMouseClicked(e -> saveAndExit());
    }

    // Styles a button depending on if it pressed or not
    private void styleButton(Button b, boolean pressed){
        if (pressed){
            b.setStyle("-fx-background-color: #00060c, " +
                    "linear-gradient(#0d1521, #1b2e47); " +
                    "-fx-text-fill: linear-gradient(#838383, #96a8ba); " +
                    "-fx-font-size: 25px; " +
                    "-fx-background-insets: 0,1.5,5,5,5;" +
                    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                    "-fx-font-family: "+ "Tahoma"+";");
        } else {
            b.setStyle("-fx-background-color: #21415f, " +
                    "linear-gradient(#0d1a26, #1a344c); " +
                    "-fx-text-fill: linear-gradient(#ffffff, #c6d9eb); " +
                    "-fx-font-size: 25px; " +
                    "-fx-background-insets: 0,1.5,5,5,5;" +
                    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                    "-fx-font-family: "+ "Tahoma"+";");
        }
    }

    // Styles & setup primary stage
    private void setupPrimaryBackground(Stage ps, HBox base){
        Image icon = new Image("resources/alien.png");

        ps.setTitle("MatchFixzer");
        ps.getIcons().add(icon);
        ps.setScene(new Scene(base, xScreenSize, yScreenSize));

        defaultStyle(base, null, null);
    }

    // Save and exit functionality
    private void saveAndExit(){
        mem.saveAllPlayers(allPlayers);
        Platform.exit();
        System.exit(42);
    }

    // Create the UI for adding players players
    private void createPlayerUI(HBox base){
        VBox playerCreator = new VBox();
        Text title = new Text("Add new player");
        Button bCreatePlayer = new Button("Create Player");
        Text nameHelpText = new Text("Enter player name:");
        TextField nameField = new TextField();

        // Remove other box if present
        if (base.getChildren().size() > 1){
            base.getChildren().remove(base.getChildren().size() - 1);
        }

        // General styling
        defaultStyle(null, playerCreator, nameHelpText);
        nameHelpText.setTextAlignment(TextAlignment.CENTER);
        defaultStyle(null,null, title);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setStyle("-fx-font-size: 50px");
        playerCreator.setSpacing(25);
        playerCreator.setPrefSize(((xScreenSize / 6) * 5) - 10, yScreenSize);
        playerCreator.setAlignment(Pos.CENTER);
        styleButton(bCreatePlayer, false);
        nameField.setMaxWidth(xScreenSize / 3);

        // Add content to UI
        playerCreator.getChildren().add(title);
        playerCreator.getChildren().addAll(nameHelpText, nameField);
        playerCreator.getChildren().add(bCreatePlayer);

        // Setup button logic
        bCreatePlayer.setOnMousePressed(e -> styleButton(bCreatePlayer, true));
        bCreatePlayer.setOnMouseReleased(e -> styleButton(bCreatePlayer, false));
        bCreatePlayer.setOnMouseClicked(e -> createPlayer(nameField.getText(), base));

        base.getChildren().add(playerCreator);
    }

    // Functionality called when a new player is created
    private void createPlayer(String name, HBox base){
        Player p = new Player(name);
        allPlayers.add(p);
        mem.savePlayer(p);
        createMainMenuUI(base);
    }

    // Create the UI for adding players players
    private void createMainMenuUI(HBox base){
        VBox mainMenu = new VBox();
        Text title = new Text("Welcome to MatchFixzer");
        ImageView ivLogo = new ImageView();
        Image logo = new Image("resources/alien.png");

        // Remove other box if present
        if (base.getChildren().size() > 1){
            base.getChildren().remove(base.getChildren().size() - 1);
        }

        // General styling
        defaultStyle(null, mainMenu, title);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setStyle("-fx-font-size: 50px;");
        mainMenu.setSpacing(25);
        mainMenu.setPrefSize(((xScreenSize / 6) * 5) - 10, yScreenSize);
        mainMenu.setAlignment(Pos.CENTER);

        // Setup image
        ivLogo.setImage(logo);
        ivLogo.setScaleX(1.15);
        ivLogo.setScaleY(1.15);

        // Add title and logo to main page
        mainMenu.getChildren().add(title);
        mainMenu.getChildren().add(ivLogo);
        base.getChildren().add(mainMenu);
    }

    // Create the UI for listing off rankings
    private void createRankingsUI(HBox base){
        VBox rankingList = new VBox();
        Text title = new Text("Rankings:");
        ListView<String> rankings = new ListView<>();
        ArrayList<Player> tempPlayers = new ArrayList<>(allPlayers);
        ObservableList<String> players = FXCollections.observableArrayList();

        // Remove other box if present
        if (base.getChildren().size() > 1){
            base.getChildren().remove(base.getChildren().size() - 1);
        }

        // General styling
        defaultStyle(null, rankingList, title);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setStyle("-fx-font-size: 50px");
        rankingList.setPrefSize(((xScreenSize / 6) * 5) - 10, yScreenSize);
        rankingList.setAlignment(Pos.CENTER);
        rankings.setMaxSize(xScreenSize / 2.5, yScreenSize);
        rankingList.setSpacing(20);

        // Sort players & add them to list
        tempPlayers.sort(playerELOCompare);
        for (Player p: tempPlayers) {
            players.add("ELO: " + p.getELO_Score() + " | Name: " + p.getPlayerName());
        }
        rankings.setItems(players);

        // Add elements to UI
        rankingList.getChildren().add(title);
        rankingList.getChildren().add(rankings);
        base.getChildren().add(rankingList);
    }

    // Create the UI for listing off rankings
    private void createTeamsUI(HBox base){
        VBox createTeamsUI = new VBox();
        HBox listHolder = new HBox();
        Text title = new Text("Create teams:");
        int i = 1;
        // Variables for the 3 rows
        VBox row1 = new VBox(), row2 = new VBox(), row3 = new VBox();
        Text tRow1 = new Text("All players"), tRow2 = new Text("Chosen players"), tRow3 = new Text("Teams");
        // First row list
        ListView<String> playerList = new ListView<>();
        ObservableList<String> players = FXCollections.observableArrayList();
        // Second row list
        ListView<String> selectedPlayerList = new ListView<>();
        ObservableList<String> sSelectedPlayers = FXCollections.observableArrayList();
        // Third row team lists
        ListView<String> team1 = new ListView<>();
        ListView<String> team2 = new ListView<>();
        // Linked list to minimize copy-paste code
        LinkedList<VBox> v = new LinkedList<>();
        LinkedList<Text> t = new LinkedList<>();
        LinkedList<Button> b = new LinkedList<>();
        // Buttons
        Button bSelectPlayer = new Button("Add player");
        Button bMakeTeams = new Button("Make teams");
        // Storage of players & teams
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        ArrayList<Team> teams = null;

        // Remove other box if present
        if (base.getChildren().size() > 1){
            base.getChildren().remove(base.getChildren().size() - 1);
        }

        // Put similar items into LinkedDists to reduce copy/paste styling/setup
        v.add(createTeamsUI); v.add(row1); v.add(row2); v.add(row3);
        t.add(title); t.add(tRow1); t.add(tRow2); t.add(tRow3);
        b.add(bSelectPlayer); b.add(bMakeTeams);

        // General styling
        defaultStyle(null, createTeamsUI, null);
        createTeamsUI.setPrefSize(((xScreenSize / 6) * 5) - 10, yScreenSize);
        listHolder.setAlignment(Pos.CENTER);
        listHolder.setSpacing(30);
        for (VBox vb: v){
            vb.setSpacing(20);
            vb.setAlignment(Pos.CENTER);
        }
        for (Text tex: t){
            defaultStyle(null,null,tex);
            tex.setTextAlignment(TextAlignment.CENTER);
        }
        title.setStyle("-fx-font-size: 50px");
        for (Button B: b){
            B.setOnMousePressed(e -> styleButton(B, true));
            B.setOnMouseReleased(e -> styleButton(B, false));
            styleButton(B, false);
        }

        // Add players into first list & setup button
        for (Player p: allPlayers) {
            players.add(i + "| " + p.getPlayerName());
            i++;
        }
        playerList.setItems(players);
        bSelectPlayer.setOnMouseClicked(e -> addPlayerToSelectedArray(playerList.getSelectionModel().getSelectedItems().toString(),
                                        allPlayers, selectedPlayers, sSelectedPlayers, selectedPlayerList));

        // Fill the first row
        row1.getChildren().addAll(tRow1, playerList, bSelectPlayer);
        listHolder.getChildren().add(row1);

        // Add elements for the second row
        selectedPlayerList.setItems(sSelectedPlayers);
        bMakeTeams.setOnMouseClicked(e -> generateTeams(teams, selectedPlayers, row3, team1, team2));
        row2.getChildren().addAll(tRow2, selectedPlayerList, bMakeTeams);
        listHolder.getChildren().add(row2);

        // Add elements for row 3
        team1.setMaxHeight(yScreenSize / 3);
        team2.setMaxHeight(yScreenSize / 3);
        row3.getChildren().addAll(tRow3, team1, team2);
        listHolder.getChildren().add(row3);

        // Add elements to UI
        createTeamsUI.getChildren().add(title);
        createTeamsUI.getChildren().add(listHolder);
        base.getChildren().add(createTeamsUI);
    }

    // Add selected player to array
    private void addPlayerToSelectedArray(String selectionString, ArrayList<Player> from, ArrayList<Player> to,
                                          ObservableList<String> list, ListView<String> lw ){
        // Only do stuff if something is selected
        if (!selectionString.equals("[]")) {
            // Split up the string, in order to get index
            String[] splitSelection = selectionString.split("|");

            // Make sure that it is not already in selected players
            if (to.size() > 0) {
                for (Player p : to) {
                    if (from.get(Integer.parseInt(splitSelection[1]) - 1).getPlayerID() == p.getPlayerID()) {
                        return;
                    }
                }
            }

            // Add it to the selected players & update the list
            to.add(from.get(Integer.parseInt(splitSelection[1]) - 1));
            list.add(from.get(Integer.parseInt(splitSelection[1]) - 1).getPlayerName());
            lw.setItems(list);
        }
    }

    private void generateTeams(ArrayList<Team> output, ArrayList<Player> input, VBox row,
                               ListView<String> team1, ListView<String> team2){
        ObservableList<String> team1Players = FXCollections.observableArrayList();
        ObservableList<String> team2Players = FXCollections.observableArrayList();
        ArrayList<ObservableList<String>> stringArray = new ArrayList<>();
        int i = 0;

        // Make sure input is valid
        if (input.size() <= 0){return;}

        // Remove UI elements if already there

        // Generate the teams
        output = mm.generateTeams(input, 50);

        // Loop through the index
        stringArray.add(team1Players); stringArray.add(team2Players);
        for (Team t: output ){
            for (Player p: t.getTeamPlayers()){
                stringArray.get(i).add(p.getPlayerName());
            }
            i++;
        }

        team1.setItems(team1Players);
        team2.setItems(team2Players);
    }

    // Compare the two players ELO in order to sort the arraylist
    private static Comparator<Player> playerELOCompare = new Comparator<Player>() {
        public int compare(Player s1, Player s2) {
            int p1ELO = s1.getELO_Score();
            int p2ELO = s2.getELO_Score();

            //ascending order
            return p2ELO - p1ELO;

        }};

    // The default setStyle for UI elements
    private void defaultStyle(HBox h, VBox v, Text t){
        if (h != null){
            h.setStyle("-fx-background-color: #00060c, " +
                    "linear-gradient(#0d1a26, #1a344c); " +
                    "-fx-text-fill: linear-gradient(#ffffff, #c6d9eb); " +
                    "-fx-font-size: 25px; " +
                    "-fx-background-insets: 0,1.5,5,5,5;" +
                    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                    "-fx-font-family: "+ "Tahoma"+";");
        }

        if (v != null){
            v.setStyle("-fx-background-color: #00060c, " +
                    "linear-gradient(#0d1a26, #1a344c); " +
                    "-fx-text-fill: linear-gradient(#ffffff, #c6d9eb); " +
                    "-fx-font-size: 25px; " +
                    "-fx-background-insets: 0,1.5,5,5,5;" +
                    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                    "-fx-font-family: "+ "Tahoma"+";");
        }

        if (t != null){
            t.setStyle("-fx-font-size: 25px; -fx-font-family: Tahoma;");
            t.setFill(Color.WHITESMOKE);
        }
    }
}
