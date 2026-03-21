package com.ecolink.api.service;

import java.util.List;

import com.ecolink.api.model.Ecopoint;

public interface LocationEcopointService {
    List<Ecopoint> findNearby(double longitude, double latitude, double maxDistance);
}