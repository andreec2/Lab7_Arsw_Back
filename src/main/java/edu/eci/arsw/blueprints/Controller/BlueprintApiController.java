package edu.eci.arsw.blueprints.Controller;

import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BlueprintApiController {

    private final BlueprintsServices blueprintService;

    public BlueprintApiController(BlueprintsServices blueprintService) {
        this.blueprintService = blueprintService;
    }


    @GetMapping("/blueprints/{author}")
    public ResponseEntity<Set<Blueprint>> getBlueprintsByAuthor(@PathVariable("author") String author) {
        Set<Blueprint> blueprints;
        try {
            blueprints = blueprintService.getBlueprintsByAuthor(author);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (blueprints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(blueprints, HttpStatus.OK);
    }

    @GetMapping("/blueprints/{author}/{bpname}")
    public ResponseEntity<Blueprint> getBlueprintByAuthorAndName(@PathVariable("author") String author,
                                                                 @PathVariable("bpname") String bpname) {
        Blueprint blueprint;
        try {
            blueprint = blueprintService.getBlueprint(author, bpname);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(blueprint, HttpStatus.OK);
    }

    @PostMapping("/blueprints")
    public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint newBlueprint) {
        try {
            blueprintService.addNewBlueprint(newBlueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            Logger.getLogger(BlueprintApiController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error al registrar el plano", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/blueprints/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(@PathVariable("author") String author,
                                             @PathVariable("bpname") String bpname,
                                             @RequestBody Blueprint updatedBlueprint) {
        try {
            blueprintService.updateBlueprint(author, bpname, updatedBlueprint);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
