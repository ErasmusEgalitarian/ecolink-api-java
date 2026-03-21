package com.ecolink.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecolink.api.model.Ecopoint;

public interface RepositoryEcopointsFromDB
        extends MongoRepository<Ecopoint, String>, RepositoryEcopointsFromDBCustom {
}