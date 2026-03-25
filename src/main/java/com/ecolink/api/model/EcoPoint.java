package com.ecolink.api.model;

import com.ecolink.api.model.embedded.OperatingHoursEntry;
import com.ecolink.api.model.enums.MaterialType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ecopoints")
public class EcoPoint {

	@Id
	private String id;

	private String name;
	private String address;

	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint coordinates;

	private String timezone;
	private List<MaterialType> acceptedMaterials;
	private String status;
	private Map<String, OperatingHoursEntry> operatingHours;
	private String phone;
	private List<String> photos;

	@Builder.Default
	private boolean isActive = true;

	@CreatedDate
	private Instant createdAt;

	@LastModifiedDate
	private Instant updatedAt;
}