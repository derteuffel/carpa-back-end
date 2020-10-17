package com.derteuffel.services;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(RegisterPayload registerPayload, Collection<Role> roles) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        User user = new User();
        user.setEnabled(true);
        user.setEmail(registerPayload.getEmail());
        user.setFonction(registerPayload.getFonction());
        user.setFullname(registerPayload.getFullname());
        user.setMatricule(registerPayload.getMatricule());
        user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));

        user.setPassword(passwordEncoder.encode(registerPayload.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    @Override
    public User findByEmail(String email) {
         Optional<User> user = userRepository.findByEmail(email);
         return user.get();
    }
}
