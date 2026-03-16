package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ecopoint;

@Repository

public interface RepositoryEcopointsFromDB extends MongoRepository<Ecopoint, String> {

    @Query("{ location: { $nearSphere: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: 50000 } } }")
    List<Ecopoint> findNearby(double longitude, double latitude, double maxDistance);
    }