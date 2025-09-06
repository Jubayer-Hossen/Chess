package jubayer.chess.ModelClasses;

public class Player {
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() { return name; }
    public Color getColor() { return color; }

    public void makeMove(Move move) {
        // In a real implementation this would interact with Game
    }
}