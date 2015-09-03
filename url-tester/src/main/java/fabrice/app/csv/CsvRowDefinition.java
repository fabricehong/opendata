package fabrice.app.csv;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvRowDefinition {
    private final int colStart;
    private final int colEnd;

    public CsvRowDefinition(int colStart, int colEnd) {
        this.colStart = colStart-1;
        this.colEnd = colEnd-1;
    }

    public int getColEnd() {
        return colEnd;
    }

    public int getColStart() {
        return colStart;
    }

    public String[] getDataRow(String[] originalRow) {
        String[] newRow = new String[getColEnd() - getColStart() + 1];
        int count = 0;
        for (int i = getColStart(); i <= getColEnd(); i++) {
            newRow[count] = originalRow[i];
            count++;
        }
        return newRow;
    }
}
