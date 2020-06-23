package com.lambdaschool.spotify.controllers;

import com.lambdaschool.spotify.models.Favesong;
import com.lambdaschool.spotify.services.FavesongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/favesongs")
public class FavesongController
{
   @Autowired
   FavesongService favesongService;

   @GetMapping(value = "/favesong/{favesongid}",
           produces = {"application/json"})
   public ResponseEntity<?> getFavesongById(
           @PathVariable
                   Long favesongid)
   {
      Favesong ufs = favesongService.findFavesongById(favesongid);
      return new ResponseEntity<>(ufs,
              HttpStatus.OK);
   }

   @PostMapping(value = "/user/{userid}/favesong/{trackid}")
   public ResponseEntity<?> addNewFavesong(
           @PathVariable
                   long userid,
           @PathVariable
                   String trackid)
           throws
           URISyntaxException
   {
      Favesong newFavesong = favesongService.save(userid,
              trackid);

      // set the location header for the newly created resource
      HttpHeaders responseHeaders = new HttpHeaders();
      URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
              .path("/favesongs/favesong/{favesongid}")
              .buildAndExpand(newFavesong.getFavesongid())
              .toUri();
      responseHeaders.setLocation(newUserEmailURI);

      return new ResponseEntity<>(null,
              responseHeaders,
              HttpStatus.CREATED);
   }

   @DeleteMapping(value = "/favesong/{favesongid}")
   public ResponseEntity<?> deleteUserEmailById(
           @PathVariable
                   long favesongid)
   {
      favesongService.delete(favesongid);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping(value = "/username/{userName}",
           produces = {"application/json"})
   public ResponseEntity<?> findFavesongByUserName(
           @PathVariable
                   String userName)
   {
      List<Favesong> usersFavesongs = favesongService.findByUserName(userName);
      return new ResponseEntity<>(usersFavesongs,
              HttpStatus.OK);
   }
}
