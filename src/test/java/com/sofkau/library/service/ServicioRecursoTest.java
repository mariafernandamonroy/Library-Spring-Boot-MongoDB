package com.sofkau.library.service;

import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.repository.RepositorioRecurso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ServicioRecursoTest {

    @MockBean
    private RepositorioRecurso repositorioRecurso;

    @Autowired
    private ServicioRecurso servicioRecurso;

    @Test
    @DisplayName("GET /recursos success")
    public void obtenerTodos() throws Exception {
        var listRecursos = new ArrayList<RecursoDTO>();
        listRecursos.add(new RecursoDTO("1","hola","revista","entretenimiento",false));
        listRecursos.add(new RecursoDTO("2","tesla","libro","biografia",false));
        listRecursos.add(new RecursoDTO("3","desayunos","libro","gastronomia",false));

        Mockito.when(servicioRecurso.obtenerTodos()).thenReturn(listRecursos);

        String url = "/library";

//        mockMvc.perform(get(url))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$",hasSize(3)))
//                .andExpect(jsonPath("$[0].id").value("1"))
//                .andExpect(jsonPath("$[0].titulo").value("hola"))
//                .andExpect(jsonPath("$[0].clasificacion").value("revista"))
//                .andExpect(jsonPath("$[0].area").value("entretenimiento"))
//                .andExpect(jsonPath("$[0].prestado").value(false))
//                .andExpect(jsonPath("$[1].id").value("2"))
//                .andExpect(jsonPath("$[1].titulo").value("tesla"))
//                .andExpect(jsonPath("$[1].clasificacion").value("libro"))
//                .andExpect(jsonPath("$[1].area").value("biografia"))
//                .andExpect(jsonPath("$[1].prestado").value(false))
//                .andExpect(jsonPath("$[2].id").value("3"))
//                .andExpect(jsonPath("$[2].titulo").value("desayunos"))
//                .andExpect(jsonPath("$[2].clasificacion").value("libro"))
//                .andExpect(jsonPath("$[2].area").value("gastronomia"))
//                .andExpect(jsonPath("$[2].prestado").value(false));
    }


    @Test
    void obtenerPorId() {
    }

    @Test
    void agregarUnRecurso() {
    }

    @Test
    void modificarUnRecurso() {
    }

    @Test
    void borrar() {
    }

    @Test
    void disponibilidadRecurso() {
    }

    @Test
    void prestarUnRecurso() {
    }

    @Test
    void recursosRecomendados() {
    }

    @Test
    void devolverRecurso() {
    }
}