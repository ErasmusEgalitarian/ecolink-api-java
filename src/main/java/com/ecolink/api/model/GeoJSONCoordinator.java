package com.ecolink.api.model;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class GeoJSONCoordinator {

    private final String geoJson; // final bruger vi når man ikke skal ændre værdien igen.

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)  // bruger vi til vores nearsphere 2d
    private final GPSLocations location;

    public GeoJSONCoordinator(String geoJson, GPSLocations location) { // constructor 
        this.geoJson = geoJson;
        this.location = location;
    }

    public String getGeoJson() { // getters
        return geoJson;
    }

    public GPSLocations getLocation() {
        return location;
    }
}