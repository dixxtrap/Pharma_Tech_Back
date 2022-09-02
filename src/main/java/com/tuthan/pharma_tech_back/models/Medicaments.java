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
@Table(name="medic")
public class Medicaments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMedic;
    private String nomMedica;
    @ManyToOne
    private CategorieMedicaments categorieMedicaments;
}
