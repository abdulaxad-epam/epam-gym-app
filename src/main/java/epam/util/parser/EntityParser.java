package epam.util.parser;

import epam.domain.User;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface EntityParser<T> {
    List<T> parse(List<Map<String, String>> data);
}
