package jubayer.chess.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, -1, 1};

        for (int d = 0; d < 4; d++) {
            int r = position.getRow(), c = position.getCol();
            while (true) {
                r += dr[d];
                c += dc[d];
                Position pos = new Position(r, c);
                if (!board.isWithinBounds(pos)) break;
                Piece target = board.getPieceAt(pos);
                if (target == null) {
                    moves.add(new Move(position, pos, null));
                } else {
                    if (target.getColor() != color) moves.add(new Move(position, pos, target));
                    break;
                }
            }
        }
        return moves;
    }
}
