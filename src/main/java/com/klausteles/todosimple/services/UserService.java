package com.klausteles.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klausteles.todosimple.models.User;
import com.klausteles.todosimple.models.enums.ProfileEnum;
import com.klausteles.todosimple.repositories.UserRepository;
import com.klausteles.todosimple.services.exceptions.DataBindingViolationException;
import com.klausteles.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;
    
    @Autowired
    UserRepository userRepository;

    public User findUserById(Long id) {

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado. Id: "+ id));
    }

    @Transactional
    public User createUser(User user) {
        user.setId(null);
        user.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUser(User user) {

        User newUser = findUserById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));

        return this.userRepository.save(newUser);
    }

    @Transactional
    public void deleteUser(Long id) {

        findUserById(id);

        try {
            this.userRepository.deleteById(id);

        } catch(Exception e) {
            throw new DataBindingViolationException("Nao foi possivel deletar o usuario pois ha entidades relacionadas");
        }

    }

}
