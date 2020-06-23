package com.lambdaschool.spotify.services;

import com.lambdaschool.spotify.exceptions.ResourceNotFoundException;
import com.lambdaschool.spotify.handlers.HelperFunctions;
import com.lambdaschool.spotify.models.Favesong;
import com.lambdaschool.spotify.models.User;
import com.lambdaschool.spotify.repositories.FavesongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "favesongService")
public class FavesongServiceImpl implements FavesongService
{
   @Autowired
   private FavesongRepository favesongrepos;

   @Autowired
   private UserService userService;

   @Autowired
   private HelperFunctions helper;
   @Override
   public List<Favesong> findAll()
   {
      List<Favesong> list = new ArrayList<>();
      /*
       * findAll returns an iterator set.
       * iterate over the iterator set and add each element to an array list.
       */
      favesongrepos.findAll()
              .iterator()
              .forEachRemaining(list::add);
      return list;
   }
   @Override
   public Favesong findFavesongById(long favesongid)
   {
      return favesongrepos.findById(favesongid)
              .orElseThrow(() -> new ResourceNotFoundException("Favesong with id " + favesongid + " Not Found!"));
   }

   @Transactional
   @Override
   public void delete(long favesongid)
   {
      if (favesongrepos.findById(favesongid).isPresent())
      {
         if (helper.isAuthorizedToMakeChange(favesongrepos.findById(favesongid)
                 .get()
                 .getUser()
                 .getUsername()))
         {
            favesongrepos.deleteById(favesongid);
         }
      } else
      {
         throw new ResourceNotFoundException("Favesong with id " + favesongid + " Not Found!");
      }
   }

   @Transactional
   @Override
   public Favesong save(
           long userid,
           String trackid)
   {
      User currentUser = userService.findUserById(userid);

      if (helper.isAuthorizedToMakeChange(currentUser.getUsername()))
      {
         Favesong newFavesong = new Favesong(currentUser,
                 trackid);
         return favesongrepos.save(newFavesong);
      } else
      {
         // note we should never get to this line but is needed for the compiler
         // to recognize that this exception can be thrown
         throw new ResourceNotFoundException("This user is not authorized to make change");
      }
   }

   @Override
   public List<Favesong> findByUserName(String username)
   {
      return favesongrepos.findAllByUser_Username(username.toLowerCase());
   }
}
