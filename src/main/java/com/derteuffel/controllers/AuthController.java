package com.derteuffel.controllers;


import com.derteuffel.configs.JwtTokenProvider;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.AuthBody;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.repositories.UserRepository;
import com.derteuffel.services.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomerUserDetailsService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String username = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register/expert")
    public ResponseEntity registerExpert(@RequestBody RegisterPayload user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveExpert(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register/root")
    public ResponseEntity registerRoot(@RequestBody RegisterPayload user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveRoot(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }


    @SuppressWarnings("rawtypes")
    @PostMapping("/register/secretaire")
    public ResponseEntity registerSecretaire(@RequestBody RegisterPayload user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveSecretaire(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register/coordonateur")
    public ResponseEntity registerCoordonateur(@RequestBody RegisterPayload user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveCoordonateur(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }


    @SuppressWarnings("rawtypes")
    @PostMapping("/register/update/{id}")
    public ResponseEntity updateUser(@RequestBody RegisterPayload registerPayload, @PathVariable Long id){
        User user = users.getOne(id);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        if (user !=null){
            user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
            user.setMatricule(registerPayload.getMatricule());
            user.setFullname(registerPayload.getFullname());
            user.setFonction(registerPayload.getFonction());
            user.setEmail(registerPayload.getEmail());
            user.setEnabled(true);
            users.save(user);

        }else {
            throw new BadCredentialsException("User with id: " + id + " doesn't exists");
        }

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }



    @SuppressWarnings("rawtypes")
    @GetMapping("/register/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        User user = users.getOne(id);
        if (user != null){
            users.delete(user);
            System.out.println("utilisateur supprimer");
        }else {
            throw new BadCredentialsException("User with id: " + id + " doesn't exists");
        }

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User delete successfully");
        return ResponseEntity.ok(model);
    }
}
