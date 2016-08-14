package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/structure", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody String input) {
        Structure structure = applicationService.postStructure(input);

        // TODO: use post for object

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/structure/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Structure get(@PathVariable(value = "id") final String id) {
        Structure structure = applicationService.getStructure(id);
        return structure;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello Bourgeois Pig!";
    }

}


