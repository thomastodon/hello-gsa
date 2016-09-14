package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class ApplicationController {

    private ApplicationService applicationService;

    @Autowired
    ApplicationController(
            ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/structure", method = RequestMethod.POST)
    public ResponseEntity<StructureEntity> postStructure(@RequestHeader(value="Structure-Id") String structureId, @RequestBody String input) {
        StructureEntity structure = applicationService.postStructure(structureId, input);
        return new ResponseEntity<StructureEntity>(structure, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @RequestMapping(
            value = "/structure/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    StructureEntity getStructure(@PathVariable(value = "id") final String id) {
        return applicationService.getStructure(id);
    }
}

// TODO: endpoints for other results