package hu.endox.demo.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.endox.demo.csv.CsvResolver;
import hu.endox.demo.model.Participation;
import hu.endox.demo.util.CollectionUtil;

@Component
public class ParticipationRepository implements Repository<Participation> {

    private Map<Long, List<Participation>> surveyCache = null;
    private Map<Long, List<Participation>> memberCache = null;

    public ParticipationRepository() {
        List<Participation> participations = CsvResolver.resolveParticipations();
        surveyCache = Collections.unmodifiableMap(participations.stream().collect(Collectors.groupingBy(Participation::getSurveyId)));
        memberCache = Collections.unmodifiableMap(participations.stream().collect(Collectors.groupingBy(Participation::getMemberId)));
    }

    @Override
    public Optional<Participation> findById(Long surveyId) {
        throw new UnsupportedOperationException();
    }

    public List<Participation> findBySurveyId(Long surveyId) {
        return CollectionUtil.nullAsEmptyList(surveyCache.get(surveyId));
    }

    public List<Participation> findByMemberId(Long memberId) {
        return CollectionUtil.nullAsEmptyList(memberCache.get(memberId));
    }

    @Override
    public Collection<Participation> findAll() {
        return surveyCache.values().stream().flatMap(List::stream).toList();
    }
}
