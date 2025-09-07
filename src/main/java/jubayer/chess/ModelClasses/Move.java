package jubayer.chess.ModelClasses;

public class Move {
    private Position from, to;
    private Piece captured;
    private boolean isCastling;

    public Move(Position from, Position to, Piece captured) {
        this(from, to, captured, false);
    }

    public Move(Position from, Position to, Piece captured, boolean isCastling) {
        this.from = from;
        this.to = to;
        this.captured = captured;
        this.isCastling = isCastling;
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

    public boolean isCastling() {
        return isCastling;
    }
}
