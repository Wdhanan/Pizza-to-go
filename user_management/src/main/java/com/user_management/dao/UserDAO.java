package com.user_management.dao;

import java.util.Arrays;
import java.util.Optional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.user_management.model.User;
import com.user_management.util.PasswordTools;

@Singleton
public class UserDAO {

    @PersistenceContext(name = "jpa-unit")
    EntityManager em;

    public boolean checkUserPassword(String username, String password) {

        try {
            Query query = em.createNamedQuery("User.findByUserName", User.class);
            query.setParameter("username", username);
            User user = (User) query.getSingleResult();

            byte[] passwordSalt = user.getPasswordSalt();
            byte[] passwordHash = user.getPasswordHash();

            byte[] passwordToCheck = PasswordTools.generatePasswordHash(password, passwordSalt);
            if (Arrays.equals(passwordHash, passwordToCheck)) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable thr) {
            throw new RuntimeException("ERROR checkUserPassword");
        }

    }

    public boolean deleteUser(String username) {
        try {
            Query query = em.createNamedQuery("User.findByUserName", User.class);
            query.setParameter("username", username);
            User user = (User) query.getSingleResult();
            em.remove(user);

            return true;
        } catch (Throwable thr) {
            throw new RuntimeException("ERROR checkUserPassword");
        }
    }

    public User createUser(String username, String password, String firstname, String lastname, String email,
                           String telefon, String street, String streetNumber, String zip, String city) {

        try {
            User user = new User();
            user.setUsername(username);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setTelefon(telefon);
            user.setStreet(street);
            user.setStreetNumber(streetNumber);
            user.setZip(zip);
            user.setCity(city);

            byte[] passwordSalt = PasswordTools.generateSalt();
            byte[] passwordHash = PasswordTools.generatePasswordHash(password, passwordSalt);

            user.setPasswordSalt(passwordSalt);
            user.setPasswordHash(passwordHash);

            em.persist(user);
            em.flush();
            em.refresh(user);
            System.out.println("User" + username + "registered");
            return user;
        } catch (Throwable thr) {
            thr.printStackTrace();
            throw new RuntimeException("ERROR createUser");
        }

    }

    public Optional<User> findUser(int user_id) {

        User user = em.find(User.class, user_id);

        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> findUser(String username) {
        Query query = em.createNamedQuery("User.findByUserName", User.class);
        query.setParameter("username", username);
        try {
            return Optional.of((User) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

}
