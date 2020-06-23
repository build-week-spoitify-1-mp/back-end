package com.lambdaschool.spotify.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "favesongs")
public class Favesong extends Auditable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long favesongid;

   @NotNull
   @Column(nullable = false)
   private String trackid;

   @ManyToOne
   @NotNull
   @JoinColumn(name = "userid")
   @JsonIgnoreProperties(value = "favesongs",
           allowSetters = true)
   private User user;

   public Favesong() {}

   public Favesong(User user,
                   String trackid)
   {
      this.trackid = trackid;
      this.user = user;
   }

   public long getFavesongid()
   {
      return favesongid;
   }

   public void setFavesongid(long favesongid)
   {
      this.favesongid = favesongid;
   }

   public String getTrackid()
   {
      return trackid;
   }

   public void setTrackid(String trackid)
   {
      this.trackid = trackid;
   }

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }
}
