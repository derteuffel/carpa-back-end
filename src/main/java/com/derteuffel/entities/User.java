package com.derteuffel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.thymeleaf.expression.Lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String fullname;
    private Boolean enabled;
    private String fonction;
    private String matricule;
    private String dateNaissance;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Courrier> courriers;

    public User() {
    }

    public User(String email, String password, String fullname, Boolean enabled, String fonction, String matricule, String dateNaissance) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.enabled = enabled;
        this.fonction = fonction;
        this.matricule = matricule;
        this.dateNaissance = dateNaissance;
    }


    public List<Courrier> getCourriers() {
        return courriers;
    }

    public void setCourriers(List<Courrier> courriers) {
        this.courriers = courriers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
