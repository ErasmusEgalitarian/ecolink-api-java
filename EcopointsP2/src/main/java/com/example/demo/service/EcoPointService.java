package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EcoPointListItemDTO;
import com.example.demo.model.EcoPoint;
import com.example.demo.repository.EcoPointRepository;

@Service
public class EcoPointService {

    private final EcoPointRepository repository;

    public EcoPointService(EcoPointRepository repository) {
        this.repository = repository;
    }

    public Page<EcoPointListItemDTO> getEcoPoints(Double lat, Double lng, int page, int limit) { // henter alle aktive EcoPoints med paginering


        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("name").ascending());

        Page<EcoPoint> ecoPoints = repository.findAllByIsActiveTrue(pageable);

        return ecoPoints.map(ep -> {
            Double distanceKm = null;
            if (lat != null && lng != null) {
                distanceKm = calculateDistance(lat, lng, // bruger Haversine-formlen en matematisk formel til at beregne afstand mellem to GPS-koordinater
                        ep.getCoordinates().getY(),
                        ep.getCoordinates().getX());
            }
            return new EcoPointListItemDTO(
                    ep.getId(),
                    ep.getName(),
                    ep.getAddress(),
                    distanceKm,
                    ep.getAcceptedMaterials(),
                    ep.getStatus(),
                    ep.getPhotos() != null && !ep.getPhotos().isEmpty() ? ep.getPhotos().get(0) : null
            );
        });
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 100.0) / 100.0;
    }
} // EcoPointService.java henter EcoPoints fra databasen,
// beregner distancen til brugeren med Haversine-formlen, og pakker dem ind i en liste.
