package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Ecopoint;

import java.util.List;

@Repository
public interface RepositoryEcopoint {
	
		List<Ecopoint> findByClosestEcopoint(String closestEcopoint);

		Ecopoint create(Ecopoint ecopoint);

		Ecopoint save(Ecopoint ecopoint);

		List<Ecopoint> update(String EcopointID, Ecopoint ecopoint);	
	}