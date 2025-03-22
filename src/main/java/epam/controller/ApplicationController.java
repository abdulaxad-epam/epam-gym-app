package epam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ApplicationController {

    @GetMapping(value = "/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/swagger-ui/index.html")
    public String buy() {
        return "Buy";
    }
}
