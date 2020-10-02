package com.derteuffel.services;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.helpers.RegisterPayload;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveExpert(RegisterPayload registerPayload) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode("1234567890"));
        user.setEnabled(true);
        user.setEmail(registerPayload.getEmail());
        user.setFonction(registerPayload.getFonction());
        user.setFullname(registerPayload.getFullname());
        user.setMatricule(registerPayload.getMatricule());
        user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
        Role userRole = roleRepository.findByName("ROLE_EXPERT");
        if (userRole != null) {
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }else {
            Role newExpert = new Role();
            newExpert.setName("ROLE_EXPERT");
            roleRepository.save(newExpert);
            user.setRoles(new HashSet<>(Arrays.asList(newExpert)));
        }
        userRepository.save(user);
    }
    public void saveCoordonateur(RegisterPayload registerPayload) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode("1234567890"));
        user.setEnabled(true);
        user.setEmail(registerPayload.getEmail());
        user.setFonction(registerPayload.getFonction());
        user.setFullname(registerPayload.getFullname());
        user.setMatricule(registerPayload.getMatricule());
        user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
        Role userRole = roleRepository.findByName("ROLE_COORDONATEUR");
        if (userRole != null) {
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }else {
            Role newCoordonateur = new Role();
            newCoordonateur.setName("ROLE_COORDONATEUR");
            roleRepository.save(newCoordonateur);
            user.setRoles(new HashSet<>(Arrays.asList(newCoordonateur)));
        }
        userRepository.save(user);
    }

    public void saveSecretaire(RegisterPayload registerPayload) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode("1234567890"));
        user.setEnabled(true);
        user.setEmail(registerPayload.getEmail());
        user.setFonction(registerPayload.getFonction());
        user.setFullname(registerPayload.getFullname());
        user.setMatricule(registerPayload.getMatricule());
        user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
        Role userRole = roleRepository.findByName("ROLE_SECRETAIRE");
        if (userRole != null) {
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }else {
            Role newSecretaire = new Role();
            newSecretaire.setName("ROLE_SECRETAIRE");
            roleRepository.save(newSecretaire);
            user.setRoles(new HashSet<>(Arrays.asList(newSecretaire)));
        }
        userRepository.save(user);
    }

    public void saveRoot(RegisterPayload registerPayload) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode("1234567890"));
        user.setEnabled(true);
        user.setEmail(registerPayload.getEmail());
        user.setFonction(registerPayload.getFonction());
        user.setFullname(registerPayload.getFullname());
        user.setMatricule(registerPayload.getMatricule());
        user.setDateNaissance(sdf.format(registerPayload.getDateNaissance()));
        Role userRole = roleRepository.findByName("ROLE_ROOT");
        if (userRole != null) {
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }else {
            Role newRoot = new Role();
            newRoot.setName("ROLE_ROOT");
            roleRepository.save(newRoot);
            user.setRoles(new HashSet<>(Arrays.asList(newRoot)));
        }
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        /*userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });*/

        for (Role role : userRoles){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
