package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/structures", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody String input) {
        System.out.println(input);
        Structure structure = applicationService.postStructure(input);

        // TODO: use post for object

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello Bourgeois Pig!";
    }

}


