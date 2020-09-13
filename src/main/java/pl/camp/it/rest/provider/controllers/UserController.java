package pl.camp.it.rest.provider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.rest.provider.model.User;
import pl.camp.it.rest.provider.services.DataBaseService;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    DataBaseService dataBaseService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return dataBaseService.getUsers();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return dataBaseService.getUserById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User addUserToDatabase(@RequestBody User user) {
        int userId = this.dataBaseService.addUserToDB(user);
        User userFromDB = dataBaseService.getUserById(userId);

        return userFromDB;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable int id, @RequestHeader("important-header") String importantHeader) {

        System.out.println(importantHeader);
        boolean updateResult = this.dataBaseService.updateUser(user, id);

        ResponseEntity responseEntity;
        if (updateResult) {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("klucz", "wartość");
            responseEntity = new ResponseEntity<>(this.dataBaseService.getUserById(id), httpHeaders, HttpStatus.OK);

        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("klucz-błąd", "wartość-błąd");
            responseEntity = new ResponseEntity(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable int id) {
        this.dataBaseService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}

