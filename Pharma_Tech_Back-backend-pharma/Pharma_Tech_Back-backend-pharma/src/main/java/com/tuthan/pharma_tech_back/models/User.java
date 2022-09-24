package com.tuthan.pharma_tech_back.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class  User implements Serializable{
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nom;
     private  String prenom;
     @NotBlank
     private String username;
     @NotBlank
     @Email
     private String email;
     @JsonProperty(access = JsonProperty.Access.READ_ONLY) //Pour que le password ne soit pas serialisé au format Json
     @NotBlank
     @Size(max = 120)
     private String password;
     private boolean active;
     private Date dateCreation =new Date();
     //Des qu'on charge un nouveau user on a les roles assosié
    // @ManyToMany(fetch = EAGER)
     @ManyToMany(fetch = EAGER)
    @JoinTable(	name = "user_roles",
 joinColumns = @JoinColumn(name = "idUser"),
inverseJoinColumns = @JoinColumn(name = "idRole"))
     private Collection<Role> roles =new ArrayList<>();

     public User(String username, String email, String password,String nom,String prenom) {
          this.username = username;
          this.email = email;
          this.password = password;
          this.nom=nom;
          this.prenom=prenom;

     }
}
