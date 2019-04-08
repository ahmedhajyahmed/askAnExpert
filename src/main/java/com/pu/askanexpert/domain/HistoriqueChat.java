package com.pu.askanexpert.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HistoriqueChat.
 */
@Entity
@Table(name = "historique_chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistoriqueChat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_appel", nullable = false)
    private LocalDate dateAppel;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "nom_client", nullable = false)
    private String nomClient;

    @ManyToOne
    @JsonIgnoreProperties("historiqueChats")
    private Expert expert;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAppel() {
        return dateAppel;
    }

    public HistoriqueChat dateAppel(LocalDate dateAppel) {
        this.dateAppel = dateAppel;
        return this;
    }

    public void setDateAppel(LocalDate dateAppel) {
        this.dateAppel = dateAppel;
    }

    public String getDescription() {
        return description;
    }

    public HistoriqueChat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomClient() {
        return nomClient;
    }

    public HistoriqueChat nomClient(String nomClient) {
        this.nomClient = nomClient;
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Expert getExpert() {
        return expert;
    }

    public HistoriqueChat expert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
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
        HistoriqueChat historiqueChat = (HistoriqueChat) o;
        if (historiqueChat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiqueChat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoriqueChat{" +
            "id=" + getId() +
            ", dateAppel='" + getDateAppel() + "'" +
            ", description='" + getDescription() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            "}";
    }
}
