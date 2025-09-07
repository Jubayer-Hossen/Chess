package jubayer.chess.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            Position pos = new Position(position.getRow() + dr[i], position.getCol() + dc[i]);
            if (board.isWithinBounds(pos)) {
                Piece target = board.getPieceAt(pos);
                if (target == null || target.getColor() != color) {
                    moves.add(new Move(position, pos, target));
                }
            }
        }

        // Castling logic
        if (!this.hasMoved()) {
            int row = this.position.getRow();
            // King-side castling
            if (canCastle(board, true)) {
                moves.add(new Move(position, new Position(row, 6), null, true));
            }
            // Queen-side castling
            if (canCastle(board, false)) {
                moves.add(new Move(position, new Position(row, 2), null, true));
            }
        }
        return moves;
    }

    private boolean canCastle(Board board, boolean kingSide) {
        int row = this.position.getRow();
        int col = this.position.getCol();
        if (kingSide) {
            // Check squares between king and rook
            for (int c = col + 1; c < 7; c++) {
                if (board.isOccupied(new Position(row, c))) return false;
            }
            Piece rook = board.getPieceAt(new Position(row, 7));
            if (rook instanceof Rook && rook.getColor() == this.color && !rook.hasMoved()) {
                // TODO: Check if king is in check or passes through check
                return true;
            }
        } else {
            for (int c = col - 1; c > 0; c--) {
                if (board.isOccupied(new Position(row, c))) return false;
            }
            Piece rook = board.getPieceAt(new Position(row, 0));
            if (rook instanceof Rook && rook.getColor() == this.color && !rook.hasMoved()) {
                // TODO: Check if king is in check or passes through check
                return true;
            }
        }
        return false;
    }
}
