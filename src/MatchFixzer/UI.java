package MatchFixzer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
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
        VBox buttonBox = new VBox();;

        // Setup the UI
        setupPrimaryBackground(primaryStage, base);
        generateMenuButtons(buttonBox);
        base.getChildren().add(buttonBox);
        primaryStage.show();

        // Get all players
        allPlayers = mem.loadAllPlayers();
    }

    /* METHODS */
    // Generates the menu buttons
    private void generateMenuButtons(VBox bBox){
        Button bMakeTeams = new Button("Make teams");
        Button bReportResult = new Button("Report result");
        Button bPlayers = new Button("Players");
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
        //bMakeTeams.setOnMouseClicked(e -> [FUNCTION]);
        //bReportResult.setOnMouseClicked(e -> [FUNCTION]);
        //bPlayers.setOnMouseClicked(e -> [FUNCTION]);
        //bRankings.setOnMouseClicked(e -> [FUNCTION]);
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
    private void setupPrimaryBackground(Stage ps, HBox b){
        Image icon = new Image("resources/alien.png");

        ps.setTitle("MatchFixzer");
        ps.getIcons().add(icon);
        ps.setScene(new Scene(b, xScreenSize, yScreenSize));

        b.setStyle("-fx-background-color: #00060c, " +
                "linear-gradient(#0d1a26, #1a344c); " +
                "-fx-text-fill: linear-gradient(#ffffff, #c6d9eb); " +
                "-fx-font-size: 22px; " +
                "-fx-background-insets: 0,1.5,5,5,5;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                "-fx-font-family: "+ "Tahoma"+";");
    }

    // Save and exit functionality
    private void saveAndExit(){
        mem.saveAllPlayers(allPlayers);
        Platform.exit();
        System.exit(0);
    }
}
