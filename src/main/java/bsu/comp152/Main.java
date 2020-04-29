package bsu.comp152;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        var loc = getClass().getResource("Main.fxml");
        try {
            root = FXMLLoader.load(loc);
        } catch (IOException e) {
            System.out.println("Couldn't Find FXML file!!!!!!");
        }
        Scene windowContents = new Scene(root, 700, 500);
        primaryStage.setScene(windowContents);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    @FXML
    public void EXIT(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void openChuckNorris(ActionEvent event) {
        Parent ryanRoot = null;
        var source = getClass().getResource("Ryan.fxml");
        try {
            ryanRoot = FXMLLoader.load(source);
        } catch (IOException e) {
            System.out.println("Chuck Norris has not found this file.");
        }
        Scene windowContents = new Scene(ryanRoot, 400, 400);
        Stage chuckWindow = new Stage();
        chuckWindow.setScene(windowContents);
        chuckWindow.setTitle("Chuck Norris Jokes");
        chuckWindow.show();
    }

    @FXML
    public void openSongFinder(ActionEvent event) {
        Parent alexRoot = null;
        var alexSource = getClass().getResource("Alex.fxml");
        try {
            alexRoot = FXMLLoader.load(alexSource);
        } catch (IOException e) {
            System.out.println("The song finder has not found what you are looking.");
        }
        Scene windowContents = new Scene(alexRoot, 400, 400);
        Stage songWindow = new Stage();
        songWindow.setScene(windowContents);
        songWindow.setTitle("Song Finder");
        songWindow.show();
    }

}