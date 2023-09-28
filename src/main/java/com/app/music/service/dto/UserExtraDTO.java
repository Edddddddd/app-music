package com.app.music.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.music.domain.UserExtra} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExtraDTO implements Serializable {

    private Long id;

    private String rut;

    private Integer telefono;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtraDTO)) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userExtraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", rut='" + getRut() + "'" +
            ", telefono=" + getTelefono() +
            ", user=" + getUser() +
            "}";
    }
}
