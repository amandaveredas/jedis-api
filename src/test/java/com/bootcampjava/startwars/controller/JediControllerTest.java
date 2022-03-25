package com.bootcampjava.startwars.controller;

import com.bootcampjava.startwars.model.Jedi;
import com.bootcampjava.startwars.service.JediService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JediControllerTest {

    @MockBean
    private JediService jediService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("GET /jedi/1 - SUCCESS")
    public void testGetJediByIdWithSuccess() throws Exception {

        // cenario
        Jedi mockJedi = new Jedi(1, "HanSolo", 10, 1);
        Mockito.doReturn(Optional.of(mockJedi)).when(jediService).findById(1);

        // execucao
        mockMvc.perform(get("/jedi/{id}", 1))

                // asserts
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("GET /jedi/1 - Not Found")
    public void testGetJediByIdNotFound() throws Exception {

        Mockito.doReturn(Optional.empty()).when(jediService).findById(1);

        mockMvc.perform(get("/jedi/{1}", 1))
                .andExpect(status().isNotFound());

    }

    // TODO: Teste do POST com sucesso

    @Test
    @DisplayName("POST /jedi/ - Created")
    public void testPostJediWithSuccess() throws Exception {
        //arrange
        Jedi mockJediReturned = new Jedi(1,"HanSolo", 10,1);
        Mockito.doReturn(mockJediReturned).when(jediService).save(any(Jedi.class));

        //act
        mockMvc.perform(MockMvcRequestBuilders
                                .post("/jedi")
                                .content(asJsonString(mockJediReturned))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                //asserts
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(1)));


    }

    // TODO: Teste do PUT com sucesso
    @Test
    @DisplayName("PUT /jedi/ - No Content")
    public void testPutJediWithSuccess() throws Exception {
        //arrange
        Jedi mockJedi = new Jedi(1,"HanSolo", 10,1);
        Mockito.doReturn(true).when(jediService).update(any(Jedi.class));

        //act
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/jedi/{id}",1)
                        .content(asJsonString(mockJedi))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //asserts
                .andExpect(status().isNoContent())
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""));
    }

    // TODO: Teste do PUT com uma versao igual da ja existente - deve retornar um conflito
    @Test
    @DisplayName("PUT /jedi/ - Bad Request")
    public void testPutJediWithSameVersionBadRequest() throws Exception {
        //arrange
        Jedi mockJedi = new Jedi(1,"HanSolo", 10,1);
        Mockito.doReturn(false).when(jediService).update(any(Jedi.class));

        //act
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/jedi")
                        .content(asJsonString(mockJedi))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                //asserts
                .andExpect(status().isBadRequest());
    }



    // TODO: Teste do PUT com erro - not found
    @Test
    @DisplayName("PUT /jedi/ - Not Found")
    public void testPutJediNotFound() {
//        //arrange
//        Jedi mockJedi = new Jedi(1,"HanSolo", 10,1);
//        DataAccessException e = new DataAccessException(""){};
//        Mockito.doThrow(e).when(jediService).update(any(Jedi.class));
//
//        //act
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/jedi")
//                        .content(asJsonString(mockJedi))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//
//                //asserts
//                .andExpect(status().isNotFound());

    }

    // TODO: Teste do delete com sucesso
    @Test
    @DisplayName("DELETE /jedi/ - No Content")
    public void testDeleteWithSuccess() throws Exception {
        //arrange
        int id = 1;
        Mockito.doReturn(true).when(jediService).delete(1);

        //act
        mockMvc.perform(MockMvcRequestBuilders
                                .delete("/jedi/{id}",id))


                //asserts
                .andExpect(status().isNoContent());
    }

    // TODO: Teste do delete com erro - deletar um id ja deletado
    @Test
    @DisplayName("DELETE /jedi/ - Not Found")
    public void testDeleteNotFound() throws Exception {
        //arrange
        int id = 1;
        Mockito.doReturn(false).when(jediService).delete(1);

        //act
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/jedi/{id}",id))


                //asserts
                .andExpect(status().isNotFound());
    }


    // TODO: Teste do delete com erro  - internal server error
    @Test
    @DisplayName("DELETE /jedi/ - Internal Server Error")
    public void testDeleteInternalServerError(){

    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
