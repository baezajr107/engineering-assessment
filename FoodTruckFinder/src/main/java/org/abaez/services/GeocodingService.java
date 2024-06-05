package org.abaez.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeocodingService {
    //pull API key from environment variables
    @Value("${GOOGLE_API_KEY}")
    private String apiKey;

    //make the request to the Google Geocoding API
    public Map<String,Double> convertAddressToCoords(String streetAddress, String city, String state, String zipcode) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        String addressContatenated = "%s %s, %s %s".formatted(streetAddress, city, state, zipcode);

        GeocodingResult[] response = null;
        try {
            response = GeocodingApi.geocode(context,
                    addressContatenated).await();
        } catch (ApiException|InterruptedException|IOException e) {
            throw new RuntimeException(e);
        }
        //extract and return the resulting coordinates
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String,Double> outputCoords = new HashMap<>();
        outputCoords.put("latitude",response[0].geometry.location.lat);
        outputCoords.put("longitude",response[0].geometry.location.lng);
        context.shutdown();
        return outputCoords;
    }
}
