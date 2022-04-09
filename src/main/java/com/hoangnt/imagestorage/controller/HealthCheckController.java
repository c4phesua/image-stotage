package com.hoangnt.imagestorage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
  @GetMapping(value = "/actuator/health")
  public ResponseEntity<String> getHealth() {
    return ResponseEntity.ok("hello world");
  }
}
