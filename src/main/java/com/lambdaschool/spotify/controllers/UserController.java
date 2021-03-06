package com.lambdaschool.spotify.controllers;

import com.lambdaschool.spotify.models.ErrorDetail;
import com.lambdaschool.spotify.models.User;
import com.lambdaschool.spotify.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "returns all Users",
            response = User.class,
            responseContainer = "List")
    @GetMapping(value = "/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                                    HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve a user based of off user id",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @GetMapping(value = "/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "User id",
                    required = true,
                    example = "4")
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    @ApiOperation(value = "returns the user with the given username",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @GetMapping(value = "/user/name/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
            @ApiParam(value = "user name",
                    required = true,
                    example = "johnmitchell")
            @PathVariable
                    String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    @ApiOperation(value = "returns all Users whose username contains the given substring",
            response = User.class,
            responseContainer = "List")
    @ApiParam(value = "User Name Substring",
            required = true,
            example = "john")
    @GetMapping(value = "/user/name/like/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    @ApiOperation(value = "adds a user given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PostMapping(value = "/createnewuser",
            consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(
            @Valid
            @RequestBody
                    User newuser)
            throws
            URISyntaxException
    {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    @ApiOperation(value = "updates a user given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PutMapping(value = "/user/{userid}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateFullUser(
            @Valid
            @ApiParam(value = "a full user object",
                    required = true)
            @RequestBody
                    User updateUser,
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long userid)
    {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "updates a user with the information given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PatchMapping(value = "/user/{id}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "a user object with just the information needed to be updated",
                    required = true)
            @RequestBody
                    User updateUser,
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long id)
    {
        userService.update(updateUser,
                           id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes the given user",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "returns the currently authenticated user",
            response = User.class)
    @GetMapping(value = "/getuserinfo",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }
}