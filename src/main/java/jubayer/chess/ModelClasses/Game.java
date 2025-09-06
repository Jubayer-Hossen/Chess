package jubayer.chess.ModelClasses;

import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private Player currentTurn;

    public Game(Player white, Player black) {
        this.board = new Board();
        this.players = List.of(white, black);
        this.currentTurn = white;
    }

    public void startGame() {
        // Board is already initialized in constructor
    }

    public boolean makeMove(Move move) {
        // Here you should check if move is valid and by the current player's piece
        Piece piece = board.getPieceAt(move.getFrom());
        if (piece == null || piece.getColor() != currentTurn.getColor()) return false;
        // Check if move is in valid moves
        List<Move> validMoves = piece.getValidMoves(board);
        boolean found = false;
        for (Move m : validMoves) {
            if (m.getTo().equals(move.getTo())) {
                found = true;
                break;
            }
        }
        if (!found) return false;
        board.movePiece(move);
        // Switch turn
        currentTurn = (currentTurn == players.get(0)) ? players.get(1) : players.get(0);
        return true;
    }

    public boolean isCheckmate() {
        // Implement check/checkmate logic
        return false;
    }

    public boolean isStalemate() {
        // Implement stalemate logic
        return false;
    }

    public Board getBoard() { return board; }
    public Player getCurrentTurn() { return currentTurn; }
}