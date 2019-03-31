package com.pu.askanexpert.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Expert.
 */
@Entity
@Table(name = "expert")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Expert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate date_naissance;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "domaine", nullable = false)
    private String domaine;

    @NotNull
    @Column(name = "profession", nullable = false)
    private String profession;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Long prix;

    @NotNull
    @Column(name = "disponibilite", nullable = false)
    private Instant disponibilite;

    @Column(name = "note")
    private Integer note;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "num_rib", nullable = false)
    private Long num_rib;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Expert nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Expert prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public Expert date_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
        return this;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public Expert adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public Expert description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomaine() {
        return domaine;
    }

    public Expert domaine(String domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getProfession() {
        return profession;
    }

    public Expert profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Long getPrix() {
        return prix;
    }

    public Expert prix(Long prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Instant getDisponibilite() {
        return disponibilite;
    }

    public Expert disponibilite(Instant disponibilite) {
        this.disponibilite = disponibilite;
        return this;
    }

    public void setDisponibilite(Instant disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Integer getNote() {
        return note;
    }

    public Expert note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Expert photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Expert photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Long getNum_rib() {
        return num_rib;
    }

    public Expert num_rib(Long num_rib) {
        this.num_rib = num_rib;
        return this;
    }

    public void setNum_rib(Long num_rib) {
        this.num_rib = num_rib;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expert expert = (Expert) o;
        if (expert.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expert.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Expert{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", date_naissance='" + getDate_naissance() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", description='" + getDescription() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", profession='" + getProfession() + "'" +
            ", prix=" + getPrix() +
            ", disponibilite='" + getDisponibilite() + "'" +
            ", note=" + getNote() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", num_rib=" + getNum_rib() +
            "}";
    }
}
