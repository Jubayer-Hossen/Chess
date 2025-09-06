package jubayer.chess.ModelClasses;

import java.util.List;

public class Game {
    // Returns only the moves for the given piece that do not leave own king in check
    public List<Move> getLegalMoves(Piece piece) {
        List<Move> allMoves = piece.getValidMoves(board);
        List<Move> legalMoves = new java.util.ArrayList<>();
        for (Move move : allMoves) {
            // Simulate the move
            Board copy = copyBoard(board);
            Piece movingPiece = copy.getPieceAt(move.getFrom());
            copy.setPieceAt(move.getFrom(), null);
            copy.setPieceAt(move.getTo(), movingPiece);
            if (movingPiece != null) movingPiece.move(move.getTo());
            if (!isKingInCheckAfterMove(piece.getColor(), copy)) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    // Helper: returns true if color's king is in check on the given board
    private boolean isKingInCheckAfterMove(Color color, Board board) {
        Position kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board.getPieceAt(new Position(row, col));
                if (p instanceof King && p.getColor() == color) {
                    kingPos = p.getPosition();
                    break;
                }
            }
        }
        if (kingPos == null) return false;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board.getPieceAt(new Position(row, col));
                if (p != null && p.getColor() != color) {
                    List<Move> moves = p.getValidMoves(board);
                    for (Move m : moves) {
                        if (m.getTo().equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Helper: deep copy the board (pieces share references, but positions are copied)
    private Board copyBoard(Board original) {
        Board copy = new Board();
        // Clear all pieces from the new board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                copy.setPieceAt(new Position(row, col), null);
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = original.getPieceAt(new Position(row, col));
                if (p != null) {
                    Piece newPiece = null;
                    Position pos = new Position(row, col);
                    if (p instanceof King) newPiece = new King(p.getColor(), pos);
                    else if (p instanceof Queen) newPiece = new Queen(p.getColor(), pos);
                    else if (p instanceof Rook) newPiece = new Rook(p.getColor(), pos);
                    else if (p instanceof Bishop) newPiece = new Bishop(p.getColor(), pos);
                    else if (p instanceof Knight) newPiece = new Knight(p.getColor(), pos);
                    else if (p instanceof Pawn) newPiece = new Pawn(p.getColor(), pos);
                    copy.setPieceAt(pos, newPiece);
                } else {
                    copy.setPieceAt(new Position(row, col), null);
                }
            }
        }
        return copy;
    }
    // Returns true if the king of the given color is in check
    public boolean isKingInCheck(Color color) {
        // Find the king's position
        Position kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board.getPieceAt(new Position(row, col));
                if (p instanceof King && p.getColor() == color) {
                    kingPos = p.getPosition();
                    break;
                }
            }
        }
        if (kingPos == null) return false;
        // Check if any enemy piece can move to the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board.getPieceAt(new Position(row, col));
                if (p != null && p.getColor() != color) {
                    List<Move> moves = p.getValidMoves(board);
                    for (Move m : moves) {
                        if (m.getTo().equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private final Board board;
    private final List<Player> players;
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
        // Check if move is in legal moves (not leaving king in check)
        List<Move> legalMoves = getLegalMoves(piece);
        boolean found = false;
        for (Move m : legalMoves) {
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