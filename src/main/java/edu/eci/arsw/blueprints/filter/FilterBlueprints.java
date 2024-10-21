package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;

public interface FilterBlueprints {

    public void filterBlueprint(Blueprint bp) throws BlueprintPersistenceException;
}
