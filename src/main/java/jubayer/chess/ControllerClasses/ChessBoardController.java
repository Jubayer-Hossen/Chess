package jubayer.chess.ControllerClasses;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import jubayer.chess.ModelClasses.Bishop;
import jubayer.chess.ModelClasses.Game;
import jubayer.chess.ModelClasses.King;
import jubayer.chess.ModelClasses.Knight;
import jubayer.chess.ModelClasses.Move;
import jubayer.chess.ModelClasses.Pawn;
import jubayer.chess.ModelClasses.Piece;
import jubayer.chess.ModelClasses.Player;
import jubayer.chess.ModelClasses.Position;
import jubayer.chess.ModelClasses.Queen;
import jubayer.chess.ModelClasses.Rook;
import jubayer.chess.ModelClasses.Square;

public class ChessBoardController {
    @FXML private GridPane boardGrid;

    private Game game;

    private Position selectedPosition = null;
    private Set<Position> highlightedMoves = new HashSet<>();

    @FXML
    public void initialize() {
        // Initialize model
        Player white = new Player("White", jubayer.chess.ModelClasses.Color.WHITE);
        Player black = new Player("Black", jubayer.chess.ModelClasses.Color.BLACK);
        game = new Game(white, black);

        drawBoard();
    }

    private void drawBoard() {
        boardGrid.getChildren().clear();
        Square[][] squares = game.getBoard().getSquares();

        // Find king position for current player
        Position kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = squares[row][col].getPiece();
                if (p instanceof King && p.getColor() == game.getCurrentTurn().getColor()) {
                    kingPos = p.getPosition();
                }
            }
        }
        boolean kingInCheck = game.isKingInCheck(game.getCurrentTurn().getColor());

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = squares[row][col];
                StackPane cell = new StackPane();
                Rectangle rect = new Rectangle(60, 60);
                // Alternate color
                if ((row + col) % 2 == 0) {
                    rect.setFill(javafx.scene.paint.Color.ALICEBLUE);
                } else {
                    rect.setFill(javafx.scene.paint.Color.DIMGRAY);
                }
                cell.getChildren().add(rect);

                Piece piece = square.getPiece();
                if (piece != null) {
                    ImageView pieceImageView = getPieceSVGImageView(piece);
                    // Neon glow for king in check
                    if (piece instanceof King && kingInCheck && kingPos != null && kingPos.getRow() == row && kingPos.getCol() == col) {
                        javafx.scene.effect.DropShadow glow = new javafx.scene.effect.DropShadow();
                        glow.setColor(javafx.scene.paint.Color.RED);
                        glow.setRadius(20);
                        glow.setSpread(0.7);
                        pieceImageView.setEffect(glow);
                    }
                    if (pieceImageView != null) {
                        cell.getChildren().add(pieceImageView);
                    }
                }

                // Highlight possible moves with a dot
                Position cellPos = new Position(row, col);
                if (highlightedMoves.contains(cellPos)) {
                    javafx.scene.shape.Circle dot = new javafx.scene.shape.Circle(8, javafx.scene.paint.Color.rgb(30, 144, 255, 0.5));
                    dot.setMouseTransparent(true);
                    cell.getChildren().add(dot);
                }

                final int r = row, c = col;
                cell.setOnMouseClicked(e -> handleCellClick(new Position(r, c)));
                boardGrid.add(cell, col, row);
            }
        }
    }

    // Helper to get the SVG file name for a piece
    private String getPieceSVGFileName(Piece piece) {
        String color = piece.getColor() == jubayer.chess.ModelClasses.Color.WHITE ? "White" : "Black";
        String type;
        if (piece instanceof King) type = "King";
        else if (piece instanceof Queen) type = "Queen";
        else if (piece instanceof Rook) type = "Rook";
        else if (piece instanceof Bishop) type = "Bishop";
        else if (piece instanceof Knight) type = "Knight";
        else if (piece instanceof Pawn) type = "Pawn";
        else type = "Unknown";
        return color + type + ".svg.svg";
    }

    // Helper to load SVG and convert to ImageView using SVG Salamander
    private ImageView getPieceSVGImageView(Piece piece) {
        try {
            String fileName = getPieceSVGFileName(piece);
            String resourcePath = "/jubayer/chess/Pieces/" + fileName;
            InputStream svgStream = getClass().getResourceAsStream(resourcePath);
            if (svgStream == null) return null;

            // Load SVG with Salamander
            SVGUniverse svgUniverse = new SVGUniverse();
            URI svgUri = svgUniverse.loadSVG(svgStream, fileName);
            SVGDiagram diagram = svgUniverse.getDiagram(svgUri);
            // Render SVG to BufferedImage
            int width = 48, height = 48;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2 = bufferedImage.createGraphics();
            diagram.setDeviceViewport(new java.awt.Rectangle(0, 0, width, height));
            diagram.render(g2);
            g2.dispose();
            // Convert to JavaFX Image
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView imageView = new ImageView(fxImage);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleCellClick(Position pos) {
        Piece clickedPiece = game.getBoard().getPieceAt(pos);

        if (selectedPosition == null) {
            // Select a piece
            if (clickedPiece != null && clickedPiece.getColor() == game.getCurrentTurn().getColor()) {
                selectedPosition = pos;
                // Highlight only legal moves (that block check if in check)
                highlightedMoves.clear();
                List<Move> moves = game.getLegalMoves(clickedPiece);
                if (moves != null) {
                    for (Move m : moves) {
                        highlightedMoves.add(m.getTo());
                    }
                }
                drawBoard();
            }
        } else {
            // Try to make a move
            Piece selectedPiece = game.getBoard().getPieceAt(selectedPosition);
            if (selectedPiece != null) {
                List<Move> legalMoves = game.getLegalMoves(selectedPiece);
                boolean found = false;
                for (Move m : legalMoves) {
                    if (m.getTo().equals(pos)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Move move = new Move(selectedPosition, pos, clickedPiece);
                    if (game.makeMove(move)) {
                        // Move successful
                        selectedPosition = null;
                        highlightedMoves.clear();
                        drawBoard();
                    } else {
                        // Invalid move, reset selection
                        selectedPosition = null;
                        highlightedMoves.clear();
                        drawBoard();
                    }
                } else {
                    // Not a legal move
                    selectedPosition = null;
                    highlightedMoves.clear();
                    drawBoard();
                }
            }
        }
    }
}