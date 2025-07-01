package com.Entite;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.Utility.MyDate;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private java.time.LocalDate dateNaissance;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_user", nullable = false, foreignKey = @ForeignKey(name = "FK_employe_type_employe"))
    private TypeUser typeUser;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Inscription> inscriptions = new ArrayList<>();

    // @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pret> prets = new ArrayList<>();

    // Constructeurs
    public User() {
    }

    
    public boolean loginMatch(String mail, String password) {
        return this.email.equals(mail) && this.password.equals(password);
    }


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


    public java.time.LocalDate getDateNaissance() {
        return dateNaissance;
    }


    public void setDateNaissance(java.time.LocalDate dateNaissance) {
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


    public String getTelephone() {
        return telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public TypeUser getTypeUser() {
        return typeUser;
    }


    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}