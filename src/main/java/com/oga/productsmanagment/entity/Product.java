package com.oga.productsmanagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;


    @Column(name = "qte")
    private int qte;


    @Column(name = "disp")
    private boolean disponibilite;

    @Column(name = "creation_date")
    private LocalDate dateDeCreation;

    @Column(name = "modify_date")
    private LocalDate dateDeModification;


    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "category_id")
    private Category category;
    @Column(insertable=false, updatable=false)
    private Long category_id;

}
