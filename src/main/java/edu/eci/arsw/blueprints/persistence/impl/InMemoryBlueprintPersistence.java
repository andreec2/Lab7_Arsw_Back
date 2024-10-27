/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * In-memory implementation of blueprint persistence.
 * 
 * This class stores blueprints in an in-memory data structure (a map) and
 * provides methods for saving, retrieving,
 * searching, and deleting blueprints. Implements the
 * {@link BlueprintsPersistence} interface.
 * 
 * The class is a Spring service, annotated with {@code @Service}.
 * 
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    /**
     * Constructor that initializes stub data.
     * 
     * Loads some example blueprints into the in-memory persistence map.
     */
    public InMemoryBlueprintPersistence() {
        // Load example data
        Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115),
                new Point(115, 115), new Point(140, 140), new Point(140, 140) };

        Point[] housePoints = new Point[] {
                new Point(100, 500), new Point(300, 500), new Point(300, 300),
                new Point(200, 200),new Point(100, 300)};

        Point[] randomPoints = new Point[] {
                new Point(100, 200), new Point(300, 400), new Point(0, 300),
                new Point(200, 350),new Point(127, 300)};

        Point[] juanPoints = new Point[] {
                new Point(100, 0), new Point(0, 500), new Point(300, 0),
                new Point(0, 200),new Point(100, 0)};

        Point[] lauraPoints = new Point[] {
                new Point(100, 50), new Point(70, 500), new Point(300, 120),
                new Point(20, 200),new Point(100, 0), new Point(30, 120),
                new Point(20, 0),new Point(100, 0), new Point(300, 120),
                new Point(250, 260),new Point(100, 180), new Point(30, 120),
                new Point(20, 123),new Point(100, 170)};

        //Creacion de planos
        Blueprint an = new Blueprint("andres", "MyPlane", pts);
        Blueprint an2 = new Blueprint("andres", "MyPlane2", housePoints);
        Blueprint an3 = new Blueprint("andres", "MyPlane3", randomPoints);
        Blueprint an4 = new Blueprint("andres", "MyPlane4", juanPoints);
        Blueprint an5 = new Blueprint("andres", "MyPlane5", lauraPoints);

        Blueprint jp = new Blueprint("juan", "MyPlane", juanPoints);
        Blueprint jp1 = new Blueprint("juan", "MyPlane1", lauraPoints);

        //Se agregan los blueprints a hash de blueprints
        blueprints.put(new Tuple<>(an.getAuthor(), an.getName()), an);
        blueprints.put(new Tuple<>(an2.getAuthor(), an2.getName()), an2);
        blueprints.put(new Tuple<>(an3.getAuthor(), an3.getName()), an3);
        blueprints.put(new Tuple<>(an4.getAuthor(), an4.getName()), an4);
        blueprints.put(new Tuple<>(an5.getAuthor(), an5.getName()), an5);

        //Mas autores xd
        blueprints.put(new Tuple<>(jp.getAuthor(), jp.getName()), jp);
        blueprints.put(new Tuple<>(jp1.getAuthor(), jp1.getName()), jp1);

    }

    /**
     * Saves a new blueprint.
     * 
     * If a blueprint with the same author and name already exists, a
     * {@link BlueprintPersistenceException} is thrown.
     * 
     * @param bp The blueprint to save.
     * @throws BlueprintPersistenceException If a blueprint with the same author and
     *                                       name already exists.
     */
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    /**
     * Retrieves a blueprint by its author and name.
     * 
     * If the blueprint is not found, a {@link BlueprintNotFoundException} is
     * thrown.
     * 
     * @param author     The author of the blueprint.
     * @param bprintname The name of the blueprint.
     * @return The corresponding blueprint.
     * @throws BlueprintNotFoundException If no blueprint is found for the given
     *                                    author and name.
     */
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bpt = blueprints.get(new Tuple<>(author, bprintname));
        if (bpt == null) {
            throw new BlueprintNotFoundException(
                    "Blueprint not found for author: " + author + " and name: " + bprintname);
        }
        return bpt;
    }

    /**
     * Retrieves all blueprints by a specific author.
     * 
     * If no blueprints are found for the author, a
     * {@link BlueprintNotFoundException} is thrown.
     * 
     * @param author The author whose blueprints are to be retrieved.
     * @return A set of blueprints for the specified author.
     * @throws BlueprintNotFoundException If no blueprints are found for the given
     *                                    author.
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();

        // Iterate over the map to find the author's blueprints
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey() != null && entry.getKey().first != null && entry.getKey().first.equals(author)) {
                authorBlueprints.add(entry.getValue());
            }
        }

        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }

        return authorBlueprints;
    }

    /**
     * Retrieves all stored blueprints.
     * 
     * @return A set of all blueprints.
     */
    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    @Override
    public void updateBlueprints(Blueprint bp) {
        blueprints.remove(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
    }

    /**
     * Deletes a blueprint by its author and name.
     * 
     * If the blueprint is not found, a {@link BlueprintNotFoundException} is
     * thrown.
     * 
     * @param author The author of the blueprint to delete.
     * @param name   The name of the blueprint to delete.
     * @throws BlueprintNotFoundException If no blueprint is found for the given
     *                                    author and name.
     */
    public void deleteBlueprint(String author, String name) throws BlueprintNotFoundException {
        Tuple<String, String> key = new Tuple<>(author, name);
        if (blueprints.remove(key) == null) {
            throw new BlueprintNotFoundException("Blueprint not found");
        }
    }

    @Override
    public void addBlueprintName(String author, String bpname, Blueprint blueprint) {

    }

}
