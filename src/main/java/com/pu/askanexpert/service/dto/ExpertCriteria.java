package com.pu.askanexpert.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Expert entity. This class is used in ExpertResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /experts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExpertCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private LocalDateFilter date_naissance;

    private StringFilter adresse;

    private StringFilter domaine;

    private StringFilter profession;

    private LongFilter prix;

    private InstantFilter disponibilite;

    private IntegerFilter note;

    private LongFilter num_rib;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public LocalDateFilter getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDateFilter date_naissance) {
        this.date_naissance = date_naissance;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getDomaine() {
        return domaine;
    }

    public void setDomaine(StringFilter domaine) {
        this.domaine = domaine;
    }

    public StringFilter getProfession() {
        return profession;
    }

    public void setProfession(StringFilter profession) {
        this.profession = profession;
    }

    public LongFilter getPrix() {
        return prix;
    }

    public void setPrix(LongFilter prix) {
        this.prix = prix;
    }

    public InstantFilter getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(InstantFilter disponibilite) {
        this.disponibilite = disponibilite;
    }

    public IntegerFilter getNote() {
        return note;
    }

    public void setNote(IntegerFilter note) {
        this.note = note;
    }

    public LongFilter getNum_rib() {
        return num_rib;
    }

    public void setNum_rib(LongFilter num_rib) {
        this.num_rib = num_rib;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpertCriteria that = (ExpertCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(date_naissance, that.date_naissance) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(domaine, that.domaine) &&
            Objects.equals(profession, that.profession) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(disponibilite, that.disponibilite) &&
            Objects.equals(note, that.note) &&
            Objects.equals(num_rib, that.num_rib);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        date_naissance,
        adresse,
        domaine,
        profession,
        prix,
        disponibilite,
        note,
        num_rib
        );
    }

    @Override
    public String toString() {
        return "ExpertCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (date_naissance != null ? "date_naissance=" + date_naissance + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (domaine != null ? "domaine=" + domaine + ", " : "") +
                (profession != null ? "profession=" + profession + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
                (disponibilite != null ? "disponibilite=" + disponibilite + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (num_rib != null ? "num_rib=" + num_rib + ", " : "") +
            "}";
    }

}
