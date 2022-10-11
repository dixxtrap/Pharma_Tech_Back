package com.tuthan.pharma_tech_back.controller;

import com.tuthan.pharma_tech_back.jwtresponse.MessageResponse;
import com.tuthan.pharma_tech_back.models.ImagesModel;
import com.tuthan.pharma_tech_back.models.Medicaments;
import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.repository.MedicamentsRepository;
import com.tuthan.pharma_tech_back.services.MedicamentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@CrossOrigin("*")
public class MedicamentsController {
    private final MedicamentsService medicamentsService;
    private final MedicamentsRepository medicamentsRepository;

    @PostMapping(value = "/saveMedoc",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Medicaments saveMedic(@RequestPart("medicaments") Medicaments medicaments,
                                        @RequestPart("imageFile") MultipartFile [] file) {
      /*  if (medicamentsRepository.existsByNomMedica(medicaments.getNomMedica())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Erreur : le medicaments existe deja !"));
        }*/
        try {
            Set<ImagesModel> images = uploadImage(file);
            medicaments.setMedocImage(images);
            return  medicamentsService.SaveMedoc(medicaments);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
       //medicamentsRepository.save(medicaments);
        //return ResponseEntity.ok(new MessageResponse("Medicaments  enregistré avec succès!"));

    }
    //methode de téléchargement d'image
    public Set<ImagesModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
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
    @GetMapping(value = "/allMedoc")
    public List<Medicaments> getAllMedoc() {
        return medicamentsService.getAllMedicaments();
    }

    @PutMapping(value = "/modif")
    public Medicaments update(@RequestBody Medicaments medicaments) {
        Medicaments medicaments1 = new Medicaments();
        medicaments1.setNomMedica(medicaments.getNomMedica());
        medicaments1.setCategorieMedicaments(medicaments.getCategorieMedicaments());
        return medicamentsService.SaveMedoc(medicaments1);
    }

    //Rechercher par Nom Medicaments
    @GetMapping("/rechercheNomMedic/{nomMedoc}")
    public Medicaments rechercheMedic(@PathVariable String nomMedoc) {
        return medicamentsService.getByNomMedoc(nomMedoc);
    }
}
