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
import hu.endox.demo.model.Member;

@Component
public class MemberRepository implements Repository<Member> {

    private Map<Long, Member> cache = null;

    public MemberRepository() {
        List<Member> members = CsvResolver.resolveMembers();
        cache = Collections.unmodifiableMap(members.stream().collect(Collectors.toMap(Member::getMemberId, Function.identity())));
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    public List<Member> findAllById(Collection<Long> ids) {
        //TODO null check, and what to do if null.
        return ids.stream().map(id -> cache.get(id)).toList();
    }

    @Override
    public Collection<Member> findAll() {
        return cache.values();
    }

    public Collection<Member> findAllActive() {
        return cache.values().stream().filter(m -> Boolean.TRUE.equals(m.getIsActive())).toList();
    }

}
