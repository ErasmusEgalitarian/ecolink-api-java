package com.ecolink.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import com.ecolink.api.dto.EcoPointListItemDTO;
import com.ecolink.api.model.EcoPoint;
import com.ecolink.api.repository.EcoPointRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcoPointService {

    private final EcoPointRepository repository;
    private final MongoTemplate mongoTemplate;

    public EcoPointService(EcoPointRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<EcoPointListItemDTO> getEcoPoints(Double lat, Double lng, int page, int limit) { // henter alle aktive EcoPoints med paginering

        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("name").ascending());

        if (lat != null && lng != null) {
            NearQuery nearQuery = NearQuery
                    .near(new Point(lng, lat), Metrics.KILOMETERS)
                    .spherical(true)
                    .limit(limit)
                    .skip((long)(page - 1) * limit);

            GeoResults<EcoPoint> results = mongoTemplate.geoNear(nearQuery, EcoPoint.class);

            List<EcoPointListItemDTO> items = new ArrayList<>();
            for (GeoResult<EcoPoint> geoResult : results.getContent()) {
                EcoPoint ep = geoResult.getContent();
                double distanceKm = geoResult.getDistance().getValue();
                items.add(new EcoPointListItemDTO(
                        ep.getId(),
                        ep.getName(),
                        ep.getAddress(),
                        distanceKm,
                        ep.getAcceptedMaterials(),
                        ep.getStatus(),
                        ep.getPhotos() != null && !ep.getPhotos().isEmpty() ? ep.getPhotos().get(0) : null
                ));
            }

            long total = results.getContent().size();
            return new PageImpl<>(items, PageRequest.of(page - 1, limit), total);
        }

        else {
            Page<EcoPoint> ecoPoints = repository.findAllByIsActiveTrue(pageable);
            return ecoPoints.map(ep -> new EcoPointListItemDTO(
                    ep.getId(),
                    ep.getName(),
                    ep.getAddress(),
                    null, // ingen distance
                    ep.getAcceptedMaterials(),
                    ep.getStatus(),
                    ep.getPhotos() != null && !ep.getPhotos().isEmpty() ? ep.getPhotos().get(0) : null
            ));
        }
    }
} // EcoPointService.java henter EcoPoints fra databasen,
// beregner distancen til brugeren med Haversine-formlen, og pakker dem ind i en liste.
