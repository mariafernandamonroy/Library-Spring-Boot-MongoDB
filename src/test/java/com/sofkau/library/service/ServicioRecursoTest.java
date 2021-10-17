package com.sofkau.library.service;

import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.dto.RecursoMapper;
import com.sofkau.library.entities.Recurso;
import com.sofkau.library.repository.RepositorioRecurso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class ServicioRecursoTest {

    @MockBean
    private RepositorioRecurso repositorioRecurso;

    @Autowired
    private ServicioRecurso servicioRecurso;

    @Test
    @DisplayName("Test findAll Success")
    public void obtenerTodos() {

        var dato1 = new Recurso();
        dato1.setId("1");
        dato1.setTitulo("hola");
        dato1.setClasificacion("revista");
        dato1.setArea("entretenimiento");
        dato1.setPrestado(false);
        dato1.setFechaPrestamo(null);

        var dato2 = new Recurso();
        dato2.setId("2");
        dato2.setTitulo("tesla");
        dato2.setClasificacion("libro");
        dato2.setArea("biografia");
        dato2.setPrestado(false);
        dato2.setFechaPrestamo(null);

        var listaRecursos = new ArrayList<Recurso>();
        listaRecursos.add(dato1);
        listaRecursos.add(dato2);

        Mockito.when(repositorioRecurso.findAll()).thenReturn(listaRecursos);

        var resultado = servicioRecurso.obtenerTodos();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(dato1.getTitulo(), resultado.get(0).getTitulo());
        Assertions.assertEquals(dato1.getClasificacion(), resultado.get(0).getClasificacion());
        Assertions.assertEquals(dato1.getArea(), resultado.get(0).getArea());
        Assertions.assertEquals(dato1.isPrestado(), resultado.get(0).isPrestado());
        Assertions.assertEquals(dato2.getTitulo(), resultado.get(1).getTitulo());
        Assertions.assertEquals(dato2.getClasificacion(), resultado.get(1).getClasificacion());
        Assertions.assertEquals(dato2.getArea(), resultado.get(1).getArea());
        Assertions.assertEquals(dato2.isPrestado(), resultado.get(1).isPrestado());
    }

    @Test
    void agregarUnRecurso() {
        var dato1 = new RecursoDTO();
        dato1.setId("1");
        dato1.setTitulo("hola");
        dato1.setClasificacion("revista");
        dato1.setArea("entretenimiento");
        dato1.setPrestado(false);
        dato1.setFechaPrestamo(null);

        var dato2 = new Recurso();
        dato2.setId("2");
        dato2.setTitulo("tesla");
        dato2.setClasificacion("libro");
        dato2.setArea("biografia");
        dato2.setPrestado(false);
        dato2.setFechaPrestamo(null);

        Mockito.when(repositorioRecurso.save(Mockito.any())).thenReturn(dato2);

        var resultado = servicioRecurso.agregarUnRecurso(dato1);

        Assertions.assertEquals(dato2.getTitulo(),resultado.getTitulo());
        Assertions.assertEquals(dato2.getClasificacion(),resultado.getClasificacion());
        Assertions.assertEquals(dato2.getArea(),resultado.getArea());
        Assertions.assertEquals(dato2.isPrestado(),resultado.isPrestado());
    }

    @Test
    @DisplayName("Test para intenta devolver un recurso no prestado")
    void devolverRecursoNoPrestado() {
        var dato1 = new RecursoDTO();
        dato1.setId("1");
        dato1.setTitulo("hola");
        dato1.setClasificacion("revista");
        dato1.setArea("entretenimiento");
        dato1.setPrestado(false);
        dato1.setFechaPrestamo(null);

        var dato2 = new Recurso();
        dato2.setId("1");
        dato2.setTitulo("tesla");
        dato2.setClasificacion("libro");
        dato2.setArea("biografia");
        dato2.setPrestado(false);
        dato2.setFechaPrestamo(null);

        Mockito.when(repositorioRecurso.findById(Mockito.any())).thenReturn(java.util.Optional.of(dato2));

        var resultado = servicioRecurso.devolverRecurso(dato1.getId());
        String expectedMessage = "El recurso no se puede devolver porque no estaba prestado";
        Assertions.assertEquals(expectedMessage,resultado);
    }

    @Test
    @DisplayName("Test para intenta devolver un recurso prestado")
    void devolverRecursoPrestado() {
        var dato1 = new RecursoDTO();
        dato1.setId("1");
        dato1.setTitulo("hola");
        dato1.setClasificacion("revista");
        dato1.setArea("entretenimiento");
        dato1.setPrestado(true);
        dato1.setFechaPrestamo(null);

        var dato2 = new Recurso();
        dato2.setId("1");
        dato2.setTitulo("tesla");
        dato2.setClasificacion("libro");
        dato2.setArea("biografia");
        dato2.setPrestado(true);
        dato2.setFechaPrestamo(null);

        Mockito.when(repositorioRecurso.findById(Mockito.any())).thenReturn(java.util.Optional.of(dato2));

        var resultado = servicioRecurso.devolverRecurso(dato1.getId());
        String expectedMessage = "Recurso devuelto";
        Assertions.assertEquals(expectedMessage,resultado);
    }
}