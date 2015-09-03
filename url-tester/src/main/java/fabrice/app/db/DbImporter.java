package fabrice.app.db;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import fabrice.app.csv.CsvFile;
import fabrice.app.csv.CsvHeader;
import fabrice.app.csv.CsvRow;
import fabrice.app.csv.CsvSheet;
import fabrice.app.csv.util.SqlUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DbImporter {

    public void importDb(OpenDb openDb, List<CsvFile> csvFiles) {
        for (CsvFile file : csvFiles) {
            for (CsvSheet sheet : file.getSheets()) {
                SqlTable table = new SqlTable(determineTableName(file, sheet), createColumnsFromSheet(sheet));
                openDb.createTable(table);
                for (CsvRow row : sheet.getDataRows()) {
                    openDb.insertInTable(table, row.getDataRow());
                }
            }
        }
    }

    private Collection<SqlColumn> createColumnsFromSheet(CsvSheet sheet) {
        return Collections2.transform(Arrays.asList(sheet.getHeaders()), new Function<CsvHeader, SqlColumn>() {
            @Override
            public SqlColumn apply(CsvHeader csvHeader) {
                return new SqlColumn(csvHeader.getNormalizedName(), SqlType.INTEGER, true);
            }
        });
    }


    private String determineTableName(CsvFile file, CsvSheet sheet) {
        return SqlUtils.toSqlName(removeExtension(file) + "_" + sheet.getName());
    }

    private String removeExtension(CsvFile file) {

        String name = file.getName();
        int i = name.lastIndexOf('.');
        if (i!=-1) {
            return name.substring(0, i);
        }
        return name;
    }
}