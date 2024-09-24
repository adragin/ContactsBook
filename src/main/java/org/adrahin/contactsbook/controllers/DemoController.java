package org.adrahin.contactsbook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getItemById(@PathVariable("id") String value) {
        String response = String.format("Передано значение: %s", value);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/postItem")
    public ResponseEntity<String> postItemWithValue(@RequestBody String data) {
        String response = String.format("postItemWithValue получил данные: %s", data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<String> postItem(@RequestBody String data) {
        String response = String.format("postItem получил данные: %s", data);
        return ResponseEntity.ok(response);
    }
}
