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
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;


public class HelloFX extends Application{
    String[][] board = {
            {"", "⛀", "", "⛀", "", "⛀", "", "⛀"},
            {"⛀", "", "⛀", "", "⛀", "", "⛀", ""},
            {"", "⛀", "", "⛀", "", "⛀", "", "⛀"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"⛂", "", "⛂", "", "⛂", "", "⛂", ""},
            {"", "⛂", "", "⛂", "", "⛂", "", "⛂"},
            {"⛂", "", "⛂", "", "⛂", "", "⛂", ""}
        };
        StackPane[][] gridPaneTileArray = new StackPane[8][8];

    
    boolean pieceSelected = false;
    int selectedPieceX, selectedPieceY;

    @Override
    public void start(Stage mainGameStage) {

        // main menu
        mainGameStage.setTitle("Checkers");
        Button start = new Button("Start Game");


        Label gameDescription1 = new Label("Welcome to Pass-The-Device two player checkers!");
        Label gameDescription2 = new Label("Click Start to play.");
        BorderPane startMenuLayout = new BorderPane();
        startMenuLayout.setTop(gameDescription1);
        startMenuLayout.setCenter(gameDescription2);
        startMenuLayout.setBottom(start);
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
                    color.setFill(Color.BEIGE);
                } else {
                    color.setFill(Color.BROWN);
                }
                tile.getChildren().add(color);
                // Adding a piece if there is one
                //String pieceCode = board[col][row];
                if (board[col][row].equals("⛀")){
                    Circle piece = new Circle(25, Color.BLACK);
                    tile.getChildren().add(piece);
                } else if (board[col][row].equals("⛂")){
                    Circle piece = new Circle(25, Color.WHITE);
                    tile.getChildren().add(piece);
                }
                final int tileRow = row;
                final int tileCol = col;
                grid.add(tile, row, col);
                gridPaneTileArray[row][col] = tile;
                tile.setOnMousePressed(MouseEvent -> {
                    if (!pieceSelected && !board[tileCol][tileRow].equals("")) {
                        selectedPieceX = tileCol;
                        selectedPieceY = tileRow;
                        pieceSelected = true;
                    } else {
                        if (movePiece(selectedPieceX, selectedPieceY, tileCol, tileRow)){
                            gridPaneTileArray[tileRow][tileCol].getChildren().add(gridPaneTileArray[selectedPieceY][selectedPieceX].getChildren().get(1));
                            gridPaneTileArray[selectedPieceY][selectedPieceX].getChildren().remove(1);
                        }
                        pieceSelected = false;
                    }
                });
            }
        }
        mainGame = new Scene(grid, 700, 700);
        start.setOnAction(event -> {
            System.out.println("but...");

            mainGameStage.setScene(mainGame);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
    public static boolean movePiece(int selectedPieceX, int selectedPieceY, int moveToX, int moveToY){
        return true;
    }
}

