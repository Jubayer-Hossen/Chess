package jubayer.chess.ModelClasses;

public class Position {
    private final int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() { return row; }
    public int getCol() { return col; }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        Position p = (Position) obj;
        return this.row == p.row && this.col == p.col;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(col);
        return result;
    }
}