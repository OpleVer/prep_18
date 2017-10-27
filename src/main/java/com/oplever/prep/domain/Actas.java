package com.oplever.prep.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Actas.
 */
@Entity
@Table(name = "actas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Actas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distrito")
    private Integer distrito;

    @Column(name = "seccion")
    private String seccion;

    @Column(name = "tipo_acta")
    private String tipo_acta;

    @Column(name = "tipo_casilla")
    private String tipo_casilla;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "votos_1")
    private String votos_1;

    @Column(name = "votos_2")
    private String votos_2;

    @Column(name = "votos_3")
    private String votos_3;

    @Column(name = "validacion")
    private String validacion;

    @Column(name = "estatus")
    private Integer estatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDistrito() {
        return distrito;
    }

    public Actas distrito(Integer distrito) {
        this.distrito = distrito;
        return this;
    }

    public void setDistrito(Integer distrito) {
        this.distrito = distrito;
    }

    public String getSeccion() {
        return seccion;
    }

    public Actas seccion(String seccion) {
        this.seccion = seccion;
        return this;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTipo_acta() {
        return tipo_acta;
    }

    public Actas tipo_acta(String tipo_acta) {
        this.tipo_acta = tipo_acta;
        return this;
    }

    public void setTipo_acta(String tipo_acta) {
        this.tipo_acta = tipo_acta;
    }

    public String getTipo_casilla() {
        return tipo_casilla;
    }

    public Actas tipo_casilla(String tipo_casilla) {
        this.tipo_casilla = tipo_casilla;
        return this;
    }

    public void setTipo_casilla(String tipo_casilla) {
        this.tipo_casilla = tipo_casilla;
    }

    public String getImagen() {
        return imagen;
    }

    public Actas imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVotos_1() {
        return votos_1;
    }

    public Actas votos_1(String votos_1) {
        this.votos_1 = votos_1;
        return this;
    }

    public void setVotos_1(String votos_1) {
        this.votos_1 = votos_1;
    }

    public String getVotos_2() {
        return votos_2;
    }

    public Actas votos_2(String votos_2) {
        this.votos_2 = votos_2;
        return this;
    }

    public void setVotos_2(String votos_2) {
        this.votos_2 = votos_2;
    }

    public String getVotos_3() {
        return votos_3;
    }

    public Actas votos_3(String votos_3) {
        this.votos_3 = votos_3;
        return this;
    }

    public void setVotos_3(String votos_3) {
        this.votos_3 = votos_3;
    }

    public String getValidacion() {
        return validacion;
    }

    public Actas validacion(String validacion) {
        this.validacion = validacion;
        return this;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public Actas estatus(Integer estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
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
        Actas actas = (Actas) o;
        if (actas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Actas{" +
            "id=" + getId() +
            ", distrito='" + getDistrito() + "'" +
            ", seccion='" + getSeccion() + "'" +
            ", tipo_acta='" + getTipo_acta() + "'" +
            ", tipo_casilla='" + getTipo_casilla() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", votos_1='" + getVotos_1() + "'" +
            ", votos_2='" + getVotos_2() + "'" +
            ", votos_3='" + getVotos_3() + "'" +
            ", validacion='" + getValidacion() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
