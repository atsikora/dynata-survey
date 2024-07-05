package hu.endox.demo.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.endox.demo.csv.CsvResolver;
import hu.endox.demo.model.ParticipationEntity;
import hu.endox.demo.util.CollectionUtil;

@Component
public class ParticipationRepository implements Repository<ParticipationEntity> {

    private Map<Long, List<ParticipationEntity>> surveyCache = null;
    private Map<Long, List<ParticipationEntity>> memberCache = null;

    public ParticipationRepository() {
        List<ParticipationEntity> participations = CsvResolver.resolveParticipationEntity();
        surveyCache = Collections.unmodifiableMap(participations.stream().collect(Collectors.groupingBy((p) -> p.getSurvey().getId())));
        memberCache = Collections.unmodifiableMap(participations.stream().collect(Collectors.groupingBy((p -> p.getMember().getId()))));
    }

    @Override
    public Optional<ParticipationEntity> findById(Long surveyId) {
        throw new UnsupportedOperationException();
    }

    public List<ParticipationEntity> findBySurveyId(Long surveyId) {
        return CollectionUtil.nullAsEmptyList(surveyCache.get(surveyId));
    }

    public List<ParticipationEntity> findByMemberId(Long memberId) {
        return CollectionUtil.nullAsEmptyList(memberCache.get(memberId));
    }

    @Override
    public Collection<ParticipationEntity> findAll() {
        return surveyCache.values().stream().flatMap(List::stream).toList();
    }
}
