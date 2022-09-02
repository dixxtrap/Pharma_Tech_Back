package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.models.User;

import java.util.List;

public interface PharmacieService {
    Pharmacie SavePharmacie(Pharmacie pharmacie);
    Pharmacie getPharmacie(Long id);
    List<Pharmacie> getAllPharmcies();
    Pharmacie getByNomPharmacie(String nomPharma);
}
