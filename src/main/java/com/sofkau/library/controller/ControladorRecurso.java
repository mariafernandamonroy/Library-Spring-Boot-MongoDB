package com.sofkau.library.controller;

import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.service.ServicioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class ControladorRecurso {
    @Autowired
    ServicioRecurso servicioRecurso;
    @GetMapping()
    public ResponseEntity<List<RecursoDTO>> obtenerTodos() {
        return new ResponseEntity<>(servicioRecurso.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> encontrarPorId(@PathVariable("id") String id) {
        return new ResponseEntity<>(servicioRecurso.obtenerPorId(id),HttpStatus.OK);
    }

    @PostMapping("/agregarRecurso")
    public ResponseEntity<RecursoDTO> agregarRecurso(@RequestBody RecursoDTO recursoDTO) {
        return new ResponseEntity<>(servicioRecurso.agregarUnRecurso(recursoDTO),HttpStatus.CREATED);
    }

    @PutMapping("/modificarRecurso")
    public ResponseEntity<RecursoDTO> modificarRecurso(@RequestBody RecursoDTO recursoDTO) {
        if (recursoDTO.getId() != null){
            return new ResponseEntity<>(servicioRecurso.modificarUnRecurso(recursoDTO),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarRecurso(@PathVariable String id) {
        try {
            servicioRecurso.borrar(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recursoDisponible/{titulo}")
    public ResponseEntity disponibilidadRecurso(@PathVariable String titulo) {
        return new ResponseEntity<>(servicioRecurso.disponibilidadRecurso(titulo),HttpStatus.OK);
    }

    @PutMapping("/prestarUnRecurso/{titulo}")
    public ResponseEntity prestarUnRecurso(@PathVariable String titulo) {
        return new ResponseEntity(servicioRecurso.prestarUnRecurso(titulo),HttpStatus.CREATED);
    }

    @GetMapping("/recursosRecomendados/{clasificacion}/{area}")
    public ResponseEntity recursosRecomendados(@PathVariable String clasificacion, @PathVariable String area) {
        return new ResponseEntity(servicioRecurso.recursosRecomendados(clasificacion,area),HttpStatus.OK);
    }

    @GetMapping("/devolverRecurso/{id}")
    public ResponseEntity devolverRecurso(@PathVariable String id) {
        return new ResponseEntity(servicioRecurso.devolverRecurso(id),HttpStatus.OK);
    }
}
