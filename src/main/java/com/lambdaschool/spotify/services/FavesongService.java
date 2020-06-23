package com.lambdaschool.spotify.services;

import com.lambdaschool.spotify.models.Favesong;

import java.util.List;

public interface FavesongService
{
   List<Favesong> findAll();
   Favesong findFavesongById(long favesongid);
   Favesong save(long userid, String trackid);
   void delete(long favesongid);
   List<Favesong> findByUserName(String username);
}
