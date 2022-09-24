package com.tuthan.pharma_tech_back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
    private Date dateCreationMedoc;
    private String photomedoc;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "medicaments_images",
            joinColumns = {
                    @JoinColumn(name = "id_mdeoc")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")
            }
    )
    private Set<ImagesModel> medocImage;
    @ManyToOne
    private CategorieMedicaments categorieMedicaments;
}
