package com.klausteles.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klausteles.todosimple.models.User;
import com.klausteles.todosimple.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public User findUserById(Long id) {

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException("Usuario nao encontrado. Id: "+ id));
    }

    @Transactional
    public User createUser(User user) {
        user.setId(null);

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUser(User user) {

        User newUser = findUserById(user.getId());
        newUser.setPassword(user.getPassword());

        return this.userRepository.save(newUser);
    }

    @Transactional
    public void deleteUser(Long id) {

        findUserById(id);

        try {
            this.userRepository.deleteById(id);

        } catch(Exception e) {
            throw new RuntimeException("Nao foi possivel deletar o usuario pois ha entidades relacionadas");
        }

    }

}
