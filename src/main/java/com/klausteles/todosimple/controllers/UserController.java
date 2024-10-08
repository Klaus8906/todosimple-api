package com.klausteles.todosimple.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.klausteles.todosimple.models.User;
import com.klausteles.todosimple.models.User.CreateUser;
import com.klausteles.todosimple.models.User.UpdateUser;
import com.klausteles.todosimple.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {

        User user = this.userService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    @Validated(CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {

        this.userService.createUser(user);
        URI uri = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(user.getId())
                            .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id) {

        user.setId(id);
        this.userService.updateUser(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
