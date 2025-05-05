package org.acme.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {
    @Inject
    EntityManager manager;

    private final String USER_NOT_FOUND_EXCEPTION = "User not found";

    @Transactional
    public void saveUser(User user) {
        manager.persist(user);
    }

    @Transactional
    public void saveAll(Iterable<User> users) {
        users.forEach(this::saveUser);
    }

    @Transactional
    public User findUserById(Long id) {
        return manager.find(User.class, id);
    }

    @Transactional
    public Collection<User> findAll() {
        return manager.createQuery("SELECT u FROM Users u", User.class).getResultList();

    }


    /**
     * Convert users to csv string
     *
     * @param users collection of users
     * @return csv string of all the users
     * @throws Exception if no users found
     */
    public String userToCSV(Collection<User> users) throws Exception {
        if (users.isEmpty()) {
            var csvMapper = new CsvMapper();
            return users.stream().map(user -> {
                try {
                    return csvMapper.writeValueAsString(user);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).toString();
        } else {
            throw new Exception(USER_NOT_FOUND_EXCEPTION);
        }
    }

    public String userToCSV(Long id) throws Exception {
        var user = findUserById(id);
        var csvMapper = new CsvMapper();
        if (user != null) {
            return csvMapper.writeValueAsString(user);
        } else {
            throw new Exception(USER_NOT_FOUND_EXCEPTION);
        }
    }

    public void csvToUser(String path) throws Exception {
        var csvMapper = new CsvMapper();
        try (var iterator = csvMapper.readerForListOf(String.class).with(CsvParser.Feature.WRAP_AS_ARRAY).readValues(new File(path))) {
            var users = iterator.readAll();
            users.forEach(user -> {
                saveUser(csvMapper.convertValue(user, User.class));
            });
        } catch (Exception e) {
            throw new Exception("Error while reading file : " + e.getMessage());
        }
    }

    public String printAllUsers() {
        return findAll().stream().map(User::toString).collect(Collectors.joining("\n"));
    }
}
