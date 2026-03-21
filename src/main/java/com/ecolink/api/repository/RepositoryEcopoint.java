package com.ecolink.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecolink.api.model.Ecopoint;

@Repository
public interface RepositoryEcopoint {
	
		List<Ecopoint> findByClosestEcopoint(String closestEcopoint);

		Ecopoint create(Ecopoint ecopoint);

		Ecopoint save(Ecopoint ecopoint);

		List<Ecopoint> update(String EcopointID, Ecopoint ecopoint);	
	}