package epam.util.parser;

import java.util.UUID;

@FunctionalInterface
public interface DataInserter<T> {
    void insert(UUID id, T entity);
}