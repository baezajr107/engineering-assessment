package org.abaez.utils;

import org.abaez.entities.FoodTruck;

import java.util.List;
import java.util.Map;

public class VectorUtils {

    public static FoodTruck findClosestToCoordinates(Map<String,Double> requestCoords, List<FoodTruck> foodTrucks){
        return foodTrucks.parallelStream()
            .min((o1, o2) -> {
                //vector math to find the point with the minimum distance to the provided point
                Double o1Distance = Math.sqrt(Math.pow((o1.getLatitude() - requestCoords.get("latitude")), 2)
                        + Math.pow((o1.getLongitude() - requestCoords.get("longitude")), 2));
                Double o2Distance = Math.sqrt(Math.pow((o2.getLatitude() - requestCoords.get("latitude")), 2)
                        + Math.pow((o2.getLongitude() - requestCoords.get("longitude")), 2));
                return o1Distance.compareTo(o2Distance);
            })
            .get();
    }
}
