/*
 * This file is generated by jOOQ.
 */
package db.tables.records;


import db.tables.User;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record7<LocalDateTime, Long, LocalDateTime, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user.created_on</code>.
     */
    public void setCreatedOn(LocalDateTime value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user.created_on</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(0);
    }

    /**
     * Setter for <code>public.user.id</code>.
     */
    public void setId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user.id</code>.
     */
    public Long getId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.user.modified_on</code>.
     */
    public void setModifiedOn(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user.modified_on</code>.
     */
    public LocalDateTime getModifiedOn() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.user.first_name</code>.
     */
    public void setFirstName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user.hashedpassword</code>.
     */
    public void setHashedpassword(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user.hashedpassword</code>.
     */
    public String getHashedpassword() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.user.last_name</code>.
     */
    public void setLastName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user.last_name</code>.
     */
    public String getLastName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.user.username</code>.
     */
    public void setUsername(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.user.username</code>.
     */
    public String getUsername() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<LocalDateTime, Long, LocalDateTime, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<LocalDateTime, Long, LocalDateTime, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<LocalDateTime> field1() {
        return User.USER.CREATED_ON;
    }

    @Override
    public Field<Long> field2() {
        return User.USER.ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return User.USER.MODIFIED_ON;
    }

    @Override
    public Field<String> field4() {
        return User.USER.FIRST_NAME;
    }

    @Override
    public Field<String> field5() {
        return User.USER.HASHEDPASSWORD;
    }

    @Override
    public Field<String> field6() {
        return User.USER.LAST_NAME;
    }

    @Override
    public Field<String> field7() {
        return User.USER.USERNAME;
    }

    @Override
    public LocalDateTime component1() {
        return getCreatedOn();
    }

    @Override
    public Long component2() {
        return getId();
    }

    @Override
    public LocalDateTime component3() {
        return getModifiedOn();
    }

    @Override
    public String component4() {
        return getFirstName();
    }

    @Override
    public String component5() {
        return getHashedpassword();
    }

    @Override
    public String component6() {
        return getLastName();
    }

    @Override
    public String component7() {
        return getUsername();
    }

    @Override
    public LocalDateTime value1() {
        return getCreatedOn();
    }

    @Override
    public Long value2() {
        return getId();
    }

    @Override
    public LocalDateTime value3() {
        return getModifiedOn();
    }

    @Override
    public String value4() {
        return getFirstName();
    }

    @Override
    public String value5() {
        return getHashedpassword();
    }

    @Override
    public String value6() {
        return getLastName();
    }

    @Override
    public String value7() {
        return getUsername();
    }

    @Override
    public UserRecord value1(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public UserRecord value2(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value3(LocalDateTime value) {
        setModifiedOn(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setHashedpassword(value);
        return this;
    }

    @Override
    public UserRecord value6(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public UserRecord value7(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UserRecord values(LocalDateTime value1, Long value2, LocalDateTime value3, String value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(LocalDateTime createdOn, Long id, LocalDateTime modifiedOn, String firstName, String hashedpassword, String lastName, String username) {
        super(User.USER);

        setCreatedOn(createdOn);
        setId(id);
        setModifiedOn(modifiedOn);
        setFirstName(firstName);
        setHashedpassword(hashedpassword);
        setLastName(lastName);
        setUsername(username);
        resetChangedOnNotNull();
    }
}
