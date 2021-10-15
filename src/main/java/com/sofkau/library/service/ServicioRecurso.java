package com.sofkau.library.service;

import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.dto.RecursoMapper;
import com.sofkau.library.entities.Recurso;
import com.sofkau.library.repository.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioRecurso {
    @Autowired
    RepositorioRecurso repositorioRecurso;
    RecursoMapper recursoMapper = new RecursoMapper();

    public List<RecursoDTO> obtenerTodos() {
        List<Recurso> recursos = (List<Recurso>) repositorioRecurso.findAll();
        return recursoMapper.fromCollectionList(recursos);
    }
    public RecursoDTO obtenerPorId(String id) {
        Recurso recurso = repositorioRecurso.findById(id).orElseThrow(()-> new RuntimeException("Recurso no encontrado"));
        return recursoMapper.fromCollection(recurso);
    }
    public RecursoDTO agregarUnRecurso(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }
    public RecursoDTO modificarUnRecurso(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        repositorioRecurso.findById(recurso.getId()).orElseThrow(()-> new RuntimeException("Recurso no encontrado"));
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }
    public void borrar(String id) {
        repositorioRecurso.deleteById(id);
    }
    public String disponibilidadRecurso(String id) {
        String mensaje = "";
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (recursoDTO.isPrestado() == false){
            recursoDTO.getFechaPrestamo();
            mensaje = ("Recurso no disponible");
        }
        return mensaje = ("Recurso disponible");
    }
    public RecursoDTO prestarUnRecurso(String id) {
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (recursoDTO.isPrestado() == true){
            recursoDTO.getFechaPrestamo();
            new RuntimeException("Recurso no disponible");
        }
        recursoDTO.setPrestado(true);
        LocalDate date = LocalDate.now();
        recursoDTO.setFechaPrestamo(date);
        new RuntimeException("Recurso disponible");
        return modificarUnRecurso(recursoDTO);
    }
    public List<RecursoDTO> recursosRecomendados(String clasificacion, String area) {
        List<RecursoDTO> listaRecursos = obtenerTodos();
        if (!clasificacion.isEmpty()) {
            return listaRecursos.stream()
                    .filter(recursoDTO -> recursoDTO.getClasificación().equalsIgnoreCase(clasificacion))
                    .collect(Collectors.toList());
        }
        if (!area.isEmpty()) {
            return listaRecursos.stream()
                    .filter(recursoDTO -> recursoDTO.getArea().equalsIgnoreCase(area))
                    .collect(Collectors.toList());
        }if (!clasificacion.isEmpty() && !area.isEmpty()) {
            return listaRecursos.stream()
                    .filter(recursoDTO -> recursoDTO.getClasificación().equalsIgnoreCase(clasificacion))
                    .filter(recursoDTO -> recursoDTO.getArea().equalsIgnoreCase(area))
                    .collect(Collectors.toList());
        }
        return listaRecursos;
    }
    public String devolverRecurso(String id) {
        String mensaje = "";
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (recursoDTO.isPrestado() == false){
            return mensaje = ("El recurso no se puede devolver porque no estaba prestado");
        }
        recursoDTO.setPrestado(false);
        return mensaje = ("Recurso devuelto");
    }
}
