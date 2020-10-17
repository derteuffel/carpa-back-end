package com.derteuffel.helpers;

import com.derteuffel.entities.Role;

import java.util.Date;
import java.util.List;

public class RegisterPayload {

    private String email;
    private String password;
    private String fullname;
    private Boolean enabled;
    private String fonction;
    private String matricule;
    private Date dateNaissance;
    private String username;
    private List<String> roles;

    public RegisterPayload() {
    }

    public RegisterPayload(String email, String password, String fullname, String username, Boolean enabled,
                           String fonction, String matricule, Date dateNaissance) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.username = username;
        this.enabled = enabled;
        this.fonction = fonction;
        this.matricule = matricule;
        this.dateNaissance = dateNaissance;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
