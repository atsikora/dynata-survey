package hu.endox.demo.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.endox.demo.csv.CsvResolver;
import hu.endox.demo.model.Survey;

@Component
public class SurveyRepository implements Repository<Survey> {

    private Map<Long, Survey> cache = null;

    public SurveyRepository() {
        List<Survey> surveys = CsvResolver.resolveSurveys();
        cache = Collections.unmodifiableMap(surveys.stream().collect(Collectors.toMap(Survey::getId, Function.identity())));
    }

    @Override
    public Optional<Survey> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    public List<Survey> findAllById(Collection<Long> ids) {
        return ids.stream().filter(id -> cache.get(id) != null).map(id -> cache.get(id)).toList();
    }

    @Override
    public Collection<Survey> findAll() {
        return cache.values();
    }
}
