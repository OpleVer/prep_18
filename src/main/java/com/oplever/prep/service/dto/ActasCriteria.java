package com.oplever.prep.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Actas entity. This class is used in ActasResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /actas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActasCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter distrito;

    private StringFilter seccion;

    private StringFilter tipo_acta;

    private StringFilter tipo_casilla;

    private StringFilter imagen;

    private StringFilter votos_1;

    private StringFilter votos_2;

    private StringFilter votos_3;

    private StringFilter validacion;

    private IntegerFilter estatus;

    public ActasCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getDistrito() {
        return distrito;
    }

    public void setDistrito(IntegerFilter distrito) {
        this.distrito = distrito;
    }

    public StringFilter getSeccion() {
        return seccion;
    }

    public void setSeccion(StringFilter seccion) {
        this.seccion = seccion;
    }

    public StringFilter getTipo_acta() {
        return tipo_acta;
    }

    public void setTipo_acta(StringFilter tipo_acta) {
        this.tipo_acta = tipo_acta;
    }

    public StringFilter getTipo_casilla() {
        return tipo_casilla;
    }

    public void setTipo_casilla(StringFilter tipo_casilla) {
        this.tipo_casilla = tipo_casilla;
    }

    public StringFilter getImagen() {
        return imagen;
    }

    public void setImagen(StringFilter imagen) {
        this.imagen = imagen;
    }

    public StringFilter getVotos_1() {
        return votos_1;
    }

    public void setVotos_1(StringFilter votos_1) {
        this.votos_1 = votos_1;
    }

    public StringFilter getVotos_2() {
        return votos_2;
    }

    public void setVotos_2(StringFilter votos_2) {
        this.votos_2 = votos_2;
    }

    public StringFilter getVotos_3() {
        return votos_3;
    }

    public void setVotos_3(StringFilter votos_3) {
        this.votos_3 = votos_3;
    }

    public StringFilter getValidacion() {
        return validacion;
    }

    public void setValidacion(StringFilter validacion) {
        this.validacion = validacion;
    }

    public IntegerFilter getEstatus() {
        return estatus;
    }

    public void setEstatus(IntegerFilter estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "ActasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (distrito != null ? "distrito=" + distrito + ", " : "") +
                (seccion != null ? "seccion=" + seccion + ", " : "") +
                (tipo_acta != null ? "tipo_acta=" + tipo_acta + ", " : "") +
                (tipo_casilla != null ? "tipo_casilla=" + tipo_casilla + ", " : "") +
                (imagen != null ? "imagen=" + imagen + ", " : "") +
                (votos_1 != null ? "votos_1=" + votos_1 + ", " : "") +
                (votos_2 != null ? "votos_2=" + votos_2 + ", " : "") +
                (votos_3 != null ? "votos_3=" + votos_3 + ", " : "") +
                (validacion != null ? "validacion=" + validacion + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
            "}";
    }

}
