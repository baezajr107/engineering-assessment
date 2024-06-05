package org.abaez.controllers;

import org.abaez.entities.FoodTruck;
import org.abaez.services.FoodTruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class FoodTruckController {

    @Autowired
    private FoodTruckService foodTruckService;

    @GetMapping("/food-trucks")
    public List<FoodTruck> getAllFoodTrucks(@RequestParam(required = false, defaultValue = "") String applicants,
                                            @RequestParam(required = false, defaultValue = "") String status,
                                            @RequestParam(required = false, defaultValue = "") String foodItems){
        return foodTruckService.findFoodTrucks(applicants,status,foodItems);
    }

    @GetMapping("/food-trucks/filters")
    public Map<String,List<String>> getFoodTruckFilters(){
        return foodTruckService.getFoodTruckFilters();
    }

    @GetMapping("/food-trucks/closest")
    public FoodTruck getClosestFoodTruckByAddress(@RequestParam String streetAddress,
                                                           @RequestParam String city,
                                                           @RequestParam String state,
                                                           @RequestParam String zipcode){
        return foodTruckService.getClosestFoodTruckByAddress(streetAddress,city,state,zipcode);
    }
}
