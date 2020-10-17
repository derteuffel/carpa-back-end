package com.derteuffel.services;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.RegisterPayload;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User saveUser(RegisterPayload registerPayload, Collection<Role> roles);

    User findByEmail(String email);

}
