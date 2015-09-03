package fabrice.app.csv;

import java.util.List;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvSheetDefinition {
    private final HeaderRowDefinition headerRowDefinition;

    private final int dataStart;

    public CsvSheetDefinition(HeaderRowDefinition headerRowDefinition, int dataStart) {
        this.headerRowDefinition = headerRowDefinition;
        this.dataStart = dataStart-1;
    }

    public HeaderRowDefinition getHeaderRowDefinition() {
        return headerRowDefinition;
    }

    public List<CsvRow> getData(List<CsvRow> rows) {
        return rows.subList(dataStart, rows.size());
    }

}
