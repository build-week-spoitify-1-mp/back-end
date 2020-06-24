package com.lambdaschool.spotify.services;

import com.lambdaschool.spotify.SpotifyApplication;
import com.lambdaschool.spotify.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotify.models.Favesong;
import com.lambdaschool.spotify.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpotifyApplication.class)
public class UserServiceImplTest
{
   @Autowired
   private UserService userService;

   @Before
   public void setUp() throws Exception
   {
      MockitoAnnotations.initMocks(this);
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void findUserById()
   {
      assertEquals("test user", userService.findUserById(1).getUsername());
   }

   @Test(expected = ResourceNotFoundException.class)
   public void findUserByIdFail()
   {
      assertEquals("test user", userService.findUserById(99).getUsername());
   }

   @Test
   public void findByNameContaining()
   {
      assertEquals(2, userService.findByNameContaining("test").size());
   }

   @Test
   public void findAll()
   {
      assertEquals(2, userService.findAll().size());
   }

   @Test
   public void delete()
   {
      userService.delete(1);
      assertEquals(1, userService.findAll().size());
   }

   @Test (expected = ResourceNotFoundException.class)
   public void deletenotfound()
   {
      userService.delete(99);
      assertEquals(1, userService.findAll().size());
   }

   @Test
   public void findByName()
   {
      assertEquals("test user 2", userService.findByName("test user 2").getUsername());
   }

   @Test
   public void save()
   {
      User newUser = new User("test new user",
              "testnewpassword",
              "testemail@test.com");
      newUser.getFavesongs().add(new Favesong(newUser, "testnewsong"));
      User addUser = userService.save(newUser);
      assertNotNull(addUser);
      User foundUser = userService.findUserById(addUser.getUserid());
      assertEquals(addUser.getUsername(), foundUser.getUsername());
   }
   
}