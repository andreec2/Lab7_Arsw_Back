package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for the InMemoryBlueprintPersistence class.
 * 
 * This class contains tests for CRUD operations on blueprints stored in memory
 * using the {@link InMemoryBlueprintPersistence} implementation.
 * It verifies the correct behavior of saving, retrieving, deleting, and
 * handling errors for blueprints.
 * 
 * Tests include:
 * - Saving and loading blueprints
 * - Handling of attempts to save duplicate blueprints
 * - Retrieving blueprints by author
 * - Deleting blueprints
 * - Handling requests for non-existent blueprints
 * 
 * Each test ensures that the persistence layer correctly implements the
 * expected behavior and handles exceptions as intended.
 */
public class InMemoryPersistenceTest {

    private InMemoryBlueprintPersistence ibpp;

    /**
     * Sets up the test environment by initializing an instance of
     * InMemoryBlueprintPersistence.
     */
    @Before
    public void setUp() {
        ibpp = new InMemoryBlueprintPersistence();
    }

    /**
     * Tests saving a new blueprint and loading it to ensure it was stored
     * correctly.
     * 
     * @throws BlueprintPersistenceException If there is an issue with saving the
     *                                       blueprint.
     * @throws BlueprintNotFoundException    If the blueprint cannot be found after
     *                                       saving.
     */
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Point[] pts0 = new Point[] { new Point(40, 40), new Point(15, 15) };
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[] { new Point(0, 0), new Point(10, 10) };
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.",
                ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        assertEquals("Loading a previously stored blueprint returned a different blueprint.", bp,
                ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
    }

    /**
     * Tests saving a blueprint with an existing name and author to ensure it throws
     * an exception.
     * 
     * @throws BlueprintPersistenceException If the first blueprint cannot be saved.
     */
    @Test
    public void saveExistingBpTest() {
        Point[] pts = new Point[] { new Point(0, 0), new Point(10, 10) };
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[] { new Point(10, 10), new Point(20, 20) };
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and author");
        } catch (BlueprintPersistenceException ex) {
            // Expected exception
        }
    }

    /**
     * Tests retrieving a blueprint by author and name to ensure it matches the
     * saved blueprint.
     * 
     * @throws BlueprintNotFoundException    If the blueprint cannot be found.
     * @throws BlueprintPersistenceException If there is an issue with retrieving
     *                                       the blueprint.
     */
    @Test
    public void shouldGetBlueprint() throws BlueprintNotFoundException, BlueprintPersistenceException {
        Blueprint expectedBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(10, 10), new Point(20, 20) });
        ibpp.saveBlueprint(expectedBlueprint);

        Blueprint retrievedBlueprint = ibpp.getBlueprint("John", "HousePlan");

        assertEquals("The retrieved blueprint should be equal to the expected blueprint", expectedBlueprint,
                retrievedBlueprint);
    }

    /**
     * Tests retrieving all blueprints by a specific author.
     * 
     * @throws BlueprintPersistenceException If there is an issue with retrieving
     *                                       the blueprints.
     * @throws BlueprintNotFoundException    If no blueprints are found for the
     *                                       given author.
     */
    @Test
    public void shouldGetBlueprintsByAuthor() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        Blueprint expectedBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(10, 10), new Point(20, 20) });
        authorBlueprints.add(expectedBlueprint);

        ibpp.saveBlueprint(expectedBlueprint);

        assertEquals("The list of saved blueprints should match the expected set of blueprints for the author",
                authorBlueprints,
                ibpp.getBlueprintsByAuthor("John"));
    }

    /**
     * Tests deleting a blueprint and ensuring it is no longer retrievable.
     * 
     * @throws BlueprintPersistenceException If there is an issue with deleting the
     *                                       blueprint.
     * @throws BlueprintNotFoundException    If the blueprint cannot be found after
     *                                       deletion.
     */
    @Test
    public void shouldDeleteBlueprint() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Blueprint bp = new Blueprint("John", "TempPlan", new Point[] { new Point(5, 5) });
        ibpp.saveBlueprint(bp);
        ibpp.deleteBlueprint(bp.getAuthor(), bp.getName());
        try {
            ibpp.getBlueprint(bp.getAuthor(), bp.getName());
            fail("Expected BlueprintNotFoundException was not thrown");
        } catch (BlueprintNotFoundException e) {
            // Expected exception
        }
    }

    /**
     * Tests that attempting to retrieve a non-existent blueprint throws a
     * BlueprintNotFoundException.
     */
    @Test
    public void shouldThrowExceptionForNonExistentBlueprint() {
        try {
            ibpp.getBlueprint("NonExistentAuthor", "NonExistentPlan");
            fail("Expected BlueprintNotFoundException was not thrown");
        } catch (BlueprintNotFoundException e) {
            // Expected exception
        }
    }
}
