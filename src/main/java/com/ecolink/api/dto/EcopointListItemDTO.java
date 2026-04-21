package com.ecolink.api.dto;

import java.util.List;

import com.ecolink.api.model.AcceptedMaterial;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="A DTO that explains how a Ecopoint is showed on a list")
public class EcopointListItemDTO {
    @Schema(description="Gives the Ecopoint a random ID", example="2h1gd1132h1h31")   
    private String id;
    @Schema(description="Gives the ecopoint the name of the ecopoint", example="Ecopoint UNB 1")
    private String name;
    @Schema(description="Gives the adress of the ecopoint", example="A.C meyers vænge 3")
    private String address;
    @Schema(description="Tell how far the ecopoint is away i km", example="1.4 km")
    private Double distanceKm;
    @Schema(description="Gives a list of what materials the ecopoint accepts", example="Aluminium and plastic")
    private List<AcceptedMaterial> acceptedMaterials;
    @Schema(description="tells if the ecopoint is under maintaince or not")
    private String status;
    @Schema(description="URL to a thumbmail picture")
    private String thumbnailUrl;

    public EcopointListItemDTO(String id, String name, String address,
                               Double distanceKm, List<AcceptedMaterial> acceptedMaterials,
                               String status, String thumbnailUrl) {
        this.id = id; // unikt ID på EcoPoint
        this.name = name; // navn fx "UNB Ecopoint 1"
        this.address = address; // adresse
        this.distanceKm = distanceKm; // Hvor langt brugeren er fra dette EcoPoint i km
        this.acceptedMaterials = acceptedMaterials; // liste af materialer fx Aluminium, Plastic
        this.status = status; // om den er fuld eller ej
        this.thumbnailUrl = thumbnailUrl; // billedlink
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Double getDistanceKm() { return distanceKm; }
    public List<AcceptedMaterial> getAcceptedMaterials() { return acceptedMaterials; }
    public String getStatus() { return status; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}

// EcoPointListItemDTO.java definerer praecis hvilke felter der sendes tilbage til appen som svar,
// fx navn, adresse og distanceKm.
