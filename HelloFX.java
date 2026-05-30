/**

        * File: Culminating Graphics

        * Author: Nithin.A

        * Date Created: may 28th, 2026

        * Date Last Modified: may 30th, 2026

        */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class HelloFX extends Application implements EventHandler<ActionEvent>{
    
    @Override
    public void start(Stage mainMenuStage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");
        // Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        // Scene scene = new Scene(new StackPane(l), 640, 480);
        // mainMenuStage.setScene(scene);
        // mainMenuStage.show();


        // main menu
        mainMenuStage.setTitle("Checkers");
        Button start = new Button("Start Game");


        Label gameDescription = new Label("Welcome to Pass-The-Device two player checkers! \n Click Start to play.");
        Scene startMenu, mainGame, endScreen;

        HBox startMenuLayout = new HBox(20);
        startMenuLayout.getChildren().addAll(start, gameDescription);
        startMenu = new Scene(startMenuLayout, 500, 500);
        start.setOnAction(event -> {
            System.out.println("but...");

            //mainMenuStage.setScene(mainGame);
        });



        // here we add everything together
        // Scene scene = new Scene(layout, 640, 480);
        // mainMenuStage.setScene(scene);
        // mainMenuStage.show();
    }

    @Override
    public void handle(ActionEvent buttonclick) {
       
    }


    public static void main(String[] args) {
        launch(args);
    }

}