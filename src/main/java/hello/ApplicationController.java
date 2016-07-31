package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/structures")
public class ApplicationController {

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<?> addStructure(@RequestBody String input) {
        System.out.println(input);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
