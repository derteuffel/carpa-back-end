package com.derteuffel.controllers;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;



}
