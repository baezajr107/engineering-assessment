package org.abaez.repositories;

import org.abaez.entities.FoodTruck;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface FoodTruckRepository extends ListCrudRepository<FoodTruck, Integer>, JpaSpecificationExecutor<FoodTruck> {

    @Query("SELECT DISTINCT ft.applicant FROM FoodTruck ft")
    List<String> findDistinctApplicant();

    @Query("SELECT DISTINCT ft.status FROM FoodTruck ft")
    List<String> findDistinctStatus();

    @Query("SELECT DISTINCT ft.foodItems FROM FoodTruck ft")
    List<String> findDistinctFoodItems();

    List<FoodTruck> findAllByLatitudeNotAndStatusEquals(Double invalid, String status);
}
