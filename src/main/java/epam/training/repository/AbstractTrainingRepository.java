package epam.training.repository;


import epam.shared.exception.exception.DateConversionException;
import epam.training.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractTrainingRepository {

    public abstract EntityManager entityManager();

    protected Optional<List<Training>> getTrainings(String username, String userRole, String periodFrom, String periodTo, Map<String, Object> additionalFilters) {

        EntityManager entityManager = entityManager();

        StringBuilder query = new StringBuilder("SELECT t FROM Training t JOIN t." + userRole + " tr JOIN tr.user u WHERE u.username = :username");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", username);

        try {
            if (periodFrom != null && periodTo != null) {
                query.append(" AND t.trainingDate BETWEEN :startDate AND :endDate");
                parameters.put("startDate", LocalDate.parse(periodFrom).atTime(0, 0, 0));
                parameters.put("endDate", LocalDate.parse(periodTo).atTime(0, 0, 0));
            } else if (periodFrom != null) {
                query.append(" AND t.trainingDate >= :startDate");
                parameters.put("startDate", LocalDate.parse(periodFrom).atTime(0, 0, 0));
            } else if (periodTo != null) {
                query.append(" AND t.trainingDate <= :endDate");
                parameters.put("endDate", LocalDate.parse(periodTo).atTime(0, 0, 0));
            }
        } catch (Exception e) {
            throw new DateConversionException("Invalid date format");
        }

        additionalFilters.forEach((key, value) -> {
            String paramName = key.replaceAll("[.]", "_");
            query.append(" AND ").append(key).append(" = :").append(paramName);
            parameters.put(paramName, value);
        });

        TypedQuery<Training> typedQuery = entityManager.createQuery(query.toString(), Training.class);

        parameters.forEach(typedQuery::setParameter);

        List<Training> results = typedQuery.getResultList();
        return Optional.of(results);
    }

}

