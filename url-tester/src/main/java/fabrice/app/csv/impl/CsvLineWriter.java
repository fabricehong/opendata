package fabrice.app.csv.impl;

import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by fabrice on 20.05.15.
 */
public class CsvLineWriter {

    private CSVPrinter csvPrinter;
    Writer writer;

    public static String toString(String[] line) {
        Writer writer = new StringWriter();
        CsvLineWriter csvLineWriter = new CsvLineWriter(writer);
        csvLineWriter.write(line);
        String str = writer.toString();
        csvLineWriter.close();
        return str;
    }

    public CsvLineWriter(Writer writer) {
        this.writer = writer;
        CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';');
        try {
            csvPrinter = new CSVPrinter(writer, csvFormat);
        } catch (IOException e) {
            throw new RuntimeException("Error during initialization of csv writer");
        }
    }

    public void write(String[] csvLine) {
        try {
            csvPrinter.printRecord(csvLine);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Impossible to write row %s", csvLine), e);
        }
    }

    public void close() {
        try {
            csvPrinter.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while closing csv stream");
        }
    }
}
