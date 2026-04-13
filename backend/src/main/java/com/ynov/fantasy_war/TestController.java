package com.ynov.fantasy_war;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @PostMapping("/secure")
    public String secureEndpoint() {
        return "JWT valide ! ✅";
    }
}
