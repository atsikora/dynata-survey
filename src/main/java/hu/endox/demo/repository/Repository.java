package hu.endox.demo.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> findById(Long id);

    Collection<T> findAll();
}
