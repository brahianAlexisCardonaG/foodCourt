package com.project.foodCourt.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "phone",nullable = false, length = 13)
    private String phone;

    @Column(name = "logo_url",nullable = false)
    private String logoUrl;

    @Column(name = "nit",nullable = false, length = 50)
    private String nit;
}
