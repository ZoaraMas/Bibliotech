package com.dto;

public class InfoUserDto {
    private Long id; //
    private String nom; //
    private String prenom; //
    private Integer idTypeUser; //
    private String typeUser; //
    private boolean penalisation; //
    private String messagePenalisation; // 
    private int quotaTotal; //
    private int quotaEnCours; //
    private int quotaReste; // 
    private boolean inscris; // 
    private String messageInscription; //   

    public InfoUserDto() {
    }

    public InfoUserDto(long id, String nom, String prenom, Integer idTypeUser, String typeUser, boolean penalisation,
            String messagePenalisation, int quotaTotal, int quotaEnCours, int quotaReste, boolean inscris, String messageInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idTypeUser = idTypeUser;
        this.typeUser = typeUser;
        this.penalisation = penalisation;
        this.messagePenalisation = messagePenalisation;
        this.quotaTotal = quotaTotal;
        this.quotaEnCours = quotaEnCours;
        this.quotaReste = quotaReste;
        this.inscris = inscris;
        this.messageInscription = messageInscription;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public boolean isPenalisation() {
        return penalisation;
    }

    public void setPenalisation(boolean penalisation) {
        this.penalisation = penalisation;
    }

    public String getMessagePenalisation() {
        return messagePenalisation;
    }

    public void setMessagePenalisation(String messagePenalisation) {
        this.messagePenalisation = messagePenalisation;
    }

    public int getQuotaTotal() {
        return quotaTotal;
    }

    public void setQuotaTotal(int quotaTotal) {
        this.quotaTotal = quotaTotal;
    }

    public int getQuotaEnCours() {
        return quotaEnCours;
    }

    public void setQuotaEnCours(int quotaEnCours) {
        this.quotaEnCours = quotaEnCours;
    }

    public boolean isInscris() {
        return inscris;
    }

    public void setInscris(boolean inscris) {
        this.inscris = inscris;
    }

    public String getMessageInscription() {
        return messageInscription;
    }

    public void setMessageInscription(String messageInscription) {
        this.messageInscription = messageInscription;
    }

    public int getQuotaReste() {
        return quotaReste;
    }

    public void setQuotaReste(int quotaReste) {
        this.quotaReste = quotaReste;
    }

}
