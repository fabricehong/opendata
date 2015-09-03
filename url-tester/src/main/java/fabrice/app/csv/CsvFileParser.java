package fabrice.app.csv;

import com.google.common.collect.ImmutableSet;
import fabrice.app.csv.util.CsvUtil;
import fabrice.app.files.Directory;
import fabrice.app.files.Predicats;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CsvFileParser {
    static final int MAX_COL = 100;
    static final int MAX_ROW = 100;
    private Set<String> ignoredSheet = ImmutableSet.of("Partis");

    private final CsvSheetDefinition sheetDefinition;
    private CsvRowDefinition csvRowDefinition = new CsvRowDefinition(CsvUtil.colNameToindex("B"), CsvUtil.colNameToindex("AC"));

    public CsvFileParser(CsvSheetDefinition sheetDefinition) {
        this.sheetDefinition = sheetDefinition;
    }

    public List<CsvFile> getCsvFiles(String directoryPath) throws IOException {
        Directory directory = new Directory(directoryPath)
                .withPredicat(Predicats.hasExtension(".ods"))
                .withPredicat(Predicats.isFile());
        Collection<File> pathCollection = directory.getPathCollection();
        List<CsvFile> files = new ArrayList<CsvFile>();
        for (File file : pathCollection) {
            files.add(readOdsFile(file));
        }
        return files;
    }

    public CsvFile readOdsFile(File file) throws IOException {
        List<CsvSheet> sheets = new ArrayList<CsvSheet>();
        SpreadSheet fromFile = SpreadSheet.createFromFile(file);
        for (int s = 0; s < fromFile.getSheetCount(); s++) {
            Sheet sheet = fromFile.getSheet(s);
            if (!ignoredSheet.contains(sheet.getName())) {
                sheets.add(getSheetContent(sheet));
            }
        }
        return new CsvFile(file.getName(), sheets);
    }

    CsvSheet getSheetContent(Sheet sheet) {
        int columnCount = Math.min(sheet.getColumnCount(), MAX_COL);
        int rowCount = Math.min(sheet.getRowCount(), MAX_ROW);
        List<CsvRow> rows = new ArrayList<CsvRow>();
        for (int r = 0; r < rowCount; r++) {
            String[] row = new String[columnCount];
            for (int c = 0; c < columnCount; c++) {
                MutableCell<SpreadSheet> cell = sheet.getCellAt(c, r);
                String textValue = cell.getTextValue();
                row[c] = textValue;
            }
            rows.add(new CsvRow(csvRowDefinition, row));
        }
        return new CsvSheet(sheetDefinition, sheet.getName(), rows);
    }
}