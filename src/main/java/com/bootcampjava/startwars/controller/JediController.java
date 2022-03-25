package com.bootcampjava.startwars.controller;


import com.bootcampjava.startwars.model.Jedi;
import com.bootcampjava.startwars.service.JediService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class JediController {

    private static final Logger logger = LogManager.getLogger(JediController.class);

    private final JediService jediService;

    public JediController(JediService jediService) {
        this.jediService = jediService;
    }

    @GetMapping("/jedi/{id}")
    public ResponseEntity<?> getJedi(@PathVariable int id) {

        return jediService.findById(id)
                .map(jedi -> {
                    try {
                        return  ResponseEntity
                                .ok()
                                .eTag(Integer.toString(jedi.getVersion()))
                                .location(new URI("/jedi/" + jedi.getId()))
                                .body(jedi);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/jedi")
    public ResponseEntity<Jedi> saveJedi(@RequestBody Jedi jedi) {

        Jedi newJedi = jediService.save(jedi);

        try {
            return ResponseEntity
                    .created(new URI("/jedi/" + newJedi.getId()))
                    .eTag(Integer.toString(newJedi.getVersion()))
                    .body(newJedi);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/jedi")
    public ResponseEntity<Jedi> updateJedi(@RequestBody Jedi jedi){
        Boolean updatedJedi = jediService.update(jedi);

        try {
            if(updatedJedi){
                return ResponseEntity
                        .noContent()
                        .build();
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/jedi/{id}")
    public ResponseEntity<Jedi> deleteJedi(@PathVariable int id){
        Boolean deletedJedi = jediService.delete(id);

        try {
            if(deletedJedi){
                return ResponseEntity
                        .noContent()
                        .build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }



    }



}
