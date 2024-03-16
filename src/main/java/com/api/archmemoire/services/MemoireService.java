package com.api.archmemoire.services;

import com.api.archmemoire.entities.*;
import com.api.archmemoire.exceptions.*;
import com.api.archmemoire.repositories.CategorieRepo;
import com.api.archmemoire.repositories.JuryRepo;
import com.api.archmemoire.repositories.KeyWorldsRepo;
import com.api.archmemoire.repositories.MemoireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoireService {

    private MemoireRepo memoireRepo;
    private EtudiantService etudiantService;
    private AnneeAcademiqueService anneeAcademiqueService;
    private DepartementService departementService;
    private ParcoursService parcoursService;
    private KeyWorldsRepo keyWorldsRepo;
    private CategorieRepo categorieRepo;
    private JuryRepo juryRepo;

    @Autowired
    public MemoireService(MemoireRepo memoireRepo, EtudiantService etudiantService, AnneeAcademiqueService anneeAcademiqueService, DepartementService departementService, ParcoursService parcoursService, KeyWorldsRepo keyWorldsRepo, CategorieRepo categorieRepo, JuryRepo juryRepo) {
        this.memoireRepo = memoireRepo;
        this.etudiantService = etudiantService;
        this.anneeAcademiqueService = anneeAcademiqueService;
        this.departementService = departementService;
        this.parcoursService = parcoursService;
        this.keyWorldsRepo = keyWorldsRepo;
        this.categorieRepo = categorieRepo;
        this.juryRepo = juryRepo;
    }

    public Memoire saveMemoire(Memoire memoire){
        if (memoire == null){
            throw new NotFoundException("Veuillez remplir correctement tous les champs");
        }
        if (memoire.getUrlFile() == null){
            throw new NotFoundException("Veuillez joindre le document");
        }
        if (memoire.getTheme() == null){
            throw new NotFoundException("Veuillez ajouter un theme de memoire");
        }
        if (memoire.getKeyWorlds().isEmpty()){
            throw new NotFoundException("Veuillez renseigner les mots cles de ce memoire");
        }
        if (memoire.getCategorie() == null){
            throw new NotFoundException("Veuillez renseigner une categorie pour ce memoire");
        }

        Memoire newMemoire = new Memoire();
        newMemoire.setTheme(memoire.getTheme());
        newMemoire.setActive(memoire.getActive());
        newMemoire.setCategorie(memoire.getCategorie());
        newMemoire.setUrlFile(memoire.getUrlFile());
        newMemoire.getJury()
                .addAll(memoire
                        .getJury()
                        .stream()
                        .map(jury -> {
                            Jury newJury = juryRepo.findById(jury.getId()).orElse(null);
                            newJury.getMemoires().add(newMemoire);
                            return newJury;
                        }).collect(Collectors.toList()));
        newMemoire.getKeyWorlds()
                .addAll(memoire
                        .getKeyWorlds()
                        .stream()
                        .map(keyWorlds -> {
                            KeyWorlds newKeyWorlds = keyWorldsRepo.findById(keyWorlds.getId()).orElse(null);
                            newKeyWorlds.getMemoires().add(newMemoire);
                            return newKeyWorlds;
                        }).collect(Collectors.toList()));

        return memoireRepo.save(newMemoire);
    }

    public Memoire getMemoireById(Long id){
        Memoire memoire = memoireRepo.findById(id).orElse(null);
        if (memoire == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        return memoire;
    }

    public Memoire editMemoire(Long id, Memoire memoire){
        Memoire memoireDB = memoireRepo.findById(id).orElse(null);
        if (memoireDB == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        memoireDB.setActive(memoire.getActive());
        memoireDB.setTelechargement(memoire.getTelechargement());
        memoireDB.setVue(memoire.getVue());
        memoireDB.setCategorie(memoire.getCategorie());
        memoireDB.setTheme(memoire.getTheme());
        memoireDB.setUrlFile(memoire.getUrlFile());
        memoireDB.setJury(memoire.getJury());
        memoireDB.setKeyWorlds(memoire.getKeyWorlds());

        return memoireRepo.save(memoireDB);
    }

    public List<Memoire> getAllMemoire(){
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return memoireRepo.findAll();
    }

    public List<Memoire> getListMemoireByKeyWorlds(String keyWorld){
        KeyWorlds keyWorlds = keyWorldsRepo.findByName(keyWorld);
        if (keyWorlds == null){
            throw new NotFoundException("Aucune entree avec cette valeur");
        }
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun memoire dans la base de donnee");
        }
        List<Memoire> memoireList = new ArrayList<>();
        for (Memoire memoire: memoireRepo.findAll()){
            if (memoire.getKeyWorlds().contains(keyWorlds)){
                memoireList.add(memoire);
            }
        }
        if (memoireList.isEmpty()){
            throw new NotFoundException("Aucun resultat avec ce mot cle");
        }
        return memoireList;
    }

    public List<Memoire> getListMemoireByEtudiant(String matricule){
        Etudiant etudiant = etudiantService.getByMatricule(matricule);
        if (etudiant == null){
            throw new NotFoundException("Aucun etudiant avec ce matricule");
        }
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun enregistrement de memoire dans la base de donnee");
        }
        List<Memoire> memoireList = new ArrayList<>();
        for (Memoire memoire : memoireRepo.findAll()){
            if (memoire.getEtudiant().getMatricule().equals(etudiant.getMatricule())){
                memoireList.add(memoire);
            }
        }
        if (memoireList.isEmpty()){
            throw new NotFoundException("Aucune publication pour cet etudiant");
        }
        return memoireList;
    }

    public List<Memoire> getListMemoireByParcours(String label, String anneeAca){
        Parcours parcours = parcoursService.getByLabel(label);
        AnneeAcademique anneeAcademique = anneeAcademiqueService.getByCode(anneeAca);
        if (parcours == null){
            throw new NotFoundException("Aucun objet avec le parcours: "+label +" dans la base de donnee");
        }
        if (anneeAcademique == null){
            throw new NotFoundException("Aucun objet avec le code: "+anneeAca +" dans la base de donnee");
        }

        List<Etudiant> etudiantList = etudiantService.getListEtudiantByParcours(label, anneeAcademique.getNumeroDebut());
        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun enregistrement pour ce parcours a cette annee");
        }
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucune publication de memoire n'a ete trouvee");
        }
        List<Memoire> memoireList = new ArrayList<>();
        for (Etudiant etudiant : etudiantList){
            for (Memoire memoire: memoireRepo.findAll()){
                if (memoire.getEtudiant().getMatricule().equals(etudiant.getMatricule())){
                    memoireList.add(memoire);
                }
            }
        }
        if (memoireList.isEmpty()){
            throw new NotFoundException("Aucun enregistrement pour ce parcours a cette annee");
        }
        return memoireList;
    }

    public List<Memoire> getListMemoireByDepartement(String code, String anneeAca){
        Departement departement = departementService.getByCode(code);
        AnneeAcademique anneeAcademique = anneeAcademiqueService.getByCode(anneeAca);
        if (departement == null){
            throw new NotFoundException("Aucun objet avec le code: "+code +" dans la base de donnee");
        }
        if (anneeAcademique == null){
            throw new NotFoundException("Aucun objet avec le code: "+anneeAca +" dans la base de donnee");
        }
        List<Etudiant> etudiantList = etudiantService.getEtudiantByDepartementAndAnnee(departement.getCode(), anneeAcademique.getNumeroDebut());
        if (etudiantList.isEmpty()){
            throw new NotFoundException("Aucun enregistrement pour ce parcours a cette annee");
        }
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucune publication de memoire n'a ete trouvee");
        }
        List<Memoire> memoireList = new ArrayList<>();
        for (Etudiant etudiant : etudiantList){
            for (Memoire memoire: memoireRepo.findAll()){
                if (memoire.getEtudiant().getMatricule().equals(etudiant.getMatricule())){
                    memoireList.add(memoire);
                }
            }
        }
        if (memoireList.isEmpty()){
            throw new NotFoundException("Aucun enregistrement pour ce parcours a cette annee");
        }
        return memoireList;
    }

    public List<Memoire> getListMemoireByCategorie(String label){
        Categorie categorie = categorieRepo.findByLabel(label);
        if (categorie == null){
            throw new NotFoundException("Aucune entree avec cette valeur");
        }
        if (memoireRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun memoire dans la base de donnee");
        }
        List<Memoire> memoireList = new ArrayList<>();
        for (Memoire memoire: memoireRepo.findAll()){
            Categorie cat = categorieRepo.findById(memoire.getCategorie().getId()).orElse(null);
            if (cat.getLabel().equals(categorie.getLabel())){
                memoireList.add(memoire);
            }
        }
        if (memoireList.isEmpty()){
            throw new NotFoundException("Aucun resultat avec ce mot cle");
        }
        return memoireList;
    }

    public String dropMemoire(Long id){
        Memoire memoire = memoireRepo.findById(id).orElse(null);
        if (memoire == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        memoire.setActive(false);
        memoireRepo.save(memoire);
        return "Desactivation reussi";
    }
}
