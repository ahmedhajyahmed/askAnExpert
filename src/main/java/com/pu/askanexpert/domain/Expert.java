package com.pu.askanexpert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.pu.askanexpert.domain.enumeration.Domaine;

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

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "domaine", nullable = false)
    private Domaine domaine;

    @NotNull
    @Column(name = "profession", nullable = false)
    private String profession;

    @NotNull
    @Column(name = "skill_1", nullable = false)
    private String skill1;

    @Column(name = "skill_2")
    private String skill2;

    @Column(name = "skill_3")
    private String skill3;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Long prix;

    @Column(name = "note")
    private Integer note;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "num_rib", nullable = false)
    private Long numRib;

    @OneToMany(mappedBy = "expert")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NonDisponibilite> disponibilites = new HashSet<>();
    @OneToMany(mappedBy = "expert")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HistoriqueAppel> historiqueAppels = new HashSet<>();
    @OneToMany(mappedBy = "expert")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HistoriqueChat> historiqueChats = new HashSet<>();
    @OneToMany(mappedBy = "expert")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RendezVous> rendezVous = new HashSet<>();
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Expert dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public Domaine getDomaine() {
        return domaine;
    }

    public Expert domaine(Domaine domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(Domaine domaine) {
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

    public String getSkill1() {
        return skill1;
    }

    public Expert skill1(String skill1) {
        this.skill1 = skill1;
        return this;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public Expert skill2(String skill2) {
        this.skill2 = skill2;
        return this;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public Expert skill3(String skill3) {
        this.skill3 = skill3;
        return this;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
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

    public Long getNumRib() {
        return numRib;
    }

    public Expert numRib(Long numRib) {
        this.numRib = numRib;
        return this;
    }

    public void setNumRib(Long numRib) {
        this.numRib = numRib;
    }

    public Set<NonDisponibilite> getDisponibilites() {
        return disponibilites;
    }

    public Expert disponibilites(Set<NonDisponibilite> nonDisponibilites) {
        this.disponibilites = nonDisponibilites;
        return this;
    }

    public Expert addDisponibilite(NonDisponibilite nonDisponibilite) {
        this.disponibilites.add(nonDisponibilite);
        nonDisponibilite.setExpert(this);
        return this;
    }

    public Expert removeDisponibilite(NonDisponibilite nonDisponibilite) {
        this.disponibilites.remove(nonDisponibilite);
        nonDisponibilite.setExpert(null);
        return this;
    }

    public void setDisponibilites(Set<NonDisponibilite> nonDisponibilites) {
        this.disponibilites = nonDisponibilites;
    }

    public Set<HistoriqueAppel> getHistoriqueAppels() {
        return historiqueAppels;
    }

    public Expert historiqueAppels(Set<HistoriqueAppel> historiqueAppels) {
        this.historiqueAppels = historiqueAppels;
        return this;
    }

    public Expert addHistoriqueAppel(HistoriqueAppel historiqueAppel) {
        this.historiqueAppels.add(historiqueAppel);
        historiqueAppel.setExpert(this);
        return this;
    }

    public Expert removeHistoriqueAppel(HistoriqueAppel historiqueAppel) {
        this.historiqueAppels.remove(historiqueAppel);
        historiqueAppel.setExpert(null);
        return this;
    }

    public void setHistoriqueAppels(Set<HistoriqueAppel> historiqueAppels) {
        this.historiqueAppels = historiqueAppels;
    }

    public Set<HistoriqueChat> getHistoriqueChats() {
        return historiqueChats;
    }

    public Expert historiqueChats(Set<HistoriqueChat> historiqueChats) {
        this.historiqueChats = historiqueChats;
        return this;
    }

    public Expert addHistoriqueChat(HistoriqueChat historiqueChat) {
        this.historiqueChats.add(historiqueChat);
        historiqueChat.setExpert(this);
        return this;
    }

    public Expert removeHistoriqueChat(HistoriqueChat historiqueChat) {
        this.historiqueChats.remove(historiqueChat);
        historiqueChat.setExpert(null);
        return this;
    }

    public void setHistoriqueChats(Set<HistoriqueChat> historiqueChats) {
        this.historiqueChats = historiqueChats;
    }

    public Set<RendezVous> getRendezVous() {
        return rendezVous;
    }

    public Expert rendezVous(Set<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
        return this;
    }

    public Expert addRendezVous(RendezVous rendezVous) {
        this.rendezVous.add(rendezVous);
        rendezVous.setExpert(this);
        return this;
    }

    public Expert removeRendezVous(RendezVous rendezVous) {
        this.rendezVous.remove(rendezVous);
        rendezVous.setExpert(null);
        return this;
    }

    public void setRendezVous(Set<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
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
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", description='" + getDescription() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", profession='" + getProfession() + "'" +
            ", skill1='" + getSkill1() + "'" +
            ", skill2='" + getSkill2() + "'" +
            ", skill3='" + getSkill3() + "'" +
            ", prix=" + getPrix() +
            ", note=" + getNote() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", numRib=" + getNumRib() +
            "}";
    }
}
