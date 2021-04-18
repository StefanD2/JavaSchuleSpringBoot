package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.config.Endpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * für eine Server-Server Kommunikation
 */
@RestController
@RequestMapping(Endpoints.demo)
public class BaseController {

    @PostMapping("/ping")
    public ResponseEntity<String> pingPost() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingGet() {
        return ResponseEntity.ok("pong");
    }

}
