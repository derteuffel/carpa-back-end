package com.derteuffel.helpers;

import java.util.Date;

public class CourrierHelper {

    private String senderName;
    private String receiverName;
    private String objet;
    private Date senderDate;
    private Date receiverDate;
    private Date addedDate = new Date();
    private String referenceNumber;
    private String typeCourrier;
    private String email;

    public CourrierHelper() {
    }

    public CourrierHelper(String senderName, String receiverName, String objet, Date senderDate,
                          Date receiverDate, Date addedDate, String referenceNumber, String typeCourrier, String email) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.objet = objet;
        this.senderDate = senderDate;
        this.receiverDate = receiverDate;
        this.addedDate = addedDate;
        this.referenceNumber = referenceNumber;
        this.typeCourrier = typeCourrier;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getSenderDate() {
        return senderDate;
    }

    public void setSenderDate(Date senderDate) {
        this.senderDate = senderDate;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTypeCourrier() {
        return typeCourrier;
    }

    public void setTypeCourrier(String typeCourrier) {
        this.typeCourrier = typeCourrier;
    }
}
