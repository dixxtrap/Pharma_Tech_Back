package com.tuthan.pharma_tech_back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cateMedic")
public class CategorieMedicaments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCate;
    private String categorie;
    @JsonIgnore
    @OneToMany(mappedBy = "categorieMedicaments")
    private List<Medicaments> medicaments ;
}
