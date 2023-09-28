package com.app.music.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.app.music.domain.Arreglo} entity. This class is used
 * in {@link com.app.music.web.rest.ArregloResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /arreglos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArregloCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rutCliente;

    private StringFilter nombre;

    private StringFilter telefono;

    private InstantFilter fechaRecepcion;

    private InstantFilter fechaEntrega;

    private StringFilter nombreInstrumento;

    private StringFilter marca;

    private StringFilter modelo;

    private StringFilter numeroSerie;

    private StringFilter diagnostico;

    private StringFilter procedimiento;

    private StringFilter observaciones;

    private StringFilter valor;

    private Boolean distinct;

    public ArregloCriteria() {}

    public ArregloCriteria(ArregloCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rutCliente = other.rutCliente == null ? null : other.rutCliente.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.fechaRecepcion = other.fechaRecepcion == null ? null : other.fechaRecepcion.copy();
        this.fechaEntrega = other.fechaEntrega == null ? null : other.fechaEntrega.copy();
        this.nombreInstrumento = other.nombreInstrumento == null ? null : other.nombreInstrumento.copy();
        this.marca = other.marca == null ? null : other.marca.copy();
        this.modelo = other.modelo == null ? null : other.modelo.copy();
        this.numeroSerie = other.numeroSerie == null ? null : other.numeroSerie.copy();
        this.diagnostico = other.diagnostico == null ? null : other.diagnostico.copy();
        this.procedimiento = other.procedimiento == null ? null : other.procedimiento.copy();
        this.observaciones = other.observaciones == null ? null : other.observaciones.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ArregloCriteria copy() {
        return new ArregloCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRutCliente() {
        return rutCliente;
    }

    public StringFilter rutCliente() {
        if (rutCliente == null) {
            rutCliente = new StringFilter();
        }
        return rutCliente;
    }

    public void setRutCliente(StringFilter rutCliente) {
        this.rutCliente = rutCliente;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public StringFilter nombre() {
        if (nombre == null) {
            nombre = new StringFilter();
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public StringFilter telefono() {
        if (telefono == null) {
            telefono = new StringFilter();
        }
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public InstantFilter getFechaRecepcion() {
        return fechaRecepcion;
    }

    public InstantFilter fechaRecepcion() {
        if (fechaRecepcion == null) {
            fechaRecepcion = new InstantFilter();
        }
        return fechaRecepcion;
    }

    public void setFechaRecepcion(InstantFilter fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public InstantFilter getFechaEntrega() {
        return fechaEntrega;
    }

    public InstantFilter fechaEntrega() {
        if (fechaEntrega == null) {
            fechaEntrega = new InstantFilter();
        }
        return fechaEntrega;
    }

    public void setFechaEntrega(InstantFilter fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public StringFilter getNombreInstrumento() {
        return nombreInstrumento;
    }

    public StringFilter nombreInstrumento() {
        if (nombreInstrumento == null) {
            nombreInstrumento = new StringFilter();
        }
        return nombreInstrumento;
    }

    public void setNombreInstrumento(StringFilter nombreInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
    }

    public StringFilter getMarca() {
        return marca;
    }

    public StringFilter marca() {
        if (marca == null) {
            marca = new StringFilter();
        }
        return marca;
    }

    public void setMarca(StringFilter marca) {
        this.marca = marca;
    }

    public StringFilter getModelo() {
        return modelo;
    }

    public StringFilter modelo() {
        if (modelo == null) {
            modelo = new StringFilter();
        }
        return modelo;
    }

    public void setModelo(StringFilter modelo) {
        this.modelo = modelo;
    }

    public StringFilter getNumeroSerie() {
        return numeroSerie;
    }

    public StringFilter numeroSerie() {
        if (numeroSerie == null) {
            numeroSerie = new StringFilter();
        }
        return numeroSerie;
    }

    public void setNumeroSerie(StringFilter numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public StringFilter getDiagnostico() {
        return diagnostico;
    }

    public StringFilter diagnostico() {
        if (diagnostico == null) {
            diagnostico = new StringFilter();
        }
        return diagnostico;
    }

    public void setDiagnostico(StringFilter diagnostico) {
        this.diagnostico = diagnostico;
    }

    public StringFilter getProcedimiento() {
        return procedimiento;
    }

    public StringFilter procedimiento() {
        if (procedimiento == null) {
            procedimiento = new StringFilter();
        }
        return procedimiento;
    }

    public void setProcedimiento(StringFilter procedimiento) {
        this.procedimiento = procedimiento;
    }

    public StringFilter getObservaciones() {
        return observaciones;
    }

    public StringFilter observaciones() {
        if (observaciones == null) {
            observaciones = new StringFilter();
        }
        return observaciones;
    }

    public void setObservaciones(StringFilter observaciones) {
        this.observaciones = observaciones;
    }

    public StringFilter getValor() {
        return valor;
    }

    public StringFilter valor() {
        if (valor == null) {
            valor = new StringFilter();
        }
        return valor;
    }

    public void setValor(StringFilter valor) {
        this.valor = valor;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArregloCriteria that = (ArregloCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rutCliente, that.rutCliente) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaRecepcion, that.fechaRecepcion) &&
            Objects.equals(fechaEntrega, that.fechaEntrega) &&
            Objects.equals(nombreInstrumento, that.nombreInstrumento) &&
            Objects.equals(marca, that.marca) &&
            Objects.equals(modelo, that.modelo) &&
            Objects.equals(numeroSerie, that.numeroSerie) &&
            Objects.equals(diagnostico, that.diagnostico) &&
            Objects.equals(procedimiento, that.procedimiento) &&
            Objects.equals(observaciones, that.observaciones) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            rutCliente,
            nombre,
            telefono,
            fechaRecepcion,
            fechaEntrega,
            nombreInstrumento,
            marca,
            modelo,
            numeroSerie,
            diagnostico,
            procedimiento,
            observaciones,
            valor,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArregloCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rutCliente != null ? "rutCliente=" + rutCliente + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (telefono != null ? "telefono=" + telefono + ", " : "") +
            (fechaRecepcion != null ? "fechaRecepcion=" + fechaRecepcion + ", " : "") +
            (fechaEntrega != null ? "fechaEntrega=" + fechaEntrega + ", " : "") +
            (nombreInstrumento != null ? "nombreInstrumento=" + nombreInstrumento + ", " : "") +
            (marca != null ? "marca=" + marca + ", " : "") +
            (modelo != null ? "modelo=" + modelo + ", " : "") +
            (numeroSerie != null ? "numeroSerie=" + numeroSerie + ", " : "") +
            (diagnostico != null ? "diagnostico=" + diagnostico + ", " : "") +
            (procedimiento != null ? "procedimiento=" + procedimiento + ", " : "") +
            (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
