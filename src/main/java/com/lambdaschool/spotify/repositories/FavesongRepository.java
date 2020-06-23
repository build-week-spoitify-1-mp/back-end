package com.lambdaschool.spotify.repositories;

import com.lambdaschool.spotify.models.Favesong;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavesongRepository extends CrudRepository<Favesong, Long>
{
   List<Favesong> findAllByUser_Username(String username);
}
