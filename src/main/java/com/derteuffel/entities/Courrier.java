package com.derteuffel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "courriers")
public class Courrier implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String senderName;
    private String receiverName;
    private String objet;
    private String senderDate;
    private String receiverDate;
    private String addedDate;
    private String referenceNumber;
    private String typeCourrier;

    private String saver;
    private Boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "courrier_user",
            joinColumns = @JoinColumn(name = "courrier_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users;


    public Courrier() {
    }

    public Courrier(String senderName, String receiverName, String objet, String senderDate, String receiverDate,
                    String addedDate, String referenceNumber, String typeCourrier, String saver, Boolean status) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.objet = objet;
        this.senderDate = senderDate;
        this.receiverDate = receiverDate;
        this.addedDate = addedDate;
        this.referenceNumber = referenceNumber;
        this.typeCourrier = typeCourrier;
        this.saver = saver;
        this.status = status;
    }


    public String getSaver() {
        return saver;
    }

    public void setSaver(String saver) {
        this.saver = saver;
    }

    public String getTypeCourrier() {
        return typeCourrier;
    }

    public void setTypeCourrier(String typeCourrier) {
        this.typeCourrier = typeCourrier;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getSenderDate() {
        return senderDate;
    }

    public void setSenderDate(String senderDate) {
        this.senderDate = senderDate;
    }

    public String getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(String receiverDate) {
        this.receiverDate = receiverDate;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
