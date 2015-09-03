package fabrice.app.csv;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvRow {
    private final CsvRowDefinition csvRowDefinition;
    private final String[] row;

    public CsvRow(CsvRowDefinition csvRowDefinition, String[] row) {
        this.csvRowDefinition = csvRowDefinition;
        this.row = row;
    }

    public String[] getRow() {
        return row;
    }

    public String[] getDataRow() {
        return csvRowDefinition.getDataRow(row);
    }
}
