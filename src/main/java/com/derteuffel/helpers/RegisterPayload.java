package com.derteuffel.helpers;

import java.util.Date;

public class RegisterPayload {

    private String email;
    private String password;
    private String fullname;
    private Boolean enabled;
    private String fonction;
    private String matricule;
    private Date dateNaissance;

    public RegisterPayload() {
    }

    public RegisterPayload(String email, String password, String fullname, Boolean enabled, String fonction, String matricule, Date dateNaissance) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.enabled = enabled;
        this.fonction = fonction;
        this.matricule = matricule;
        this.dateNaissance = dateNaissance;
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
