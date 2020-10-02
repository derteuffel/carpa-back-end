package com.derteuffel.controllers;

import com.derteuffel.entities.Courrier;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.repositories.CourrierRepository;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourrierRepository courrierRepository;

    @GetMapping("")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id){
        return userRepository.getOne(id);
    }

    @GetMapping("/roles/{name}/{id}")
    public ResponseEntity changeRole(@PathVariable Long id, @PathVariable String name){
        User user = userRepository.getOne(id);
        if (user != null) {
            Role role = roleRepository.findByName(name);
            user.getRoles().clear();
            if (role!= null) {
                user.getRoles().add(role);
            }else {
                Role role1 = new Role();
                role1.setName(name);
                roleRepository.save(role1);
                user.getRoles().add(role1);
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
