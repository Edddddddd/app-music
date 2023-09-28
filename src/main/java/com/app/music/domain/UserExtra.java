package com.app.music.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rut")
    private String rut;

    @Column(name = "telefono")
    private Integer telefono;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserExtra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return this.rut;
    }

    public UserExtra rut(String rut) {
        this.setRut(rut);
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public UserExtra telefono(Integer telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserExtra user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", rut='" + getRut() + "'" +
            ", telefono=" + getTelefono() +
            "}";
    }
}
