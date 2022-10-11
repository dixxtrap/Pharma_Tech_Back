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
@Table(name="pharmacie")
public class Pharmacie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPharm;
    private String nomPharm;
    private  String localite;
    private String longitude;
    private String laltitude;
    private String dateInsertion;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "pharmacie_images",
    joinColumns = {
            @JoinColumn(name = "id_pharm")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "image_id")
            }
    )
    private Set<ImagesModel> pharmacieImage;

    public Set<ImagesModel> getPharmacieImage() {
        return pharmacieImage;
    }

    public void setPharmacieImage(Set<ImagesModel> pharmacieImage) {
        this.pharmacieImage = pharmacieImage;
    }
}
