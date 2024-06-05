package org.abaez.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name="food_trucks")
public class FoodTruck {

    @Id
    private String locationId;

    @Column
    private String applicant;
    @Column
    private String facilityType;
    @Column
    private Integer cnn;
    @Column
    private String LocationDescription;
    @Column
    private String address;
    @Column
    private String blocklot;
    @Column
    private String block;
    @Column
    private String lot;
    @Column
    private String permit;
    @Column
    private String status;
    @Column
    private String foodItems;
    @Column
    private Double x;
    @Column
    private Double y;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private String schedule;
    @Column
    private String dayshours;
    @Column
    private LocalDateTime approved;
    @Column
    private String received;
    @Column
    private Boolean priorPermit;
    @Column
    private LocalDateTime expirationDate;
}
