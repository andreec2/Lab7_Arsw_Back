package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.filter.FilterSuprimePoints;
import edu.eci.arsw.blueprints.filter.FilterSuprimePointsRepetitive;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the blueprint filtering functionality.
 * 
 * This class contains tests for the filtering methods in the
 * {@link FilterSuprimePoints} and {@link FilterSuprimePointsRepetitive}
 * classes.
 * It verifies that the filtering processes correctly handle blueprints with
 * various configurations of points.
 * 
 * Tests include filtering for duplicate points, handling empty blueprints, and
 * verifying that non-repetitive blueprints remain unchanged.
 * 
 * Note: The expected results are compared with the filtered blueprints to
 * ensure the correctness of the filtering logic.
 * 
 */
public class FilterBlueprintsTest {

    private FilterSuprimePoints fsp;
    private FilterSuprimePointsRepetitive fpr;

    /**
     * Tests filtering of a blueprint to remove duplicate points.
     * 
     * This test ensures that the filter removes repetitive points from the
     * blueprint, leaving only unique points.
     * 
     * @throws BlueprintPersistenceException If there is an issue with the filtering
     *                                       process.
     */
    @Test
    public void shouldFilterBlueprint() throws BlueprintPersistenceException {
        fsp = new FilterSuprimePoints();
        Blueprint pBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(10, 10), new Point(20, 20), new Point(20, 20), new Point(20, 20) });
        Blueprint resBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(20, 20), new Point(20, 20) });
        fsp.filterBlueprint(pBlueprint);
        assertEquals("The filtered blueprint should have the expected points", resBlueprint.getPoints(),
                pBlueprint.getPoints());
    }

    /**
     * Tests filtering of a blueprint to remove repetitive points.
     * 
     * This test ensures that the filter correctly handles blueprints with
     * repetitive points, leaving only unique points.
     * 
     * @throws BlueprintPersistenceException If there is an issue with the filtering
     *                                       process.
     */
    @Test
    public void shouldFilterBlueprintRepetitive() throws BlueprintPersistenceException {
        fpr = new FilterSuprimePointsRepetitive();
        Blueprint pBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(10, 10), new Point(20, 20), new Point(20, 20), new Point(20, 20) });
        Blueprint resBlueprint = new Blueprint("John", "HousePlan",
                new Point[] { new Point(10, 10), new Point(20, 20) });
        fpr.filterBlueprint(pBlueprint);
        assertEquals("The filtered blueprint should have the expected points without repetitions",
                resBlueprint.getPoints(), pBlueprint.getPoints());
    }

    /**
     * Tests filtering of an empty blueprint.
     * 
     * This test ensures that an empty blueprint remains empty after filtering.
     * 
     * @throws BlueprintPersistenceException If there is an issue with the filtering
     *                                       process.
     */
    @Test
    public void shouldFilterEmptyBlueprint() throws BlueprintPersistenceException {
        fsp = new FilterSuprimePoints();
        Blueprint pBlueprint = new Blueprint("John", "EmptyPlan", new Point[] {});
        Blueprint expectedBlueprint = new Blueprint("John", "EmptyPlan", new Point[] {});
        fsp.filterBlueprint(pBlueprint);
        assertEquals("The empty blueprint should remain empty", expectedBlueprint.getPoints(),
                pBlueprint.getPoints());
    }

    /**
     * Tests filtering of a blueprint where all points are repetitive.
     * 
     * This test ensures that a blueprint with all repetitive points is filtered to
     * have only one unique point.
     * 
     * @throws BlueprintPersistenceException If there is an issue with the filtering
     *                                       process.
     */
    @Test
    public void shouldFilterBlueprintAllPointsRepetitive() throws BlueprintPersistenceException {
        fpr = new FilterSuprimePointsRepetitive();
        Blueprint pBlueprint = new Blueprint("John", "RepetitivePlan",
                new Point[] { new Point(10, 10), new Point(10, 10), new Point(10, 10) });
        Blueprint expectedBlueprint = new Blueprint("John", "RepetitivePlan", new Point[] { new Point(10, 10) });
        fpr.filterBlueprint(pBlueprint);
        assertEquals("The blueprint with all repetitive points should have only one unique point",
                expectedBlueprint.getPoints(), pBlueprint.getPoints());
    }

    /**
     * Tests that a blueprint with no repetitive points remains unchanged after
     * filtering.
     * 
     * This test ensures that a blueprint with only unique points is not altered by
     * the filtering process.
     * 
     * @throws BlueprintPersistenceException If there is an issue with the filtering
     *                                       process.
     */
    @Test
    public void shouldNotFilterBlueprintNoRepetitions() throws BlueprintPersistenceException {
        fpr = new FilterSuprimePointsRepetitive();
        Blueprint pBlueprint = new Blueprint("John", "UniquePlan",
                new Point[] { new Point(10, 10), new Point(20, 20) });
        Blueprint expectedBlueprint = new Blueprint("John", "UniquePlan",
                new Point[] { new Point(10, 10), new Point(20, 20) });
        fpr.filterBlueprint(pBlueprint);
        assertEquals("The blueprint with no repetitive points should remain unchanged", expectedBlueprint.getPoints(),
                pBlueprint.getPoints());
    }
}
