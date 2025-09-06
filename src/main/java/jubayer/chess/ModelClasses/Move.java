package jubayer.chess.ModelClasses;

public class Move {
    private Position from, to;
    private Piece captured;

    public Move(Position from, Position to, Piece captured) {
        this.from = from;
        this.to = to;
        this.captured = captured;
    }
    public boolean isValid() { return from != null && to != null; }

    public Piece getCaptured() {
        return captured;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}
