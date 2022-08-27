package com.tuthan.pharma_tech_back.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class  User implements Serializable {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long idUser;
     private String nom;
     private  String prenom;
     private String username;
     @JsonProperty(access = JsonProperty.Access.READ_ONLY) //Pour que le password ne soit pas serialisé au format Json
     private String password;
     private boolean active;
     //Des que on charge un nouveau user on a les roles assosié
     @ManyToMany(fetch = EAGER)
     private Collection<Role> roles =new ArrayList<>();

}
