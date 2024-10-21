package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterSuprimePointsRepetitive implements FilterBlueprints{

    public FilterSuprimePointsRepetitive(){

    }
    public void filterBlueprint(Blueprint bp) {
        List<Point> points = bp.getPoints();
        if (points == null || points.size() <= 1) {
            return; // Si no hay puntos o solo hay uno, no hay redundancias que filtrar
        }

        List<Point> filteredPoints = new ArrayList<>();
        Point previousPoint = points.get(0); // El primer punto siempre se incluye
        filteredPoints.add(previousPoint);

        for (int i = 1; i < points.size(); i++) {
            Point currentPoint = points.get(i);
            // Solo agregar el punto si es diferente al anterior
            if (currentPoint.getX() != previousPoint.getX() && currentPoint.getY() != previousPoint.getY()) {
                filteredPoints.add(currentPoint);
            }
            previousPoint = currentPoint;
        }

        bp.setPoints(filteredPoints);
    }

}
