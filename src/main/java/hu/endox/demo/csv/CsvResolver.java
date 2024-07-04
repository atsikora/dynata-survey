package hu.endox.demo.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.converter.Converter;
import hu.endox.demo.converter.MemberConverter;
import hu.endox.demo.converter.ParticipationConverter;
import hu.endox.demo.converter.StatusConverter;
import hu.endox.demo.converter.SurveyConverter;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Status;
import hu.endox.demo.model.Survey;

public final class CsvResolver {

    private static final Logger LOG = LoggerFactory.getLogger(CsvResolver.class);

    private static List<Converter<?>> converters;

    private CsvResolver() {
    }

    static {
        converters = new ArrayList<>();
        converters.add(new MemberConverter());
        converters.add(new SurveyConverter());
        converters.add(new ParticipationConverter());
        converters.add(new StatusConverter());
    }

    private static <T> List<T> resolveCsvFile(String fileName, Class<T> clazz) {
        List<T> response = Collections.emptyList();
        List<CsvRecord> membersAsCsv = FastCSVReader.read(fileName);
        Optional<Converter<?>> converterO = converters.stream().filter(c -> c.support(clazz)).findFirst();
        if (converterO.isPresent()) {
            Converter<?> converter = converterO.get();
            response = (List<T>) membersAsCsv.subList(1, membersAsCsv.size()).stream().map(converter::convert).toList();
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Converter not found for class: %s", clazz.getName()));
            }
        }
        return response;
    }

    public static List<Member> resolveMembers() {
        return resolveCsvFile(CSVFiles.MEMBERS.getFileName(), Member.class);
    }

    public static List<Survey> resolveSurveys() {
        return resolveCsvFile(CSVFiles.SURVEYS.getFileName(), Survey.class);
    }

    public static List<Participation> resolveParticipations() {
        return resolveCsvFile(CSVFiles.PARTICIPATION.getFileName(), Participation.class);
    }

    public static List<Status> resolveStatuses() {
        return resolveCsvFile(CSVFiles.STATUSES.getFileName(), Status.class);
    }
}
