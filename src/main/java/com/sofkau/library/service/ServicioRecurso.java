package com.sofkau.library.service;

import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.dto.RecursoMapper;
import com.sofkau.library.entities.Recurso;
import com.sofkau.library.repository.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public RecursoDTO agregar(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }
    public RecursoDTO modificar(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        repositorioRecurso.findById(recurso.getId()).orElseThrow(()-> new RuntimeException("Recurso no encontrado"));
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }
    public void borrar(String id) {
        repositorioRecurso.deleteById(id);
    }
}
