package org.abaez.repositories;

import org.abaez.entities.FoodTruck;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FoodTruckQuerySpecifications {

    public static Specification<FoodTruck> hasApplicant(List<String> applicantFilter) {
        return (root, query, criteriaBuilder) ->
                root.get("applicant").in(applicantFilter);
    }

    public static Specification<FoodTruck> hasStatus(List<String> statusFilter) {
        return (root, query, criteriaBuilder) ->
                root.get("status").in(statusFilter);
    }

    public static Specification<FoodTruck> hasFoodItems(List<String> foodItemFilter) {
        return (root, query, criteriaBuilder) ->
                root.get("foodItems").in(foodItemFilter);
    }
}
