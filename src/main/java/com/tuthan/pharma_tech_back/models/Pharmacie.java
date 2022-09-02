package com.tuthan.pharma_tech_back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pharmacie")
public class Pharmacie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPharm;
    private String nomPharm;
    private  String localite;
    private Long longitude;
    private Long laltitude;
}
