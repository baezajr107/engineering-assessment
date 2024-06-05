package org.abaez.services;

import org.abaez.entities.FoodTruck;
import org.abaez.repositories.FoodTruckRepository;
import org.abaez.utils.VectorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FoodTruckServiceTests {

    @InjectMocks
    FoodTruckService foodTruckService;

    @Mock
    private FoodTruckRepository foodTruckRepository;

    @Mock
    private GeocodingService geocodingService;

    @Mock
    private VectorUtils vectorUtils;

    @Mock
    Specification<FoodTruck> spec;

    @Test
    void findFoodTrucks_noParams(){
        Mockito.when(foodTruckRepository.findAll(Mockito.any())).thenReturn(List.of(new FoodTruck()));
        Mockito.when(spec.and(Mockito.any())).thenThrow(new MockitoException("Test failed"));
        List<FoodTruck> resultList = foodTruckService.findFoodTrucks("","","");
        Assertions.assertEquals(1, resultList.size());
    }

    @Test
    void getFoodTruckFilters(){
        Mockito.when(foodTruckRepository.findDistinctApplicant()).thenReturn(List.of("Food truck 1","Food truck 2"));
        Mockito.when(foodTruckRepository.findDistinctStatus()).thenReturn(List.of("APPROVED","EXPIRED","REJECTED"));
        Mockito.when(foodTruckRepository.findDistinctFoodItems()).thenReturn(List.of("Rocks"));
        Map<String,List<String>> resultList = foodTruckService.getFoodTruckFilters();
        Assertions.assertEquals(2,resultList.get("applicant").size());
        Assertions.assertEquals(3,resultList.get("status").size());
        Assertions.assertEquals(1,resultList.get("foodItems").size());
    }
}
