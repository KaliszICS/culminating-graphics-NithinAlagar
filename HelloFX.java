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
    static String[][] board = {
            {"", "⛀", "", "⛀", "", "⛀", "", "⛀"},
            {"⛀", "", "⛀", "", "⛀", "", "⛀", ""},
            {"", "⛀", "", "⛀", "", "⛀", "", "⛀"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"⛂", "", "⛂", "", "⛂", "", "⛂", ""},
            {"", "⛂", "", "⛂", "", "⛂", "", "⛂"},
            {"⛂", "", "⛂", "", "⛂", "", "⛂", ""}
        };
    static StackPane[][] gridPaneTileArray = new StackPane[8][8];

    
    
    static int selectedPieceX, selectedPieceY;
    static boolean pieceSelected;

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
                gridPaneTileArray[col][row] = tile;
                tile.setOnMousePressed(MouseEvent -> {
                    // Checking if a piece was already selected and if there is a piece actually there to select
                    pieceSelected = false;
                    // the x and y are swapped somewhere
                    if (!pieceSelected && !board[tileRow][tileCol].equals("")) {
                        selectedPieceX = tileRow;
                        selectedPieceY = tileCol;
                        pieceSelected = true;
                    } else if (moveValidity(selectedPieceX, selectedPieceY, tileRow, tileCol)){
                            movePiece(selectedPieceX, selectedPieceY, tileRow, tileCol);
                            pieceSelected = false;
                    } else {
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
    public static boolean moveValidity(int selectedPieceX, int selectedPieceY, int moveToX, int moveToY){
        boolean normalMovesCheck = false;
        if (board[selectedPieceY][selectedPieceX].equals("⛀")){
            normalMovesCheck = ((selectedPieceX == moveToX + 1 || selectedPieceX == moveToX - 1) && moveToY - selectedPieceY == -1);
        } else if ((board[selectedPieceY][selectedPieceX].equals("⛂"))){
            normalMovesCheck = ((selectedPieceX == moveToX + 1 || selectedPieceX == moveToX - 1) && moveToY - selectedPieceY == 1);
        } else {
            return false;
        }
        return board[moveToY][moveToX].equals("") && normalMovesCheck;
    }
    public static void movePiece(int selectedPieceX, int selectedPieceY, int moveToX, int moveToY){
        // moving the piece on the stage itself
        StackPane tileTemp = gridPaneTileArray[selectedPieceY][selectedPieceX];
        System.out.println(tileTemp.getChildren().size());
        gridPaneTileArray[moveToY][moveToX].getChildren().add(tileTemp.getChildren().get(tileTemp.getChildren().size()-1));

        // Moving piece in 2d array
        board[moveToY][moveToX] = board[selectedPieceX][selectedPieceY];
        board[selectedPieceY][selectedPieceX] = "";

    }
}

