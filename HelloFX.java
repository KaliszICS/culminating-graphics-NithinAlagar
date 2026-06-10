/**

        * File: Culminating Graphics

        * Author: Nithin.A

        * Date Created: may 28th, 2026

        * Date Last Modified: june 10th, 2026

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
import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class HelloFX extends Application{
   static Stage mainGameStage;
   static BorderPane mainGameLayout;
   static Scene startMenu, mainGame, endScreen;
   // Setting global variables
   // the board that will be used as reference for all move checks
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
   // reference for how the starting layout should look like
   static final String[][] startingBoardLayout = {
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
   static boolean pieceSelected;
   // A true value means that it's white to move, a false value means it's black's move
   static boolean playerTurn = true;
   static boolean continueCapture = false;
   static boolean drawOffered, drawCooldownFinished;


   @Override
   public void start(Stage stage) {
       mainGameStage = stage;
       // making and setting the start menu
       mainGameStage.setTitle("Checkers");
       Button start = new Button("Start Game");
       Button drawButton = new Button("Offer Draw");
       Label gameDescription1 = new Label("Welcome to Pass-The-Device two player checkers!");
       Label gameDescription2 = new Label("Click Start to play.");
       BorderPane startMenuLayout = new BorderPane();
       startMenuLayout.setTop(gameDescription1);
       startMenuLayout.setCenter(gameDescription2);
       startMenuLayout.setBottom(start);
       // creating the main game menu
       GridPane grid = createGrid();
       mainGameLayout = new BorderPane();
       mainGameLayout.setCenter(grid);
       mainGameLayout.setLeft(drawButton);
       mainGame = new Scene(mainGameLayout, 700, 600);
       start.setOnAction(event -> {
               // creating new game
               newGame();
               mainGameStage.setScene(mainGame);
       });
       // creating draw button for mainGame
       Label endLabel = new Label("Game Drawn");
       BorderPane endLayout = new BorderPane();
       endLayout.setCenter(endLabel);
       endScreen = new Scene(endLayout, 600, 500);
       drawButton.setOnAction(mouseEvent -> {
           if (!drawOffered) {
               drawOffered = true;
               drawCooldownFinished = false;
               // getting player to wait 5 seconds after draw button is pressed
               drawButton.setText("Wait 5s...");
               PauseTransition pause = new PauseTransition(Duration.seconds(5));
               pause.setOnFinished(event -> {
                   drawCooldownFinished = true;
                   drawButton.setText("Accept Draw?");
               });
               pause.play();
               return;
           }
           // after 5 seconds have passed and draw button is pressed send user back to endScreen
           if (drawOffered && drawCooldownFinished) {
               mainGameStage.setScene(endScreen);
           }
       });
       // showing the
       startMenu = new Scene(startMenuLayout, 600, 500);
       mainGameStage.setScene(startMenu);
       mainGameStage.show();
       // creating a button to send user back to start menu on endscreen
       Button backButton = new Button("Back to Menu");
       endLayout.setBottom(backButton);
       backButton.setOnAction(mouseEvent -> {
           drawOffered = false;
           drawCooldownFinished = false;
           drawButton.setText("Offer Draw");
           mainGameStage.setScene(startMenu);
       });
   }




   public static void main(String[] args) {
       launch(args);
   }
   // function to check move validity
   public static boolean moveValidity(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow) {
       // checking if move is within bounds
       if (moveToRow < 0 || moveToRow > 7 ||
           moveToCol < 0 || moveToCol > 7) {
           return false;
       }
       // checking if moveTo tile is empty
       if (!board[moveToRow][moveToCol].equals("")) {
           return false;
       }


       return normalMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow) || capturingMove(selectedPieceCol, selectedPieceRow, moveToCol, moveToRow);
   }
      
   // function for checking if move was a normal move
   public static boolean normalMove(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        // checking if pieces are moving in the right direction for a normal move
        
       if (board[selectedPieceRow][selectedPieceCol].equals("⛀")){
           return (selectedPieceCol == moveToCol + 1 || selectedPieceCol == moveToCol - 1) && moveToRow - selectedPieceRow == 1;
       } else if ((board[selectedPieceRow][selectedPieceCol].equals("⛂"))){
           return (selectedPieceCol == moveToCol + 1 || selectedPieceCol == moveToCol - 1) && moveToRow - selectedPieceRow == -1;
       } else if (board[selectedPieceRow][selectedPieceCol].equals("⛁") || board[selectedPieceRow][selectedPieceCol].equals("⛃")) {
               return Math.abs(moveToCol - selectedPieceCol) == 1  && Math.abs(moveToRow - selectedPieceRow) == 1;
       } else {
           return false;
       }
   }




   // checking if piece captures something
   public static boolean capturingMove(int selectedPieceCol, int selectedPieceRow, int moveToCol, int moveToRow){
        // checking for out of bounds
       if (moveToRow < 0 || moveToRow > 7 ||
           moveToCol < 0 || moveToCol > 7) {
           return false;
       }
       String pieceMoving = board[selectedPieceRow][selectedPieceCol];
       int distanceRow = Math.abs(moveToRow - selectedPieceRow);
       int distanceCol = Math.abs(moveToCol - selectedPieceCol);
       // checking distance travelled is 2 squares
       if (distanceCol != 2 || distanceRow != 2) {
           return false;
       }
       // checking if square is empty
       if (!board[moveToRow][moveToCol].equals("")){
           return false;
       }
       String pieceCapturee = board[(selectedPieceRow + moveToRow) / 2][(selectedPieceCol + moveToCol) / 2];
       // checking if the piece being caputre is empty
       if (pieceCapturee.equals("")){
           return false;
       }

       // checking if pieces are moving in the right direction
        boolean isKing = pieceMoving.equals("⛁") || pieceMoving.equals("⛃");
        if (!isKing) {
            if (pieceMoving.equals("⛀") && moveToRow - selectedPieceRow != 2){
                return false;
            }
            if (pieceMoving.equals("⛂") && moveToRow - selectedPieceRow != -2){
                return false;
            }
        }
       // checking if pieces are captuing the opposing piece
       boolean movingBlack = pieceMoving.equals("⛀") || pieceMoving.equals("⛁");
       boolean movingWhite = pieceMoving.equals("⛂") || pieceMoving.equals("⛃");
       boolean captureeBlack = pieceCapturee.equals("⛀") || pieceCapturee.equals("⛁");
       boolean captureeWhite = pieceCapturee.equals("⛂") || pieceCapturee.equals("⛃");
       if ((movingBlack && captureeBlack) || (movingWhite && captureeWhite)) {
           return false;
       }
       return true;
   }

   // function to move the pieces
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

       // Checking for promotions and changing pieces visually and on 2d board array if there are
       if (board[moveToRow][moveToCol].equals("⛂") && moveToRow == 0) {
           board[moveToRow][moveToCol] = "⛃";
           Circle kingPiece = (Circle) tileMoveTo.getChildren().get(tileMoveTo.getChildren().size() - 1);
           kingPiece.setStroke(Color.GOLD);
           kingPiece.setStrokeWidth(5);
       } else if (board[moveToRow][moveToCol].equals("⛀") && moveToRow == 7) {
           board[moveToRow][moveToCol] = "⛁";
           Circle kingPiece = (Circle) tileMoveTo.getChildren().get(tileMoveTo.getChildren().size() - 1);
           kingPiece.setStroke(Color.GOLD);
           kingPiece.setStrokeWidth(5);
       }
       // Checking if the piece that was just moved has an capturing and forcing an multi-jump to occur
       if (captureeTaken && hasCapture(moveToRow, moveToCol)) {
            continueCapture = true;
            pieceSelected = true;
       } else {
           continueCapture = false;
       }
   }
   // function to check if piece has a capture avalible or not
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
   if (piece.equals("⛃") || piece.equals("⛁")) {
   return capturingMove(col,row,col-2,row-2)
       || capturingMove(col,row,col+2,row-2)
       || capturingMove(col,row,col-2,row+2)
       || capturingMove(col,row,col+2,row+2);
   }
   return false;
   }
   // function to create a new board
   public static GridPane createGrid(){
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
               if (board[row][col].equals("⛀")){
                   Circle piece = new Circle(25, Color.BLACK);
                   tile.getChildren().add(piece);
               } else if (board[row][col].equals("⛂")){
                   Circle piece = new Circle(25, Color.WHITE);
                   tile.getChildren().add(piece);
               } else if (board[row][col].equals("⛁")){
                   Circle piece = new Circle(25, Color.BLACK);
                   tile.getChildren().add(piece);
                   piece.setStroke(Color.GOLD);
                   piece.setStrokeWidth(5);
               } else if (board[row][col].equals("⛃")){
                   Circle piece = new Circle(25, Color.WHITE);
                   tile.getChildren().add(piece);
                   piece.setStroke(Color.GOLD);
                   piece.setStrokeWidth(5);
               }
               final int tileRow = row;
               final int tileCol = col;
               grid.add(tile, col, row);
               gridPaneTileArray[row][col] = tile;
               tile.setOnMousePressed(mouseEvent -> {
                    System.out.println(
                        "continueCapture=" + continueCapture +
                        " selected=(" + selectedPieceRow + "," + selectedPieceCol + ")" +
                        " clicked=(" + tileRow + "," + tileCol + ")"
                    );
                   String clickedPiece = board[tileRow][tileCol];
                   boolean whitePiece = clickedPiece.equals("⛂") || clickedPiece.equals("⛃");
                   boolean blackPiece = clickedPiece.equals("⛀") || clickedPiece.equals("⛁");
                   // Checking if there is a multi jump in play and forcing the multi jump to be played
                   if (continueCapture) {
                        if (!capturingMove(selectedPieceCol, selectedPieceRow, tileCol, tileRow)) {
                            return; // ignore invalid clicks
                        }
                       movePiece(selectedPieceCol, selectedPieceRow, tileCol, tileRow);
                       // exiting multi jump if there is none
                       if (!hasCapture(tileRow, tileCol)) {
                           continueCapture = false;
                           playerTurn = !playerTurn;
                           pieceSelected = false;
                       }
                       String winner = winner();
                       if (!winner.equals("")){
                           showEndScreen(winner + "has won!", startMenu);
                       }
                       selectedPieceRow = tileRow;
                       selectedPieceCol = tileCol;
                       return;
                   }
                   // checking if the a piece that just made a capture that moved to the square it's already on and canceling the mouseEvent if true
                   if (continueCapture && tileRow == selectedPieceRow && tileCol == selectedPieceCol) {
                       return;
                   }
                   // Checking if a piece was already selected and if there is a piece actually there to select
                   if (!pieceSelected && playerTurn && whitePiece) {
                       selectedPieceCol = tileCol;
                       selectedPieceRow = tileRow;
                       pieceSelected = true;
                   } else if (!pieceSelected && !playerTurn && blackPiece){
                       selectedPieceCol = tileCol;
                       selectedPieceRow = tileRow;
                       pieceSelected = true;
                   } else if (playerTurn && whitePiece && !continueCapture){
                       selectedPieceRow = tileRow;
                       selectedPieceCol = tileCol;
                       pieceSelected = true;
                   } else if (!playerTurn && blackPiece && !continueCapture){
                       selectedPieceRow = tileRow;
                       selectedPieceCol = tileCol;
                       pieceSelected = true;
                   } else if (pieceSelected && moveValidity(selectedPieceCol, selectedPieceRow, tileCol, tileRow)) {
                       movePiece(selectedPieceCol, selectedPieceRow, tileCol, tileRow);
                       String winner = winner();
                       if (!winner.equals("")){
                           showEndScreen(winner + "has won!", startMenu);
                       }
                       if (!continueCapture) {
                           playerTurn = !playerTurn;
                           pieceSelected = false;
                       }
                   }
               });
           }
       }
       return grid;
   }


   public static void resetBoard() {
       for (int row = 0; row < 8; row++) {
           for (int col = 0; col < 8; col++) {
               board[row][col] = startingBoardLayout[row][col];
           }
       }
       }
   public static void newGame() {
       resetBoard();
       playerTurn = true;
       continueCapture = false;
       pieceSelected = false;
       drawOffered = false;
       drawCooldownFinished = false;
       GridPane grid = createGrid();
       mainGameLayout.setCenter(grid);
   }
    public static boolean hasPieces(boolean whitePlayer) {
       for (int row = 0; row < 8; row++) {
           for (int col = 0; col < 8; col++) {


               String piece = board[row][col];


               if (whitePlayer &&
                   (piece.equals("⛂") || piece.equals("⛃"))) {
                   return true;
               }


               if (!whitePlayer &&
                   (piece.equals("⛀") || piece.equals("⛁"))) {
                   return true;
               }
           }
       }
       return false;
   }
   public static boolean hasLegalMove(boolean whitePlayer) {
       for (int row = 0; row < 8; row++) {
           for (int col = 0; col < 8; col++) {


               String piece = board[row][col];


               boolean ownedPiece =
                   whitePlayer &&
                   (piece.equals("⛂") || piece.equals("⛃"));


               ownedPiece |=
                   !whitePlayer &&
                   (piece.equals("⛀") || piece.equals("⛁"));


               if (!ownedPiece) {
                   continue;
               }


               // Check all possible destinations
               for (int newRow = 0; newRow < 8; newRow++) {
                   for (int newCol = 0; newCol < 8; newCol++) {


                       if (moveValidity(col, row, newCol, newRow)) {
                           return true;
                       }
                   }
               }
           }
       }


       return false;
   }
   public static String winner() {


       if (!hasPieces(true)) {
           return "Black";
       }


       if (!hasPieces(false)) {
           return "White";
       }


       if (!hasLegalMove(true)) {
           return "Black";
       }


       if (!hasLegalMove(false)) {
           return "White";
       }


       return "";
   }
   private static void showEndScreen(String message, Scene startMenu) {
   Label endLabel = new Label(message);
   BorderPane endLayout = new BorderPane();
   endLayout.setCenter(endLabel);
   Button backButton = new Button("Back to Menu");
   endLayout.setBottom(backButton);
   Scene endScene = new Scene(endLayout, 600, 500);
   backButton.setOnAction(MouseEvent -> {
       mainGameStage.setScene(startMenu);
   });
   mainGameStage.setScene(endScene);
}
}








