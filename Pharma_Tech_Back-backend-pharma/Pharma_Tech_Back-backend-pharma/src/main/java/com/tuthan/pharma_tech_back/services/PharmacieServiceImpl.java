package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.models.User;
import com.tuthan.pharma_tech_back.repository.PharmacieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PharmacieServiceImpl  implements PharmacieService{

    private final PharmacieRepository pharmacieRepository;
    @Override
    public Pharmacie SavePharmacie(Pharmacie pharmacie) {

        return pharmacieRepository.save(pharmacie);
    }

    @Override
    public Pharmacie getPharmacie(Long id) {
        return pharmacieRepository.findById(id).get();
    }

    @Override
    public List<Pharmacie> getAllPharmcies(){
        System.out.println("Recevez tous pharmacie...");
        return pharmacieRepository.findAll();
    }

    @Override
    public Pharmacie getByNomPharmacie(String nomPharma) {
        return pharmacieRepository.findByNomPharm(nomPharma);
    }

//    @Override
//    public Pharmacie getNomPage(String nomPharma) {
//        return pharmacieRepository.pharmacieByNomPharm(nomPharma);
//    }


}
