package com.ecolink.api.repository;

import com.ecolink.api.model.Ecopoint;
import com.ecolink.api.model.enums.MaterialType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcopointRepository extends MongoRepository<Ecopoint, String> {

	// US-033
	Optional<Ecopoint> findByIdAndIsActiveTrue(String id);

	// US-030
	Page<Ecopoint> findAllByIsActiveTrue(Pageable pageable);

	// US-032
	Page<Ecopoint> findByIsActiveTrueAndNameContainingIgnoreCaseOrIsActiveTrueAndAddressContainingIgnoreCase(
			String name, String address, Pageable pageable);

	// US-035
	Page<Ecopoint> findByIsActiveTrueAndAcceptedMaterialsContaining(
			MaterialType material, Pageable pageable);
}