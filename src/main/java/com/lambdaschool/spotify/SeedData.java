package com.lambdaschool.spotify;

import com.lambdaschool.spotify.models.*;
import com.lambdaschool.spotify.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
//@Component
public class SeedData
        implements CommandLineRunner
{

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public void run(String[] args)
            throws
            Exception
    {
        User u1 = new User("test user",
                "password",
                "testuser@lambdaschool.local");
        u1.getFavesongs().add(new Favesong(u1, "a1f52asdf"));
        userService.save(u1);

        User u2 = new User("test user 2",
                "password",
                "testuser2@lambdaschool.local");
        u2.getFavesongs().add(new Favesong(u2, "la1sdfkjad1l"));
        userService.save(u2);
    }
}