package com.example.Vuelos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FlightService {

    @Value("${aviation.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final String API_URL = "http://api.aviationstack.com/v1/flights";
    //private static final String API_URL = "http://api.aviationstack.com/v1/airlines";

    public FlightService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFlightSchedule(String airlineCode) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("access_key", apiKey)
                    .queryParam("airline_iata", airlineCode)
                    .queryParam("limit", 10)
                    .toUriString();

            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "Error al obtener los vuelos: " + e.getMessage();
        }
    }
}