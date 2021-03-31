package at.ac.htlstp.deimel.springbootdemoserver.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * für eine Server-Server Kommunikation
 */
@RestController
@RequestMapping("/demo")
public class BaseController {

    @PostMapping("/ping")
    public ResponseEntity<String> pingPost() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingGet() {
        return ResponseEntity.ok("pong");
    }

    // auch ein Test für KeyBoardWebController, nocht weitergeamcht
    @PostMapping(value = "/keySend", consumes = "text/plain")
    public ResponseEntity<String> tes(@RequestBody String val) {

        final String uri = "http://192.168.137.58/key/";

        ResponseEntity<String> response = (new RestTemplate()).exchange(uri, HttpMethod.POST, new HttpEntity<>(val), String.class);

        return ResponseEntity.ok(response.getStatusCode().getReasonPhrase());
    }

}
