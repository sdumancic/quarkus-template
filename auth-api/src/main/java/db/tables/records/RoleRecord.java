/*
 * This file is generated by jOOQ.
 */
package db.tables.records;


import db.tables.Role;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> implements Record5<Long, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.role.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.role.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.role.role</code>.
     */
    public void setRole(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.role.role</code>.
     */
    public String getRole() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.role.rolename</code>.
     */
    public void setRolename(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.role.rolename</code>.
     */
    public String getRolename() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.role.created_on</code>.
     */
    public void setCreatedOn(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.role.created_on</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>public.role.modified_on</code>.
     */
    public void setModifiedOn(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.role.modified_on</code>.
     */
    public LocalDateTime getModifiedOn() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Role.ROLE.ID;
    }

    @Override
    public Field<String> field2() {
        return Role.ROLE.ROLE_;
    }

    @Override
    public Field<String> field3() {
        return Role.ROLE.ROLENAME;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Role.ROLE.CREATED_ON;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Role.ROLE.MODIFIED_ON;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getRole();
    }

    @Override
    public String component3() {
        return getRolename();
    }

    @Override
    public LocalDateTime component4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime component5() {
        return getModifiedOn();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getRole();
    }

    @Override
    public String value3() {
        return getRolename();
    }

    @Override
    public LocalDateTime value4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime value5() {
        return getModifiedOn();
    }

    @Override
    public RoleRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public RoleRecord value2(String value) {
        setRole(value);
        return this;
    }

    @Override
    public RoleRecord value3(String value) {
        setRolename(value);
        return this;
    }

    @Override
    public RoleRecord value4(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public RoleRecord value5(LocalDateTime value) {
        setModifiedOn(value);
        return this;
    }

    @Override
    public RoleRecord values(Long value1, String value2, String value3, LocalDateTime value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(Role.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(Long id, String role, String rolename, LocalDateTime createdOn, LocalDateTime modifiedOn) {
        super(Role.ROLE);

        setId(id);
        setRole(role);
        setRolename(rolename);
        setCreatedOn(createdOn);
        setModifiedOn(modifiedOn);
        resetChangedOnNotNull();
    }
}