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
    // Setting global variables
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
    // A true value means that it's white to move, a false value means it's black's move
    static boolean playerTurn = true;
    static boolean continueCapture = false;

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
        startMenu = new Scene(startMenuLayout, 600, 500);
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
                } else if (board[row][col].equals("⛁")){
                    Circle piece = new Circle(25, Color.WHITE);
                    tile.getChildren().add(piece);
                    piece.setStroke(Color.GOLD);
                    piece.setStrokeWidth(5);
                    tile.getChildren().add(piece);
                } else if (board[row][col].equals("⛃")){
                    Circle piece = new Circle(25, Color.WHITE);
                    tile.getChildren().add(piece);
                    piece.setStroke(Color.GOLD);
                    piece.setStrokeWidth(5);
                    tile.getChildren().add(piece);
                }
                final int tileRow = row;
                final int tileCol = col;
                grid.add(tile, col, row);
                gridPaneTileArray[row][col] = tile;
                tile.setOnMousePressed(MouseEvent -> {
                    String clickedPiece = board[tileRow][tileCol];
                    // Checking if there is a multi jump in play and forcing the multi jump to be played
                    if (capturingMove(selectedPieceCol, selectedPieceRow, tileCol, tileRow) && continueCapture) {
                        movePiece(selectedPieceCol, selectedPieceRow, tileCol, tileRow);
                        selectedPieceRow = tileRow;
                        selectedPieceCol = tileCol;
                        if (!hasCapture(tileRow, tileCol)) {
                            continueCapture = false;
                            playerTurn = !playerTurn;
                            pieceSelected = false;
                        }
                        return;
                    }
                    // checking if the a piece that just made a capture that moved to the square it's already on and canceling the mouseEvent if true
                    if (continueCapture && tileRow == selectedPieceRow && tileCol == selectedPieceCol) {
                        return;
                    }
                    // Checking if a piece was already selected and if there is a piece actually there to select
                    if (!pieceSelected && !clickedPiece.equals("") && playerTurn && clickedPiece.equals("⛂")) {
                        selectedPieceCol = tileCol;
                        selectedPieceRow = tileRow;
                        pieceSelected = true;
                    } else if (!pieceSelected && !clickedPiece.equals("") && !playerTurn && clickedPiece.equals("⛀")){
                        selectedPieceCol = tileCol;
                        selectedPieceRow = tileRow;
                        pieceSelected = true;
                    } else if (playerTurn && clickedPiece.equals("⛂") && !continueCapture){
                        selectedPieceRow = tileRow;
                        selectedPieceCol = tileCol;
                        pieceSelected = true;
                    } else if (!playerTurn && clickedPiece.equals("⛀") && !continueCapture){
                        selectedPieceRow = tileRow;
                        selectedPieceCol = tileCol;
                        pieceSelected = true;
                    } else if (pieceSelected && moveValidity(selectedPieceCol, selectedPieceRow, tileCol, tileRow)) {
                        movePiece(selectedPieceCol, selectedPieceRow, tileCol, tileRow);
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
                        if (!continueCapture) {
                            playerTurn = !playerTurn;
                            pieceSelected = false;
                        }
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
    
    public static boolean moveValidity(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow) {
        if (!board[moveToRow][moveToCol].equals("")) {
            return false;
        } 

        return normalMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow) || capturingMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow);
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
        if (moveToRow < 0 || moveToRow > 7 ||
            moveToCol < 0 || moveToCol > 7) {
            return false;
        }
        String pieceMoving = board[selectedPieceRow][selectedPieceCol];
        int distanceRow = Math.abs(moveToRow - selectedPieceRow);
        int distanceCol = Math.abs(moveToCol - selectedPieceCol);
        if (distanceCol != 2 || distanceRow != 2) {
            return false;
        }
        String pieceCapturee = board[(selectedPieceRow + moveToRow) / 2][(selectedPieceCol + moveToCol) / 2];
        if (pieceCapturee.equals("")){
            return false;
        }

        if (pieceMoving.equals("⛀") && moveToRow - selectedPieceRow != 2){
            return false;
        } else if (pieceMoving.equals("⛂") && moveToRow - selectedPieceRow != -2){
            return false;
        }
        //pieceMoving.equals("⛂") && 
        
        if (pieceMoving.equals(pieceCapturee)){
            return false;
        }
        return true;
    }

    public static void movePiece(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        StackPane tileSelected = gridPaneTileArray[selectedPieceRow][selectedPieceCol];
        StackPane tileMoveTo = gridPaneTileArray[moveToRow][moveToCol];
        boolean captureeTaken = capturingMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow);
        if (normalMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow)){
            // moving the piece on the stage itself
            tileMoveTo.getChildren().add(tileSelected.getChildren().get(tileSelected.getChildren().size() - 1));
            // Moving piece in 2d array
            board[moveToRow][moveToCol] = board[selectedPieceRow][selectedPieceCol];
            board[selectedPieceRow][selectedPieceCol] = "";

        } else if (captureeTaken){
            int captureePiece = gridPaneTileArray[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2].getChildren().size() - 1;
            // moving the piece on the stage itself
            tileMoveTo.getChildren().add(tileSelected.getChildren().get(tileSelected.getChildren().size() - 1));
            gridPaneTileArray[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2].getChildren().remove(captureePiece);
            // Moving piece in 2d array
            board[moveToRow][moveToCol] = board[selectedPieceRow][selectedPieceCol];
            board[(selectedPieceRow + moveToRow)/2][(selectedPieceCol + moveToCol)/2] = "";
            board[selectedPieceRow][selectedPieceCol] = "";
        }

        if (board[moveToRow][moveToCol].equals("⛂") && moveToRow == 0) {
            board[moveToRow][moveToCol] = "⛃";
        } else if (board[moveToRow][moveToCol].equals("⛀") && moveToRow == 7) {
            board[moveToRow][moveToCol] = "⛁";
        }
        // Checking if the piece that was just moved has an capturing and forcing an multi-jump to occur
        if (captureeTaken && hasCapture(moveToRow, moveToCol)) {
            continueCapture = true;
        } else {
            continueCapture = false;
        }
    }
    // Helper Function(s)
    // hasCapture is used to check if a piece has avalible captures
    public static boolean hasCapture(int row, int col) {

    String piece = board[row][col];

    if (piece.equals("⛂")) {
        return capturingMove(col, row, col - 2, row - 2)
            || capturingMove(col, row, col + 2, row - 2);
    }

    if (piece.equals("⛀")) {
        return capturingMove(col, row, col - 2, row + 2)
            || capturingMove(col, row, col + 2, row + 2);
    }

    return false;
    }
}

