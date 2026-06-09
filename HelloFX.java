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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;


public class HelloFX extends Application{
    // setting global variables
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
    static int selectedPieceCol, selectedPieceRow;
    boolean pieceSelected;
    // a true value means that it's white to move, a false value means it's black's move
    static boolean playerTurn = true;

    @Override
    public void start(Stage mainGameStage) {
        System.out.println(board[0][1]);
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
        startMenu = new Scene(startMenuLayout, 600, 600);
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
                if (board[row][col].equals("⛀")){
                    Circle piece = new Circle(25, Color.BLACK);
                    tile.getChildren().add(piece);
                } else if (board[row][col].equals("⛂")){
                    Circle piece = new Circle(25, Color.WHITE);
                    tile.getChildren().add(piece);
                }
                final int tileRow = row;
                final int tileCol = col;
                grid.add(tile, col, row);
                gridPaneTileArray[row][col] = tile;
                tile.setOnMousePressed(MouseEvent -> {
                    // Checking if a piece was already selected and if there is a piece actually there to select
                    // the x and y are swapped somewhere
                    if (!pieceSelected && !board[tileRow][tileCol].equals("") && playerTurn && board[tileRow][tileCol].equals("⛂")) {
                        selectedPieceCol = tileCol;
                        selectedPieceRow = tileRow;
                        pieceSelected = true;
                    } else if (!pieceSelected && !board[tileRow][tileCol].equals("") && !playerTurn && board[tileRow][tileCol].equals("⛀")){
                        selectedPieceCol = tileCol;
                        selectedPieceRow = tileRow;
                        pieceSelected = true;
                    } else if (moveValidity(selectedPieceCol, selectedPieceRow, tileCol, tileRow) && pieceSelected){
                            movePiece(selectedPieceCol, selectedPieceRow, tileCol, tileRow);
                            playerTurn = !playerTurn;
                            String output = "";
                            for (int x = 0; x < 8; x++){
                                for (int y = 0; y < 8; y++){
                                    if (board[x][y].equals("")){
                                        output += "  □  ";
                                    } else {
                                        output += "  " + board[x][y] + "  ";
                                    }
                                }
                                output += "\n";
                            }
                            System.out.println(output);
                            pieceSelected = false;
                    } else {
                        pieceSelected = false;
                    }
                    
                });
            }
        }
        mainGame = new Scene(grid, 600, 600);
        start.setOnAction(event -> {
            System.out.println("but...");

            mainGameStage.setScene(mainGame);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public static boolean moveValidity(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        if (!board[moveToRow][moveToCol].equals("")){
            return false;
        }
        if (capturingMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow)){
            return true;
        }
        if (normalMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow)){
            return true;
        }
        return false;

    }
        
    
    public static boolean normalMove(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        if (board[selectedPieceRow][selectedPieceCol].equals("⛀")){
            return (selectedPieceCol == moveToCol + 1 || selectedPieceCol == moveToCol - 1) && moveToRow - selectedPieceRow == 1;
        } else if ((board[selectedPieceRow][selectedPieceCol].equals("⛂"))){
            return (selectedPieceCol == moveToCol + 1 || selectedPieceCol == moveToCol - 1) && moveToRow - selectedPieceRow == -1;
        } else {
            return false;
        }
        
    }

    // something wrong with logic here
    public static boolean capturingMove(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        String pieceMoving = board[selectedPieceRow][selectedPieceCol];
        String pieceCapturee = board[(selectedPieceRow + moveToRow) / 2][(selectedPieceCol + moveToCol) / 2];
        int distanceRow = Math.abs(moveToRow - selectedPieceRow);
        int distanceCol = Math.abs(moveToCol - selectedPieceCol);

        if (pieceCapturee.equals("")){
            return false;
        }

        if (distanceCol != 2 || distanceRow != 2){
            return false;
        }

        if (pieceMoving.equals("⛀") && moveToRow - selectedPieceRow != 2){
            return false;
        } else if (pieceMoving.equals("⛂") && moveToRow - selectedPieceRow != -2){
            return false;
        }
        //pieceMoving.equals("⛂") && 
        
        if (pieceMoving.equals("⛀") && pieceMoving.equals(pieceCapturee)
         || pieceMoving.equals("⛂") && pieceMoving.equals(pieceCapturee)){
            return false;
        }
        return true;
        
    }

    public static void movePiece(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        StackPane tileSelected = gridPaneTileArray[selectedPieceRow][selectedPieceCol];
        StackPane tileMoveTo = gridPaneTileArray[moveToRow][moveToCol];
        if (normalMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow)){
            // moving the piece on the stage itself
            tileMoveTo.getChildren().add(tileSelected.getChildren().get(tileSelected.getChildren().size() - 1));

            // Moving piece in 2d array
            board[moveToRow][moveToCol] = board[selectedPieceRow][selectedPieceCol];
            board[selectedPieceRow][selectedPieceCol] = "";

        } else if (capturingMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow)){
            int captureePiece = gridPaneTileArray[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2].getChildren().size() - 1;
            // moving the piece on the stage itself
            tileMoveTo.getChildren().add(tileSelected.getChildren().get(tileSelected.getChildren().size() - 1));
            gridPaneTileArray[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2].getChildren().remove(captureePiece);
            // Moving piece in 2d array
            board[moveToRow][moveToCol] = board[selectedPieceRow][selectedPieceCol];
            board[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2] = "";
            board[selectedPieceRow][selectedPieceCol] = "";
        }

    }
}

