/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.FilterBlueprints;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.*;

import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    FilterBlueprints fpp;
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Set<String> getAllBlueprints(){
        Set<String> val = new HashSet<>();
        for(Blueprint b : bpp.getAllBlueprints()){
            val.add(b.toString());
        }
        return val;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return bpp.getBlueprint(author, name);
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> authorBlueprints = bpp.getBlueprintsByAuthor(author);
        return authorBlueprints;
    }

    public Blueprint FilterSuprimePoints(int blueprint) throws BlueprintPersistenceException {
        Set<Blueprint> authorBlueprints = bpp.getAllBlueprints();
        List<Blueprint> myList = new ArrayList<>(authorBlueprints);
        Blueprint blueprint1Filter = myList.get(blueprint);
        fpp.filterBlueprint(blueprint1Filter);
        return blueprint1Filter;
    }

    public void updateBlueprint(String author, String bpname, Blueprint updatedBlueprint) throws BlueprintNotFoundException, BlueprintPersistenceException {
        Blueprint existingBlueprint = getBlueprintsByAuthor(author)
                .stream()
                .filter(bp -> bp.getName().equals(bpname))
                .findFirst()
                .orElseThrow(() -> new BlueprintNotFoundException("Blueprint not found"));



        existingBlueprint.setPoints(updatedBlueprint.getPoints());
        existingBlueprint.setVersion(existingBlueprint.getVersion() + 1);

        saveBlueprint(existingBlueprint);
    }


    public void saveBlueprint(Blueprint blueprint) throws BlueprintPersistenceException {
        // Implementa la lógica para guardar el plano en tu repositorio
        bpp.updateBlueprints(blueprint);
    }
}
