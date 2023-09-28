package com.app.music.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.music.domain.Arreglo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArregloDTO implements Serializable {

    private Long id;

    private String rutCliente;

    private String nombre;

    private String telefono;

    private Instant fechaRecepcion;

    private Instant fechaEntrega;

    private String nombreInstrumento;

    private String marca;

    private String modelo;

    private String numeroSerie;

    private String diagnostico;

    private String procedimiento;

    private String observaciones;

    private String valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Instant getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Instant fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Instant getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreInstrumento() {
        return nombreInstrumento;
    }

    public void setNombreInstrumento(String nombreInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArregloDTO)) {
            return false;
        }

        ArregloDTO arregloDTO = (ArregloDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, arregloDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArregloDTO{" +
            "id=" + getId() +
            ", rutCliente='" + getRutCliente() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaRecepcion='" + getFechaRecepcion() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", nombreInstrumento='" + getNombreInstrumento() + "'" +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", diagnostico='" + getDiagnostico() + "'" +
            ", procedimiento='" + getProcedimiento() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
