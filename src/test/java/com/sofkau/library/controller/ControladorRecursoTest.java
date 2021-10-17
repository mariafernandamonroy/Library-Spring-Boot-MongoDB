package com.sofkau.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.library.dto.RecursoDTO;
import com.sofkau.library.entities.Recurso;
import com.sofkau.library.repository.RepositorioRecurso;
import com.sofkau.library.service.ServicioRecurso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.Matchers.hasSize;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ControladorRecurso.class)
class ControladorRecursoTest {

    @MockBean
    private ServicioRecurso servicioRecurso;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /recursos success")
    public void obtenerTodos() throws Exception {
        var listRecursos = new ArrayList<RecursoDTO>();
        listRecursos.add(new RecursoDTO("1","hola","revista","entretenimiento",false));
        listRecursos.add(new RecursoDTO("2","tesla","libro","biografia",false));
        listRecursos.add(new RecursoDTO("3","desayunos","libro","gastronomia",false));

        Mockito.when(servicioRecurso.obtenerTodos()).thenReturn(listRecursos);

        String url = "/library";

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].titulo").value("hola"))
                .andExpect(jsonPath("$[0].clasificacion").value("revista"))
                .andExpect(jsonPath("$[0].area").value("entretenimiento"))
                .andExpect(jsonPath("$[0].prestado").value(false))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].titulo").value("tesla"))
                .andExpect(jsonPath("$[1].clasificacion").value("libro"))
                .andExpect(jsonPath("$[1].area").value("biografia"))
                .andExpect(jsonPath("$[1].prestado").value(false))
                .andExpect(jsonPath("$[2].id").value("3"))
                .andExpect(jsonPath("$[2].titulo").value("desayunos"))
                .andExpect(jsonPath("$[2].clasificacion").value("libro"))
                .andExpect(jsonPath("$[2].area").value("gastronomia"))
                .andExpect(jsonPath("$[2].prestado").value(false));
    }

    @Test
    void agregarRecurso() throws Exception {
        var datoPost = new RecursoDTO();
        datoPost.setTitulo("arquimedes");
        datoPost.setClasificacion("libro");
        datoPost.setArea("ciencia");
        datoPost.setPrestado(false);
        datoPost.setFechaPrestamo(null);

        var datoReturn = new RecursoDTO();
        datoReturn.setId("2");
        datoReturn.setTitulo("astronomia");
        datoReturn.setClasificacion("libro");
        datoReturn.setArea("ciencia");
        datoReturn.setPrestado(false);
        datoReturn.setFechaPrestamo(null);

        Mockito.when(servicioRecurso.agregarUnRecurso(Mockito.any())).thenReturn(datoReturn);

        String url = "/library/agregarRecurso";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(datoPost)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.titulo").value("astronomia"))
                .andExpect(jsonPath("$.clasificacion").value("libro"))
                .andExpect(jsonPath("$.area").value("ciencia"))
                .andExpect(jsonPath("$.prestado").value(false));
    }

    @Test
    @DisplayName("Test recurso disponible")
    void disponibilidadRecurso() throws Exception {
        var datoPost = new RecursoDTO();
        datoPost.setId("2");
        datoPost.setTitulo("arquimedes");
        datoPost.setClasificacion("libro");
        datoPost.setArea("ciencia");
        datoPost.setPrestado(false);
        datoPost.setFechaPrestamo(null);

        Mockito.when(servicioRecurso.disponibilidadRecurso(Mockito.any())).thenReturn("Recurso disponible");

        String url = "/library/recursoDisponible/arquimedes";

        mockMvc.perform(get(url)
                .contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
                .content(asJsonString(datoPost)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Recurso disponible"));
    }

    @Test
    void prestarUnRecurso() throws Exception {
        var datoPost = new RecursoDTO();
        datoPost.setTitulo("arquimedes");
        datoPost.setClasificacion("libro");
        datoPost.setArea("ciencia");
        datoPost.setPrestado(false);
        datoPost.setFechaPrestamo(null);

        var datoReturn = new RecursoDTO();
        datoReturn.setId("2");
        datoReturn.setTitulo("astronomia");
        datoReturn.setClasificacion("libro");
        datoReturn.setArea("ciencia");
        datoReturn.setPrestado(true);
        datoReturn.setFechaPrestamo(LocalDate.now());

        Mockito.when(servicioRecurso.prestarUnRecurso(Mockito.any())).thenReturn(datoReturn);

        String url = "/library/prestarUnRecurso/astronomia";

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(datoPost)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.titulo").value("astronomia"))
                .andExpect(jsonPath("$.clasificacion").value("libro"))
                .andExpect(jsonPath("$.area").value("ciencia"))
                .andExpect(jsonPath("$.prestado").value(true))
                .andExpect(jsonPath("$.fechaPrestamo").value(LocalDate.now().toString()));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}