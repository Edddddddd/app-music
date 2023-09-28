package com.app.music.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.app.music.domain.UserExtra} entity. This class is used
 * in {@link com.app.music.web.rest.UserExtraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-extras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExtraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rut;

    private IntegerFilter telefono;

    private LongFilter userId;

    private Boolean distinct;

    public UserExtraCriteria() {}

    public UserExtraCriteria(UserExtraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rut = other.rut == null ? null : other.rut.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserExtraCriteria copy() {
        return new UserExtraCriteria(this);
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

    public StringFilter getRut() {
        return rut;
    }

    public StringFilter rut() {
        if (rut == null) {
            rut = new StringFilter();
        }
        return rut;
    }

    public void setRut(StringFilter rut) {
        this.rut = rut;
    }

    public IntegerFilter getTelefono() {
        return telefono;
    }

    public IntegerFilter telefono() {
        if (telefono == null) {
            telefono = new IntegerFilter();
        }
        return telefono;
    }

    public void setTelefono(IntegerFilter telefono) {
        this.telefono = telefono;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final UserExtraCriteria that = (UserExtraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rut, that.rut) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rut, telefono, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rut != null ? "rut=" + rut + ", " : "") +
            (telefono != null ? "telefono=" + telefono + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
