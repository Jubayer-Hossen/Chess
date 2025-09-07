package jubayer.chess.ModelClasses;

import java.util.List;

public abstract class Piece {
    protected Color color;
    protected Position position;
    protected boolean hasMoved = false;

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
    public boolean hasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public abstract List<Move> getValidMoves(Board board);
    public void move(Position to) {
        this.position = to;
        this.hasMoved = true;
    }
}
