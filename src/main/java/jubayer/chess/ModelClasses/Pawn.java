package jubayer.chess.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int dir = (color == Color.WHITE) ? -1 : 1;
        int startRow = (color == Color.WHITE) ? 6 : 1;

        Position oneForward = new Position(position.getRow() + dir, position.getCol());
        if (board.isWithinBounds(oneForward) && board.getPieceAt(oneForward) == null) {
            moves.add(new Move(position, oneForward, null));
            // Two forward from starting position
            Position twoForward = new Position(position.getRow() + 2 * dir, position.getCol());
            if (position.getRow() == startRow && board.getPieceAt(twoForward) == null && board.isWithinBounds(twoForward)) {
                moves.add(new Move(position, twoForward, null));
            }
        }
        // Captures
        for (int dc = -1; dc <= 1; dc += 2) {
            Position diag = new Position(position.getRow() + dir, position.getCol() + dc);
            if (board.isWithinBounds(diag)) {
                Piece target = board.getPieceAt(diag);
                if (target != null && target.getColor() != color) {
                    moves.add(new Move(position, diag, target));
                }
            }
        }
        return moves;
    }
}
