package jubayer.chess.ModelClasses;

public class Board {
    private Square[][] squares = new Square[8][8];

    public Board() {
        // Initialize empty squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new Square(new Position(row, col), null);
            }
        }
        // Setup pieces for both colors (call a helper if needed)
        setupPieces();
    }

    private void setupPieces() {
        // Pawns
        for (int col = 0; col < 8; col++) {
            squares[1][col].setPiece(new Pawn(Color.BLACK, new Position(1, col)));
            squares[6][col].setPiece(new Pawn(Color.WHITE, new Position(6, col)));
        }
        // Rooks
        squares[0][0].setPiece(new Rook(Color.BLACK, new Position(0, 0)));
        squares[0][7].setPiece(new Rook(Color.BLACK, new Position(0, 7)));
        squares[7][0].setPiece(new Rook(Color.WHITE, new Position(7, 0)));
        squares[7][7].setPiece(new Rook(Color.WHITE, new Position(7, 7)));
        // Knights
        squares[0][1].setPiece(new Knight(Color.BLACK, new Position(0, 1)));
        squares[0][6].setPiece(new Knight(Color.BLACK, new Position(0, 6)));
        squares[7][1].setPiece(new Knight(Color.WHITE, new Position(7, 1)));
        squares[7][6].setPiece(new Knight(Color.WHITE, new Position(7, 6)));
        // Bishops
        squares[0][2].setPiece(new Bishop(Color.BLACK, new Position(0, 2)));
        squares[0][5].setPiece(new Bishop(Color.BLACK, new Position(0, 5)));
        squares[7][2].setPiece(new Bishop(Color.WHITE, new Position(7, 2)));
        squares[7][5].setPiece(new Bishop(Color.WHITE, new Position(7, 5)));
        // Queens
        squares[0][3].setPiece(new Queen(Color.BLACK, new Position(0, 3)));
        squares[7][3].setPiece(new Queen(Color.WHITE, new Position(7, 3)));
        // Kings
        squares[0][4].setPiece(new King(Color.BLACK, new Position(0, 4)));
        squares[7][4].setPiece(new King(Color.WHITE, new Position(7, 4)));
    }

    public Piece getPieceAt(Position pos) {
        return squares[pos.getRow()][pos.getCol()].getPiece();
    }

    public void setPieceAt(Position pos, Piece piece) {
        squares[pos.getRow()][pos.getCol()].setPiece(piece);
    }

    public boolean isOccupied(Position pos) {
        return getPieceAt(pos) != null;
    }

    public boolean isWithinBounds(Position pos) {
        int row = pos.getRow(), col = pos.getCol();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void movePiece(Move move) {
        Piece movingPiece = getPieceAt(move.getFrom());
        setPieceAt(move.getFrom(), null);
        setPieceAt(move.getTo(), movingPiece);
        if (movingPiece != null) movingPiece.move(move.getTo());
    }

    public Square[][] getSquares() { return squares; }
}