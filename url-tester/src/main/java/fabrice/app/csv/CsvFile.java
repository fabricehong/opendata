package fabrice.app.csv;

import fabrice.app.csv.impl.CsvLineWriter;

import java.io.Writer;
import java.util.List;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvFile {
    private final String name;
    private final List<CsvSheet> sheets;

    public CsvFile(String name, List<CsvSheet> sheets) {
        this.name = name;
        this.sheets = sheets;
    }

    public List<CsvSheet> getSheets() {
        return sheets;
    }

    public void writeTo(Writer writer) {
        for (CsvSheet sheet : getSheets()) {
            for (CsvRow row : sheet.getDataRows()) {
                CsvLineWriter csvLineWriter = new CsvLineWriter(writer);
                csvLineWriter.write(row.getDataRow());
            }
        }
    }

    public String getName() {
        return name;
    }
}
