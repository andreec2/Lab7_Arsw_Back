package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class FilterSuprimePoints implements FilterBlueprints{

    public FilterSuprimePoints(){

    }
    @Override
    public void filterBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        List<Point> points = bp.getPoints();
        List<Point> resPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            if (!(i % 2 == 0)) {
                resPoints.add(points.get(i));
            }
        }

        bp.setPoints(resPoints);
    }
}
