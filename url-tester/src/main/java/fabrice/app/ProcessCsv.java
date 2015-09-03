package fabrice.app;

import fabrice.app.csv.CsvFile;
import fabrice.app.csv.CsvFileParser;
import fabrice.app.csv.CsvSheetDefinition;
import fabrice.app.csv.HeaderRowDefinition;
import fabrice.app.csv.util.CsvUtil;
import fabrice.app.db.DbImporter;
import fabrice.app.db.OpenDb;
import fabrice.app.files.Directory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static fabrice.app.files.Predicats.hasExtension;
import static fabrice.app.files.Predicats.isFile;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class ProcessCsv {

    private CsvSheetDefinition sheetDefinition = new CsvSheetDefinition(new HeaderRowDefinition(4, CsvUtil.colNameToindex("B"), CsvUtil.colNameToindex("AC")), 9);
    private final CsvFileParser csvFileParser = new CsvFileParser(sheetDefinition);

    public static void main(String[] args) throws IOException {
        new ProcessCsv().job3();
    }

    private void job2() throws IOException {
        Directory directory = new Directory("/home/fabrice/datamining/opendata")
                .withPredicat(hasExtension(".ods"))
                .withPredicat(isFile());
        Collection<File> pathCollection = directory.getPathCollection();
        for (File file : pathCollection) {
            System.out.println(file.getPath());
        }
    }

    private void job3() throws IOException {
        CsvFile csvFile = csvFileParser.readOdsFile(new File("/home/fabrice/datamining/opendata/election_national/elections-mandatsPartisCantons.ods"));
        List<CsvFile> csvFiles = Arrays.asList(new CsvFile[]{csvFile});
        new DbImporter().importDb(new OpenDb(), csvFiles);
    }

    private void job() throws IOException {
        OpenDb openDb = new OpenDb();
        List<CsvFile> csvFiles = csvFileParser.getCsvFiles("/home/fabrice/datamining/opendata");
        DbImporter dbImporter = new DbImporter();
        dbImporter.importDb(openDb, csvFiles);
    }
}
