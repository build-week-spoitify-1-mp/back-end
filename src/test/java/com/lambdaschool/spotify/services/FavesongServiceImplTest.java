package com.lambdaschool.spotify.services;

import com.lambdaschool.spotify.SpotifyApplication;
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
public class FavesongServiceImplTest
{
   @Autowired
   private FavesongService favesongService;
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
   public void findAll()
   {
      assertEquals(2, favesongService.findAll().size());
   }

   @Test
   public void findFavesongById()
   {
      assertEquals("a1f52asdf", favesongService.findFavesongById(2).getTrackid());
   }

   @Test
   public void delete()
   {
//      favesongService.delete(2);
//      assertEquals(1, favesongService.findAll().size());
   }

   @Test
   public void save()
   {
//      Favesong newFavesong = new Favesong(userService.findUserById(1), "newtrackid");
//      assertEquals(2, userService.findUserById(1).getFavesongs().size());
   }

   @Test
   public void findByUserName()
   {
      favesongService.findByUserName("test user");
   }
}