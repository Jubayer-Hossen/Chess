package jubayer.chess.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        // Combine rook and bishop moves
        moves.addAll(new Rook(color, position).getValidMoves(board));
        moves.addAll(new Bishop(color, position).getValidMoves(board));
        return moves;
    }
}
