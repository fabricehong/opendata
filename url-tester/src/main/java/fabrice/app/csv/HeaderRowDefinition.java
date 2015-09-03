package fabrice.app.csv;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class HeaderRowDefinition {
    private final int startCol;
    private final int row;
    private final int endCol;

    public HeaderRowDefinition(int row, int startCol, int endCol) {
        this.startCol = startCol-1;
        this.row = row-1;
        this.endCol = endCol-1;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getRow() {
        return row;
    }

    public int getEndCol() {
        return endCol;
    }
}
