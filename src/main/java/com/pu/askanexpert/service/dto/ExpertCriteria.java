package com.pu.askanexpert.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.pu.askanexpert.domain.enumeration.Domaine;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
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
    /**
     * Class for filtering Domaine
     */
    public static class DomaineFilter extends Filter<Domaine> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private LocalDateFilter dateNaissance;

    private StringFilter adresse;

    private DomaineFilter domaine;

    private StringFilter profession;

    private LongFilter prix;

    private IntegerFilter note;

    private LongFilter numRib;

    private LongFilter disponibiliteId;

    private LongFilter historiqueAppelId;

    private LongFilter historiqueChatId;

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

    public LocalDateFilter getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public DomaineFilter getDomaine() {
        return domaine;
    }

    public void setDomaine(DomaineFilter domaine) {
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

    public IntegerFilter getNote() {
        return note;
    }

    public void setNote(IntegerFilter note) {
        this.note = note;
    }

    public LongFilter getNumRib() {
        return numRib;
    }

    public void setNumRib(LongFilter numRib) {
        this.numRib = numRib;
    }

    public LongFilter getDisponibiliteId() {
        return disponibiliteId;
    }

    public void setDisponibiliteId(LongFilter disponibiliteId) {
        this.disponibiliteId = disponibiliteId;
    }

    public LongFilter getHistoriqueAppelId() {
        return historiqueAppelId;
    }

    public void setHistoriqueAppelId(LongFilter historiqueAppelId) {
        this.historiqueAppelId = historiqueAppelId;
    }

    public LongFilter getHistoriqueChatId() {
        return historiqueChatId;
    }

    public void setHistoriqueChatId(LongFilter historiqueChatId) {
        this.historiqueChatId = historiqueChatId;
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
            Objects.equals(dateNaissance, that.dateNaissance) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(domaine, that.domaine) &&
            Objects.equals(profession, that.profession) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(note, that.note) &&
            Objects.equals(numRib, that.numRib) &&
            Objects.equals(disponibiliteId, that.disponibiliteId) &&
            Objects.equals(historiqueAppelId, that.historiqueAppelId) &&
            Objects.equals(historiqueChatId, that.historiqueChatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        dateNaissance,
        adresse,
        domaine,
        profession,
        prix,
        note,
        numRib,
        disponibiliteId,
        historiqueAppelId,
        historiqueChatId
        );
    }

    @Override
    public String toString() {
        return "ExpertCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (domaine != null ? "domaine=" + domaine + ", " : "") +
                (profession != null ? "profession=" + profession + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (numRib != null ? "numRib=" + numRib + ", " : "") +
                (disponibiliteId != null ? "disponibiliteId=" + disponibiliteId + ", " : "") +
                (historiqueAppelId != null ? "historiqueAppelId=" + historiqueAppelId + ", " : "") +
                (historiqueChatId != null ? "historiqueChatId=" + historiqueChatId + ", " : "") +
            "}";
    }

}
