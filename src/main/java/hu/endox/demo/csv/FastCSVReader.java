package hu.endox.demo.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;

public final class FastCSVReader {

    private static final Logger LOG = LoggerFactory.getLogger(FastCSVReader.class);

    private FastCSVReader() {
        super();
    }

    public static List<CsvRecord> read(String fileName) {
        try (CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(getReader(fileName))) {
            return csv.stream().toList();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private static Reader getReader(final String name) throws IOException {
        return new InputStreamReader(new ClassPathResource(name).getInputStream(), StandardCharsets.UTF_8);
    }
}
