package com.bootcampjava.startwars.service;

import com.bootcampjava.startwars.model.Jedi;
import com.bootcampjava.startwars.repository.JediRepositoryImpl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JediTestService {

    @Autowired
    private JediService jediService;

    @MockBean
    private JediRepositoryImpl jediRepository;


    @Test
    @DisplayName("Should return Jedi with success")
    public void testFindBySuccess() {

        // arrange
        Jedi mockJedi = new Jedi(1, "Jedi Name", 10, 1);
        Mockito.doReturn(Optional.of(mockJedi)).when(jediRepository).findById(1);

        // act
        Optional<Jedi> returnedJedi  = jediService.findById(1);

        // assert
        Assertions.assertTrue(returnedJedi.isPresent(), "Jedi was not found");
        Assertions.assertSame(returnedJedi.get(), mockJedi, "Jedis must be the same");
    }

    // TODO: Criar teste de erro NOT FOUND
    @Test
    @DisplayName("Should return not found")
    public void testFindByNotFound() {
        //arrange
        int id = 1;
        Mockito.doReturn(Optional.empty()).when(jediRepository).findById(id);

        //act
        Optional<Jedi> returnedJedi  = jediService.findById(id);

        //assert
        Assertions.assertTrue(returnedJedi.isEmpty(), "Jedi was not found");

    }

    // TODO: Criar um teste pro findAll();

    @Test
    @DisplayName("Should return a list with 3 Jedis")
    public void testFindAllReturningAListWithJedisSucces() {

        //arrange
        Jedi mockJedi1 = new Jedi(1, "Jedi Name", 10, 1);
        Jedi mockJedi2 = new Jedi(2, "Jedi Name 2", 20, 2);
        Jedi mockJedi3 = new Jedi(3, "Jedi Name 3", 30, 3);

        List<Jedi> mockedList = new ArrayList<>();
        mockedList.add(mockJedi1);
        mockedList.add(mockJedi2);
        mockedList.add(mockJedi3);


        Mockito.doReturn(mockedList).when(jediRepository).findAll();

        //act
        List<Jedi> returnedListOfJedis = jediService.findAll();

        //assert
        Assertions.assertTrue(returnedListOfJedis != null);
        Assertions.assertTrue(returnedListOfJedis.size() == 3);
        Assertions.assertSame(returnedListOfJedis,mockedList);

    }


}
