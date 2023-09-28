package com.app.music.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Arreglo.
 */
@Entity
@Table(name = "arreglo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Arreglo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rut_cliente")
    private String rutCliente;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_recepcion")
    private Instant fechaRecepcion;

    @Column(name = "fecha_entrega")
    private Instant fechaEntrega;

    @Column(name = "nombre_instrumento")
    private String nombreInstrumento;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "procedimiento")
    private String procedimiento;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "valor")
    private String valor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Arreglo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutCliente() {
        return this.rutCliente;
    }

    public Arreglo rutCliente(String rutCliente) {
        this.setRutCliente(rutCliente);
        return this;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Arreglo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Arreglo telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Instant getFechaRecepcion() {
        return this.fechaRecepcion;
    }

    public Arreglo fechaRecepcion(Instant fechaRecepcion) {
        this.setFechaRecepcion(fechaRecepcion);
        return this;
    }

    public void setFechaRecepcion(Instant fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Instant getFechaEntrega() {
        return this.fechaEntrega;
    }

    public Arreglo fechaEntrega(Instant fechaEntrega) {
        this.setFechaEntrega(fechaEntrega);
        return this;
    }

    public void setFechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreInstrumento() {
        return this.nombreInstrumento;
    }

    public Arreglo nombreInstrumento(String nombreInstrumento) {
        this.setNombreInstrumento(nombreInstrumento);
        return this;
    }

    public void setNombreInstrumento(String nombreInstrumento) {
        this.nombreInstrumento = nombreInstrumento;
    }

    public String getMarca() {
        return this.marca;
    }

    public Arreglo marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Arreglo modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Arreglo numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public Arreglo diagnostico(String diagnostico) {
        this.setDiagnostico(diagnostico);
        return this;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProcedimiento() {
        return this.procedimiento;
    }

    public Arreglo procedimiento(String procedimiento) {
        this.setProcedimiento(procedimiento);
        return this;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Arreglo observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getValor() {
        return this.valor;
    }

    public Arreglo valor(String valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arreglo)) {
            return false;
        }
        return id != null && id.equals(((Arreglo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arreglo{" +
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
