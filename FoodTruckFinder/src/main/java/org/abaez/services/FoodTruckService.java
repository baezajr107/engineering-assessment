package org.abaez.services;

import org.abaez.entities.FoodTruck;
import org.abaez.repositories.FoodTruckQuerySpecifications;
import org.abaez.repositories.FoodTruckRepository;
import org.abaez.utils.VectorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodTruckService {
    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private GeocodingService geocodingService;

    public List<FoodTruck> findFoodTrucks(String applicants, String status, String foodItems){
        //given the dynamic nature of this query, we build a dynamic query specification instead relying on
        //jpa's default repository query builder
        Specification<FoodTruck> spec = Specification.where(null);
        if(!applicants.isBlank()){
            spec = spec.and(FoodTruckQuerySpecifications.hasApplicant(Arrays.asList(applicants.split(","))));
        }
        if(!status.isBlank()){
            spec = spec.and(FoodTruckQuerySpecifications.hasStatus(Arrays.asList(status.split(","))));
        }
        if(!foodItems.isBlank()){
            spec = spec.and(FoodTruckQuerySpecifications.hasFoodItems(Arrays.asList(foodItems.split(","))));
        }
        //This will treat filters as AND's if they exist but disregard them if theyre empty
        return foodTruckRepository.findAll(spec);
    }

    public Map<String, List<String>> getFoodTruckFilters(){
        Map<String, List<String>> filterSets = new HashMap<String, List<String>>();
        filterSets.put("applicant",foodTruckRepository.findDistinctApplicant());
        filterSets.put("status",foodTruckRepository.findDistinctStatus());
        filterSets.put("foodItems",foodTruckRepository.findDistinctFoodItems());
/*
        I wanted this to basically be a way to filter based on any single type of item.
        If these weren't split, then the filters would end up being basically unique strings that only map
        1:1 to each food truck, so you might as well search by the truck name. This lets you find commonality
        between the trucks by getting a more varied result set at the cost of a slower and more costly query.
        The quality of the dataset also makes it come out a bit inconsistent.
*/
//        List<String> foodItemsUnified = foodTruckRepository.findDistinctFoodItems();
//
//        List<String> foodItemsSorted = foodItemsUnified.parallelStream()
//                .filter(Objects::nonNull)
//                .map(unsplitList -> {
//                    return List.of(unsplitList.toLowerCase().split(":"));
//                })
//                .flatMap(List::stream)
//                .map(String::trim)
//                .distinct()
//                .sorted()
//                .collect(Collectors.toList());
//        filterSets.put("foodItems",foodItemsSorted);

        return filterSets;
    }

    public FoodTruck getClosestFoodTruckByAddress(String streetAddress, String city, String state, String zipcode){
        Map<String,Double> addressCoordinates = geocodingService.convertAddressToCoords(streetAddress,city,state,zipcode);
        //get all food trucks minus the ones that don't have a valid location or aren't active
        List<FoodTruck> foodTrucks = foodTruckRepository.findAllByLatitudeNotAndStatusEquals(0.0,"APPROVED");
        return VectorUtils.findClosestToCoordinates(addressCoordinates,foodTrucks);

    }
}
