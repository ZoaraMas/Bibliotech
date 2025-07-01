package com.Entite;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.Utility.MyDate;
import jakarta.persistence.*;

@Entity
@Table(name = "employe", indexes = {
        @Index(name = "idx_employe_nom", columnList = "nom"),
        @Index(name = "idx_employe_email", columnList = "email")
})
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_employe", nullable = false, foreignKey = @ForeignKey(name = "FK_employe_type_employe"))
    private TypeEmploye typeEmploye;

    @Column(name = "date_embauche", nullable = false)
    private java.time.LocalDate dateEmbauche;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean actif = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Inscription> inscriptions = new ArrayList<>();

    // @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pret> prets = new ArrayList<>();

    // Constructeurs
    public Employe() {
    }

    public Employe(String nom, String prenom, String email, TypeEmploye typeEmploye, java.time.LocalDate dateEmbauche) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.typeEmploye = typeEmploye;
        this.dateEmbauche = dateEmbauche;
    }

    public boolean loginMatch(String mail, String password) {
        return this.email.equals(mail) && this.password.equals(password);
    }
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public TypeEmploye getTypeEmploye() {
        return typeEmploye;
    }

    public void setTypeEmploye(TypeEmploye typeEmploye) {
        this.typeEmploye = typeEmploye;
    }

    public java.time.LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(java.time.LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public List<Inscription> getInscriptions() {
    //     return inscriptions;
    // }

    // public void setInscriptions(List<Inscription> inscriptions) {
    //     this.inscriptions = inscriptions;
    // }

    // public List<Pret> getPrets() {
    //     return prets;
    // }

    // public void setPrets(List<Pret> prets) {
    //     this.prets = prets;
    // }
}