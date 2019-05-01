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
 * A RendezVous.
 */
@Entity
@Table(name = "rendez_vous")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RendezVous implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private LocalDate start;

    @Column(name = "jhi_end")
    private LocalDate end;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "all_day")
    private Boolean allDay;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties("rendezVous")
    private Expert expert;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public RendezVous start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public RendezVous end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public RendezVous title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isAllDay() {
        return allDay;
    }

    public RendezVous allDay(Boolean allDay) {
        this.allDay = allDay;
        return this;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getUrl() {
        return url;
    }

    public RendezVous url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Expert getExpert() {
        return expert;
    }

    public RendezVous expert(Expert expert) {
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
        RendezVous rendezVous = (RendezVous) o;
        if (rendezVous.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rendezVous.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RendezVous{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", title='" + getTitle() + "'" +
            ", allDay='" + isAllDay() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
