package com.derteuffel.controllers;

import com.derteuffel.entities.Courrier;
import com.derteuffel.entities.ERole;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.repositories.CourrierRepository;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.repositories.UserRepository;
import com.derteuffel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id){
        return userRepository.getOne(id);
    }


    @GetMapping("/roles/{id}")
    public ResponseEntity changeRole(@PathVariable Long id, ERole role){
        User user = userRepository.getOne(id);
        Optional<Role> role1 = roleRepository.findByName(role);
        if (user != null) {
            user.getRoles().clear();
            if (role1.get() != null){

                user.getRoles().add(role1.get());
            }else {
                Role role2 = new Role();
                role2.setName(role);
                roleRepository.save(role2);
                user.getRoles().add(role2);
            }
            userRepository.save(user);
        }else {
            throw new BadCredentialsException("User with id: " + id + " doesn't exists");
        }
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User role change successfully");
        return ResponseEntity.ok(model);
    }

    @GetMapping("/courriers/{id}")
    public List<User> findAllUserForCourrier(@PathVariable Long id){
        Courrier courrier = courrierRepository.getOne(id);
        List<User> users = userRepository.findAllByCourriers_Id(courrier.getId());
        System.out.println(users.size());
        return users;
    }
}
