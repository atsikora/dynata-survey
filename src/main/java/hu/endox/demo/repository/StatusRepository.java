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
import hu.endox.demo.model.Status;

@Component
public class StatusRepository implements Repository<Status> {

    private Map<Long, Status> cache = null;

    public StatusRepository() {
        List<Status> surveys = CsvResolver.resolveStatuses();
        cache = Collections.unmodifiableMap(surveys.stream().collect(Collectors.toMap(Status::getId, Function.identity())));
    }

    @Override
    public Optional<Status> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Collection<Status> findAll() {
        return cache.values();
    }

}
