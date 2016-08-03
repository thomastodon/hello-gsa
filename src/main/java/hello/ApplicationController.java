package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @RequestMapping(value = "/structures", method = RequestMethod.POST)
    public ResponseEntity<?> addStructure(@RequestBody String input) {
        System.out.println(input);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello Bourgeois Pig!";
    }

}


