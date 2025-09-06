package jubayer.chess.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            Position pos = new Position(position.getRow() + dr[i], position.getCol() + dc[i]);
            if (board.isWithinBounds(pos)) {
                Piece target = board.getPieceAt(pos);
                if (target == null || target.getColor() != color) {
                    moves.add(new Move(position, pos, target));
                }
            }
        }
        return moves;
    }
}
