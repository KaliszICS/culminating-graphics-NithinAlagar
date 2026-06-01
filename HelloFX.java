/**

        * File: Culminating Graphics

        * Author: Nithin.A

        * Date Created: may 28th, 2026

        * Date Last Modified: june 1st, 2026

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
import javafx.scene.shape.Rectangle;


public class HelloFX extends Application{
    
    @Override
    public void start(Stage mainGameStage) {

        // main menu
        mainGameStage.setTitle("Checkers");
        Button start = new Button("Start Game");


        Label gameDescription = new Label("Welcome to Pass-The-Device two player checkers! \n Click Start to play.");
        HBox startMenuLayout = new HBox(20);
        startMenuLayout.getChildren().addAll(start, gameDescription);
        Scene startMenu, mainGame, endScreen;
        // Label playerTurn = new Label(playerToMove + " to move.");
        // mainGame = new Scene(playerToMove, 500, 500);
        startMenu = new Scene(startMenuLayout, 500, 500);
        mainGameStage.setScene(startMenu);
        mainGameStage.show();

        GridPane grid = new GridPane();
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                StackPane tile = new StackPane();
                Rectangle color = new Rectangle(70, 70);
                if ((col+row)%2 == 0){
                    color.setFill(Color.WHITE);
                } else {
                    color.setFill(Color.BLACK);
                }
                tile.getChildren().add(color);
                // Adding a piece if there is one
                String pieceCode = board[col][row];
                if (!pieceCode.equals("")){
                    Label piece = new Label(board[col][row]);
                
                piece.setStyle("-fx-font-size: 40;");
                tile.getChildren().add(piece);
                }
                grid.add(tile, row, col);
            }
        }
        
        start.setOnAction(event -> {
            System.out.println("but...");

            mainGameStage.setScene(mainGame);
        });

        



        // here we add everything together
        // Scene scene = new Scene(layout, 640, 480);
    }


    public static void main(String[] args) {
        launch(args);
    }

}