package com.derteuffel.controllers;


import com.derteuffel.entities.Courrier;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.CourrierHelper;
import com.derteuffel.repositories.CourrierRepository;
import com.derteuffel.repositories.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/courriers")
@RestController
public class CourrierController {

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public Iterable<Courrier> courriers(){
        return courrierRepository.findAll();
    }



    @GetMapping("/type/{type}")
    public List<Courrier> findAllByTypeCourrier(@PathVariable String type){
        return courrierRepository.findAllByTypeCourrier(type);
    }

    @GetMapping("/get/{id}")
    public Courrier getOne(@PathVariable Long id){
        return courrierRepository.getOne(id);
    }

    @GetMapping("/archives/{id}")
    public ResponseEntity archiver(@PathVariable Long id){
        Courrier courrier = courrierRepository.getOne(id);
        courrier.setStatus(false);
        courrierRepository.save(courrier);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Courrier archived successfully");
        return ResponseEntity.ok(model);
    }


    @GetMapping("/reference/{numero}")
    public Courrier findByReferenceNumber(@PathVariable String numero){
        return courrierRepository.findByReferenceNumber(numero);
    }

    @GetMapping("/users")
    public List<Courrier> findAllByUsers(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
       Optional<User> user = userRepository.findByEmail(principal.getName());
        return courrierRepository.findAllByUsers_Id(user.get().getId());
    }

    @GetMapping("/status/{status}")
    public List<Courrier> findAllbyStatus(@PathVariable Boolean status){
        return courrierRepository.findAllByStatus(status);
    }



    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('SECRETAIRE') or hasRole('ROOT')")
    public ResponseEntity saveCourrier(@RequestBody CourrierHelper courrierHelper, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Optional<User> existUser = userRepository.findByEmail(principal.getName());
        System.out.println("je suis la!!!");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        if (existUser != null){
            System.out.println("if test is successful");
            Courrier courrier = new Courrier();
            courrier.setObjet(courrierHelper.getObjet());
            courrier.setReceiverDate(sdf.format(courrierHelper.getReceiverDate()));
            courrier.setSenderDate(sdf.format(courrierHelper.getSenderDate()));
            courrier.setReceiverName(courrierHelper.getReceiverName());
            courrier.setSenderName(courrierHelper.getSenderName());
            courrier.setReferenceNumber(courrierHelper.getReferenceNumber());
            courrier.setTypeCourrier(courrierHelper.getTypeCourrier());
            courrier.setAddedDate(sdf.format(new Date()));
            courrier.setSaver(existUser.get().getFullname());
            courrier.setUsers(Arrays.asList(existUser.get()));
            courrier.setStatus(true);

            courrierRepository.save(courrier);
        }else {
            throw new BadCredentialsException("User with username: " + courrierHelper.getEmail() + " Didn't matches");
        }
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Courrier saved successfully");
        return ResponseEntity.ok(model);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SECRETAIRE') or hasRole('ROOT')")
    public ResponseEntity updateCourrier(@RequestBody CourrierHelper courrierHelper, @PathVariable Long id){
        System.out.println("Je suis dedans ici");
        Courrier courrier = courrierRepository.getOne(id);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            courrier.setTypeCourrier(courrierHelper.getTypeCourrier());
            courrier.setReferenceNumber(courrierHelper.getReferenceNumber());
            courrier.setSenderName(courrierHelper.getSenderName());
            courrier.setSenderDate(sdf.format(courrierHelper.getSenderDate()));
            courrier.setReceiverName(courrierHelper.getReceiverName());
            courrier.setReceiverDate(sdf.format(courrierHelper.getReceiverDate()));
            courrier.setObjet(courrierHelper.getObjet());
            courrierRepository.save(courrier);

            Map<Object, Object> model = new HashMap<>();
            model.put("message", "Courrier updated successfully");
            return ResponseEntity.ok(model);

    }

    @PostMapping("/users/add/{id}")
    @PreAuthorize("hasRole('COORDONATEUR') or hasRole('ROOT') or hasRole('EXPERT')")
    public ResponseEntity addUserToCourrier(@RequestBody ArrayList<String> utilisateurs, @PathVariable Long id){
        System.out.println(utilisateurs);
        Courrier courrier = courrierRepository.getOne(id);

            for (String email : utilisateurs){
                Optional<User> existUser = userRepository.findByEmail(email);
                if (!(courrier.getUsers().contains(existUser))){
                    courrier.getUsers().add(existUser.get());
                }else {
                    throw new BadCredentialsException("User with email: " + existUser.get().getEmail() + " Already exist");
                }
                courrierRepository.save(courrier);
            }

        Map<Object, Object> model = new HashMap<>();
        model.put("message", "Users added successfully in Courrier");
        return ResponseEntity.ok(model);
    }




}
