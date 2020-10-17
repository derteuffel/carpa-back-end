package com.derteuffel.controllers;


import com.derteuffel.configs.JwtUtils;
import com.derteuffel.entities.ERole;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.AuthBody;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.messages.JwtResponse;
import com.derteuffel.messages.ResponseMessage;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.repositories.UserRepository;
import com.derteuffel.services.UserDetailsImpl;
import com.derteuffel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody AuthBody authBody) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authBody.getUsername(), authBody.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROOT') or hasRole('COORDONATEUR')")
    public ResponseEntity<?> registerUser( @RequestBody RegisterPayload signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Email is already in use!"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        // Create new user's account
        User user = new User();
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setDateNaissance(sdf.format(new Date()));
        user.setMatricule(signUpRequest.getMatricule());
        user.setFullname(signUpRequest.getFullname());
        user.setFonction(signUpRequest.getFonction());
        user.setEmail(signUpRequest.getEmail());
        user.setEnabled(true);



        Set<Role> roles = new HashSet<>();

        System.out.println(signUpRequest.getRoles());
            signUpRequest.getRoles().forEach(role -> {
                switch (role) {
                    case "expert":
                        Role expertRole = roleRepository.findByName(ERole.ROLE_EXPERT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(expertRole);

                        break;
                    case "coordonateur":
                        Role coordonateurRole = roleRepository.findByName(ERole.ROLE_COORDONATEUR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(coordonateurRole);

                        break;
                    case "secretaire":
                        Role secretaireRole = roleRepository.findByName(ERole.ROLE_SECRETAIRE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(secretaireRole);

                        break;
                    case "root":
                        Role rootRole = roleRepository.findByName(ERole.ROLE_ROOT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(rootRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }



    @SuppressWarnings("rawtypes")
    @PostMapping("/register/update/{id}")
    @PreAuthorize("hasRole('ROOT') or hasRole('COORDONATEUR')")
    public ResponseEntity updateUser(@RequestBody RegisterPayload registerPayload, @PathVariable Long id){
        User user = userRepository.getOne(id);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        if (user !=null){
            user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
            user.setMatricule(registerPayload.getMatricule());
            user.setFullname(registerPayload.getFullname());
            user.setFonction(registerPayload.getFonction());
            user.setEmail(registerPayload.getEmail());
            user.setEnabled(true);
            userRepository.save(user);

        }else {
            throw new BadCredentialsException("User with id: " + id + " doesn't exists");
        }

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }



    @SuppressWarnings("rawtypes")
    @GetMapping("/register/delete/{id}")
    @PreAuthorize("hasRole('ROOT') or hasRole('COORDONATEUR')")
    public ResponseEntity deleteUser(@PathVariable Long id){
        User user = userRepository.getOne(id);
        if (user != null){
            userRepository.delete(user);
            System.out.println("utilisateur supprimer");
        }else {
            throw new BadCredentialsException("User with id: " + id + " doesn't exists");
        }

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User delete successfully");
        return ResponseEntity.ok(model);
    }
}
