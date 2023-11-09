package com.kroger.cache.poc.controller;

import java.util.List;
import java.util.Optional;

import com.kroger.cache.poc.model.Data;
import com.kroger.cache.poc.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("data")
@RequiredArgsConstructor
public class DataController {
    private final CacheService cacheService;

    @PostMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Data> put(@RequestBody Data data, @PathVariable String id) {
        return ResponseEntity.of(
            Optional.of(
                cacheService.put(id, data)
            )
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Data> get(@PathVariable String id) {
        return ResponseEntity.of(
            Optional.of(
                cacheService.get(id)
            )
        );
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Data>> get() {
        return ResponseEntity.of(
            Optional.of(
                cacheService.all()
            )
        );
    }

    @GetMapping(value = "/evict", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Data>> evict() {
        return ResponseEntity.of(
            Optional.of(
                cacheService.evict()
            )
        );
    }
}
