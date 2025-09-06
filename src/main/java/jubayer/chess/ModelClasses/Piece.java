package jubayer.chess.ModelClasses;

import java.util.List;

public abstract class Piece {
    protected Color color;
    protected Position position;

    public Piece(Color color, Position position){
        this.color = color;
        this.position = position;
    }
    public Color getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    public abstract List<Move> getValidMoves(Board board);
    public void move(Position to) { this.position = to; }
}
