package com.lambdaschool.spotify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@ApiModel(value = "User",
        description = "Yes, this is an actual user")
@Entity
@Table(name = "users")
public class User
        extends Auditable
{
    @ApiModelProperty(name = "user id",
            value = "primary key for User",
            required = true,
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @ApiModelProperty(name = "User Name",
            value = "Actual user name for sign on",
            required = true,
            example = "Some Name")
    @Size(min = 2,
            max = 30,
            message = "User Name must be between 2 and 30 characters")
    @NotNull
    @Column(nullable = false,
            unique = true)
    private String username;


    @ApiModelProperty(name = "password",
            value = "The password for this user",
            required = true,
            example = "ILuvM4th!")
    @Size(min = 4,
            message = "Password must 4 or more characters")
    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(name = "primary email",
            value = "The email for this user",
            required = true,
            example = "john@lambdaschool.com")
    @NotNull
    @Column(nullable = false,
            unique = true)
    @Email
    private String email;

    @ApiModelProperty(name = "fave songs",
            value = "List of fave songs for this user")
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
            allowSetters = true)
    private List<Favesong> favesongs = new ArrayList<>();

    public User()
    {
    }

    public User(
            String username,
            String password,
            String email)
    {
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }


    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        if (username == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return username.toLowerCase();
        }
    }

    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    public String getEmail()
    {
        if (email == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return email.toLowerCase();
        }
    }

    public void setEmail(String email)
    {
        this.email = email.toLowerCase();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public List<Favesong> getFavesongs()
    {
        return favesongs;
    }

    public void setFavesongs(List<Favesong> favesongs)
    {
        this.favesongs = favesongs;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        String myRole = "ROLE_USER";
        rtnList.add(new SimpleGrantedAuthority(myRole));

        return rtnList;
    }
}
