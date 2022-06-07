package helper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CsvHelper {

    static CSVFormat CSV_DEFAULT_FORMAT = CSVFormat.DEFAULT.withHeader();
    static CSVFormat TSV_DEFAULT_FORMAT = CSVFormat.DEFAULT.withHeader().withDelimiter('\t');

    public static CSVParser getCSVParser(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), CSV_DEFAULT_FORMAT);
    }

    public static CSVParser getCSVParserSJIS(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), "SJIS"), CSV_DEFAULT_FORMAT);
    }

    public static CSVParser getCSVParserMS932(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), "MS932"), CSV_DEFAULT_FORMAT);
    }

    public static CSVParser getCSVParserWithoutHeader(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), CSVFormat.DEFAULT);
    }

    public static CSVParser getTSVParser(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), TSV_DEFAULT_FORMAT);
    }

    public static CSVParser getEXCELParser(String filePath) throws IOException {
        return new CSVParser(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), CSV_DEFAULT_FORMAT);
    }

    public static CSVParser getParser(String filePath, String fileType) throws IOException {
        switch (fileType) {
            case "csv":
                return getCSVParser(filePath);
            case "tsv":
                return getTSVParser(filePath);
            case "xlsx":
                return getEXCELParser(filePath);
            default:
                return null;
        }
    }

    public static CSVPrinter getCSVPrinter(String outputFileName) throws IOException {
        BufferedWriter companyMasterWriter = Files.newBufferedWriter(Paths.get(outputFileName), StandardCharsets.UTF_8);
        return new CSVPrinter(companyMasterWriter, CSVFormat.DEFAULT);
    }

    public static CSVPrinter getCSVPrinter(String outputFileName, boolean append) throws IOException {
        BufferedWriter companyMasterWriter = append ?
                Files.newBufferedWriter(Paths.get(outputFileName), StandardOpenOption.APPEND,
                        StandardOpenOption.CREATE) : Files.newBufferedWriter(Paths.get(outputFileName), StandardCharsets.UTF_8);
        return new CSVPrinter(companyMasterWriter, CSVFormat.DEFAULT);
    }

    public static CSVPrinter getCSVPrinterSJIS(String outputFileName) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(outputFileName)), "SJIS");
        BufferedWriter bw = new BufferedWriter(osw);
        return new CSVPrinter(bw, CSVFormat.DEFAULT);
    }

    public static CSVPrinter getPrinter(String outputFileName, String fileType, List<String> headerList) throws IOException {
        CSVFormat format;
        switch (fileType) {
            case "csv":
                format = CSVFormat.DEFAULT;
                break;
            case "tsv":
                format = CSVFormat.DEFAULT.withDelimiter('\t');
                break;
            default:
                format = CSVFormat.DEFAULT;
        }
        BufferedWriter companyMasterWriter = Files.newBufferedWriter(Paths.get(outputFileName), StandardCharsets.UTF_8);
        return new CSVPrinter(companyMasterWriter, format
                .withHeader(headerList.toArray(new String[0])));
    }
}
