package jubayer.chess.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import jubayer.chess.ModelClasses.Bishop;
import jubayer.chess.ModelClasses.Game;
import jubayer.chess.ModelClasses.King;
import jubayer.chess.ModelClasses.Knight;
import jubayer.chess.ModelClasses.Move;
import jubayer.chess.ModelClasses.Pawn;
import jubayer.chess.ModelClasses.Piece;
import jubayer.chess.ModelClasses.Player;
import jubayer.chess.ModelClasses.Position;
import jubayer.chess.ModelClasses.Queen;
import jubayer.chess.ModelClasses.Rook;
import jubayer.chess.ModelClasses.Square;

public class ChessBoardController {
    @FXML private GridPane boardGrid;

    private Game game;

    private Position selectedPosition = null;

    @FXML
    public void initialize() {
        // Initialize model
        Player white = new Player("White", jubayer.chess.ModelClasses.Color.WHITE);
        Player black = new Player("Black", jubayer.chess.ModelClasses.Color.BLACK);
        game = new Game(white, black);

        drawBoard();
    }

    private void drawBoard() {
        boardGrid.getChildren().clear();
        Square[][] squares = game.getBoard().getSquares();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = squares[row][col];
                StackPane cell = new StackPane();
                Rectangle rect = new Rectangle(60, 60);
                // Alternate color
                if ((row + col) % 2 == 0) {
                    rect.setFill(javafx.scene.paint.Color.LIMEGREEN);
                } else {
                    rect.setFill(javafx.scene.paint.Color.DEEPSKYBLUE);
                }
                cell.getChildren().add(rect);

                Piece piece = square.getPiece();
                if (piece != null) {
                    Label pieceLabel = new Label(getPieceUnicode(piece));
                    pieceLabel.setFont(new javafx.scene.text.Font(32));
                    cell.getChildren().add(pieceLabel);
                }

                final int r = row, c = col;
                cell.setOnMouseClicked(e -> handleCellClick(new Position(r, c)));
                boardGrid.add(cell, col, row);
            }
        }
    }

    private String getPieceUnicode(Piece piece) {
        // Basic Unicode for chess pieces
        if (piece instanceof King)   return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♔" : "♚";
        if (piece instanceof Queen)  return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♕" : "♛";
        if (piece instanceof Rook)   return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♖" : "♜";
        if (piece instanceof Bishop) return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♗" : "♝";
        if (piece instanceof Knight) return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♘" : "♞";
        if (piece instanceof Pawn)   return piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "♙" : "♟";
        return "?";
    }

    private void handleCellClick(Position pos) {
        Piece clickedPiece = game.getBoard().getPieceAt(pos);

        if (selectedPosition == null) {
            // Select a piece
            if (clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn().getColor()) {
                selectedPosition = pos;
                // Optionally: highlight possible moves
            }
        } else {
            // Try to make a move
            Piece selectedPiece = game.getBoard().getPieceAt(selectedPosition);
            if (selectedPiece != null) {
                Move move = new Move(selectedPosition, pos, clickedPiece);
                if (game.makeMove(move)) {
                    // Move successful
                    selectedPosition = null;
                    drawBoard();
                } else {
                    // Invalid move, reset selection
                    selectedPosition = null;
                }
            }
        }
    }
}