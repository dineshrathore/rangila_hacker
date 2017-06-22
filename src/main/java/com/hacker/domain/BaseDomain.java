package com.hacker.domain;

/**
 * Created by dinesh.rathore on 22/02/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.activejpa.entity.Filter;
import org.activejpa.entity.Model;

import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PrePersist;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseDomain extends Model {

    @JsonIgnore
    private Timestamp createdAt;

    public static <T extends Model> T one(Class<T> clazz, Filter filter) throws NoResultException, NonUniqueResultException {
        return createQuery(clazz, filter).getSingleResult();
    }

    /**
     * Helper method to determine whether ONE model with specific filter exists
     *
     * @param clazz
     * @param filter
     * @param <T>
     * @return exist
     */
    public static <T extends Model> boolean exists(Class<T> clazz, Filter filter) {
        try {
            one(clazz, filter);
            return true;
        } catch (NonUniqueResultException nure) {
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void preSave() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

}
