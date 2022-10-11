package com.tuthan.pharma_tech_back.controller;

import com.tuthan.pharma_tech_back.models.ImagesModel;
import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.services.PharmacieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@CrossOrigin("*")
public class PharmacienController {
    @Autowired
    private PharmacieService pharmacieService;
    //Recuperer la liste de tous lees pharmacies
    @GetMapping("/pharmacies")
    public List<Pharmacie> getAllPharmciess() {
        System.out.println("Recevez tous les pharmacies...");
        return pharmacieService.getAllPharmcies();
    }


    //Ajout de Pharmacie
    @PostMapping(value = {"/addpharmcie"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Pharmacie createPharmcie(@RequestPart("pharmacie") Pharmacie pharmacie,
                               @RequestPart(value = "imageFile") MultipartFile[] file)  {
        try {
           Set<ImagesModel> images = uploadImage(file);
           pharmacie.setPharmacieImage(images);
           return pharmacieService.SavePharmacie(pharmacie);
        }catch (Exception e){
          System.out.println(e.getMessage());
          return null;
        }
    }

    //methode de téléchargement d'image
     public  Set<ImagesModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
         Set<ImagesModel> imagesModels=new HashSet<>();
         for(MultipartFile file:multipartFiles){
             ImagesModel imagesModel=new ImagesModel(
                     file.getOriginalFilename(),
                     file.getContentType(),
                     file.getBytes()
             );
         imagesModels.add(imagesModel);
         }
         return imagesModels;
     }
     @GetMapping("/getPharmacie")
    public Pharmacie getPharmcie(@PathVariable Long id){
        return  pharmacieService.getPharmacie(id);
    }

    //Rechercher par Nom
    @GetMapping("/rechercheNomPhar/{nomPharma}")
    public Pharmacie recherchePharmacie(@PathVariable String nomPharma) {
        return pharmacieService.getByNomPharmacie(nomPharma);
    }
}
