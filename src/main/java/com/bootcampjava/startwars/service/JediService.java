package com.bootcampjava.startwars.service;

import com.bootcampjava.startwars.model.Jedi;
import com.bootcampjava.startwars.repository.JediRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JediService {

    private static final Logger logger = LogManager.getLogger(JediService.class);

    // repository
    private final JediRepositoryImpl jediRepositoryImpl;


    public JediService(JediRepositoryImpl jediRepositoryImpl) {
        this.jediRepositoryImpl = jediRepositoryImpl;
    }


    public Optional<Jedi> findById(int id) {

        logger.info("Find Jedi with id: {}", id);

        return  jediRepositoryImpl.findById(id);
    }

    public List<Jedi> findAll() {

        logger.info("Bring all the Jedis from the Galaxy");

        return jediRepositoryImpl.findAll();
    }

    public Jedi save(Jedi jedi) {
        jedi.setVersion(1);

        logger.info("Update Jedi from system");

        return jediRepositoryImpl.save(jedi);
    }

    public boolean update(Jedi jedi){
        boolean updated = false;

        Optional<Jedi> savedJedi = this.findById(1);

        if(savedJedi.isPresent() && savedJedi.get().getVersion() == jedi.getVersion()){
                return false;
        }

        updated = jediRepositoryImpl.update(jedi);

        return updated;
    }

    public boolean delete(int id) {
        boolean deleted = false;

        Optional<Jedi> savedJedi = this.findById(1);

        if(savedJedi.isPresent()){
            deleted = jediRepositoryImpl.delete(id);
        }

        return deleted;
    }
}
