package hu.endox.demo.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import hu.endox.demo.model.ParticipationEntity;
import hu.endox.demo.model.Status;
import hu.endox.demo.model.Survey;

public final class CsvResolver {

    private static final Logger LOG = LoggerFactory.getLogger(CsvResolver.class);

    private static final List<Converter<?>> CONVERTERS;

    private CsvResolver() {
    }

    static {
        CONVERTERS = new ArrayList<>();
        CONVERTERS.add(new MemberConverter());
        CONVERTERS.add(new SurveyConverter());
        CONVERTERS.add(new ParticipationConverter());
        CONVERTERS.add(new StatusConverter());
    }

    private static <T> List<T> resolveCsvFile(String fileName, Class<T> clazz) {
        List<T> response = Collections.emptyList();
        List<CsvRecord> records = FastCSVReader.read(fileName);
        Optional<Converter<?>> converterO = CONVERTERS.stream().filter(c -> c.support(clazz)).findFirst();
        if (converterO.isPresent()) {
            Converter<?> converter = converterO.get();
            response = (List<T>) records.subList(1, records.size()).stream().map(converter::convert).toList();
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Converter not found for class: %s", clazz.getName()));
            }
        }
        return response;
    }
    
    public static List<ParticipationEntity> resolveParticipationEntity() {
    	List<Member> members = resolveMembers();
    	List<Survey> surveys = resolveSurveys();
    	List<Status> statuses = resolveStatuses();
    	List<Participation> participations = resolveParticipations();
    	
    	Map<Long, Member> memberMap = members.stream().collect(Collectors.toMap(Member::getId, Function.identity()));
    	Map<Long, Survey> surveyMap = surveys.stream().collect(Collectors.toMap(Survey::getId, Function.identity()));
    	Map<Long, Status> statusMap = statuses.stream().collect(Collectors.toMap(Status::getId, Function.identity()));
    	
    	return participations.stream().map(p -> create(p, memberMap.get(p.getMemberId()), surveyMap.get(p.getSurveyId()), statusMap.get(p.getStatus()))).toList();
    }
    
    private static ParticipationEntity create(Participation participation, Member member, Survey survey, Status status) {
    	return new ParticipationEntity(member, survey, status, participation.getLength());
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
