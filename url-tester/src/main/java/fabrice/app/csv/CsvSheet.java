package fabrice.app.csv;

import java.util.List;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvSheet {
    private String name;
    private List<CsvRow> rows;
    private final CsvSheetDefinition csvSheetDefinition;

    public CsvSheet(CsvSheetDefinition csvSheetDefinition, String name, List<CsvRow> rows) {
        this.csvSheetDefinition = csvSheetDefinition;
        this.name = name;
        this.rows = rows;
    }

    public List<CsvRow> getDataRows() {
        return csvSheetDefinition.getData(rows);
    }

    public String getName() {
        return name;
    }

    public CsvHeader[] getHeaders() {
        HeaderRowDefinition headerRowDefinition = csvSheetDefinition.getHeaderRowDefinition();
        CsvRow csvRow = rows.get(headerRowDefinition.getRow());
        String[] row = csvRow.getRow();
        CsvHeader[] headers = new CsvHeader[headerRowDefinition.getEndCol()-headerRowDefinition.getStartCol()+1];
        int index = 0;
        for (int i = headerRowDefinition.getStartCol(); i <= headerRowDefinition.getEndCol(); i++) {
            headers[index] = new CsvHeader(row[i]);
            index++;
        }
        return headers;
    }
}
