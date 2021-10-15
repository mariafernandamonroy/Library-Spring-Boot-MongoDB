package com.sofkau.library.repository;

import com.sofkau.library.entities.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioRecurso extends MongoRepository<Recurso, String> {

}
