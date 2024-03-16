package com.api.archmemoire.services;

import com.api.archmemoire.entities.*;
import com.api.archmemoire.exceptions.*;
import com.api.archmemoire.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtudiantService {

    private EtudiantRepo etudiantRepo;
    private InscriptionRepo inscriptionRepo;
    private ParcoursService parcoursService;
    private AnneeAcademiqueRepo anneeAcademiqueRepo;
    private DepartementRepo departementRepo;
    private OptionRepo optionRepo;

    @Autowired
    public EtudiantService(EtudiantRepo etudiantRepo, InscriptionRepo inscriptionRepo, ParcoursService parcoursService, AnneeAcademiqueRepo anneeAcademiqueRepo, DepartementRepo departementRepo, OptionRepo optionRepo) {
        this.etudiantRepo = etudiantRepo;
        this.inscriptionRepo = inscriptionRepo;
        this.parcoursService = parcoursService;
        this.anneeAcademiqueRepo = anneeAcademiqueRepo;
        this.departementRepo = departementRepo;
        this.optionRepo = optionRepo;
    }

    public List<Etudiant> getListEtudiantByParcours(String label, int year){

        List<Etudiant> etudiantList = new ArrayList<>();
        Parcours parcours = parcoursService.getByLabel(label);
        AnneeAcademique anneeAcademique = anneeAcademiqueRepo.findByNumeroDebut(year);

        if (parcours == null){
            throw new NotFoundException("Aucun parcours avec le label : "+label + " dans la base de donnee");
        }
        if (anneeAcademique == null){
            throw new NotFoundException("Aucune annee avec la valeur : "+year + " dans la base de donnee");
        }
        List<Inscription> inscriptions = inscriptionRepo.findAllByParcoursAndAnneeAcademiqueAndActive(parcours, anneeAcademique, true);
        if (inscriptions.isEmpty()){
            throw new NotFoundException("Aucune inscription active pour le parcours : "+label + " et l'annee : " + year + " dans la base de donnee");
        }
        for (Inscription inscription : inscriptions){
            if (inscription.getEtudiant().getActive().equals(true)){
                etudiantList.add(inscription.getEtudiant());
            }
        }

        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun etudiant inscrit au parcours : "+label + " au cours de l'annee : "+year);
        }
        return etudiantList;
    }

    public List<Etudiant> getEtudiantByDepartement(String code){
        List<Etudiant> etudiantList = new ArrayList<>();
        Departement departement = departementRepo.findByCode(code).orElse(null);
        if (departement == null){
            throw new NotFoundException("Aucun departement avec le code : "+code + " dans la base de donnee");
        }
        if (inscriptionRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucune inscription effective pour ce departement");
        }
        for (Inscription inscription: inscriptionRepo.findAll()){
            Parcours parcours = parcoursService.getById(inscription.getParcours().getId());
            Option option = optionRepo.findById(parcours.getOption().getId()).get();
            Departement depart = departementRepo.findById(option.getDepartement().getId()).get();

            if (depart.getCode().equals(departement.getCode())){
                Etudiant etudiant = etudiantRepo.findById(inscription.getEtudiant().getId()).get();
                etudiantList.add(etudiant);
            }
        }
        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun etudiant inscrit dans ce departement");
        }
        return etudiantList;
    }

    public Etudiant addEtudiant(Etudiant etudiant) {
        if (etudiant == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        Etudiant updateEtudiant = etudiantRepo.save(etudiant);
        if (updateEtudiant.getMatricule() != null){
            return updateEtudiant;
        }
        MatriculeService matriculeService = new MatriculeService();
        String matricule = matriculeService.matriculeGenerator(updateEtudiant.getId());
        updateEtudiant.setMatricule(matricule);
        return etudiantRepo.save(updateEtudiant);
    }

    public List<Etudiant> getAll() {
        List<Etudiant> list = etudiantRepo.findAll();
        if (list == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return list;
    }

    public Etudiant getById(Long id) {
        Etudiant etudiant = etudiantRepo.findById(id).orElse(null);
        if (etudiant == null){
            throw new NotFoundException("Aucun etudiant avec l'id : "+id + " dans la base de donnee");
        }
        return etudiant;
    }

    public Etudiant getByMatricule(String matricule) {
        Etudiant etudiant = etudiantRepo.findByMatricule(matricule).orElse(null);
        if (etudiant == null){
            throw new NotFoundException("Aucun etudiant avec le matricule : "+matricule + " dans la base de donnee");
        }
        return etudiant;
    }

    public Etudiant updateById(Long id, Etudiant etudiant) {
        Etudiant update = etudiantRepo.findById(id).orElse(null);
        if (update == null){
            throw new NotFoundException("Aucun etudiant avec l'id : "+id + " dans la base de donnee");
        }
        update.setEmail(etudiant.getEmail());
        update.setGenre(etudiant.getGenre());
        update.setMatricule(etudiant.getMatricule());
        update.setNom(etudiant.getNom());
        update.setDateDeNaissance(etudiant.getDateDeNaissance());
        update.setLieuDeNaissance(etudiant.getLieuDeNaissance());
        update.setNumeroTelephone(etudiant.getNumeroTelephone());
        update.setRegion(etudiant.getRegion());
        update.setType(etudiant.getType());

        return etudiantRepo.save(update);
    }

    public String delete(Long id) {
        Etudiant etudiant = getById(id);
        if (etudiant == null){
            throw new NotFoundException("Aucun etudiant avec l'id : "+id + " dans la base de donnee");
        }
        etudiant.setActive(false);
        etudiantRepo.save(etudiant);

        return "Operation reussi avec succes";
    }

    public List<Etudiant> getEtudiantByDepartementAndAnnee(String code, int year) {
        Departement departement = departementRepo.findByCode(code).orElse(null);
        AnneeAcademique anneeAcademique = anneeAcademiqueRepo.findByNumeroDebut(year);
        if (departement == null){
            throw new NotFoundException("Aucun departement avec le code : "+code + " dans la base de donnee");
        }

        if (anneeAcademique == null){
            throw new NotFoundException("Aucune annee avec cette valeur : "+year + " dans la base de donnee");
        }

        List<Parcours> parcoursList = parcoursService.getAllByDepartement(departement.getCode());
        if (parcoursList.isEmpty()){
            throw new NotFoundException("Aucun parcours pour le departement de code : "+code + " dans la base de donnee");
        }
        List<Etudiant> etudiantList = new ArrayList<>();
        for (Parcours parcours : parcoursList){
            if (getListEtudiantByParcours(parcours.getLabel(), anneeAcademique.getNumeroDebut()) != null){
                etudiantList.addAll(getListEtudiantByParcours(parcours.getLabel(), anneeAcademique.getNumeroDebut()));
            }
        }
        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun etudiant inscrit a ce departement pour l'annee specifier dans la base de donnee");
        }
        return etudiantList;
    }

    public List<Etudiant> getAllByAnnee(int year) {
        AnneeAcademique anneeAcademique = anneeAcademiqueRepo.findByNumeroDebut(year);
        if (anneeAcademique == null){
            throw new NotFoundException("Aucune annee avec la valeur : "+year + " dans la base de donnee");
        }
        List<Inscription> inscriptions = inscriptionRepo.findAllByAnneeAcademique(anneeAcademique);
        if (inscriptions == null){
            throw new NotFoundException("Aucune inscription pour l'annee : "+year + " dans la base de donnee");
        }
        List<Etudiant> etudiantList = new ArrayList<>();
        for (Inscription inscription : inscriptions){
            etudiantList.add(inscription.getEtudiant());
        }
        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun etudiant inscrit a l'annee : "+year + " dans la base de donnee");
        }
        return etudiantList;
    }

    public List<Etudiant> getListEtudiantByParcoursAndActive(String label, Integer numeroDebut) {
        List<Etudiant> etudiantList = new ArrayList<>();
        Parcours parcours = parcoursService.getByLabel(label);
        AnneeAcademique anneeAcademique = anneeAcademiqueRepo.findByNumeroDebut(numeroDebut);

        if (parcours == null){
            throw new NotFoundException("Aucun parcours avec le label : "+label + " dans la base de donnee");
        }

        if (anneeAcademique == null){
            throw new NotFoundException("Aucune annee avec la valeur : "+numeroDebut + " dans la base de donnee");
        }
        List<Inscription> inscriptions = inscriptionRepo.findAllByParcoursAndAnneeAcademiqueAndActive(parcours, anneeAcademique, true);
        if (inscriptions.isEmpty()){
            throw new NotFoundException("Aucune inscription active pour l'annee : "+numeroDebut + " et le parcours : "+ label+ " dans la base de donnee");
        }
        for (Inscription inscription : inscriptions){
            if (inscription.getEtudiant().getActive().equals(true)){
                etudiantList.add(inscription.getEtudiant());
            }
        }

        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun etudiant inscrit a ce parocurs et l'annee specifier dans la base de donnee");
        }
        return etudiantList;
    }
}
