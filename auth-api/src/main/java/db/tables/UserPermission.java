/*
 * This file is generated by jOOQ.
 */
package db.tables;


import db.Keys;
import db.Public;
import db.tables.records.UserPermissionRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPermission extends TableImpl<UserPermissionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_permission</code>
     */
    public static final UserPermission USER_PERMISSION = new UserPermission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserPermissionRecord> getRecordType() {
        return UserPermissionRecord.class;
    }

    /**
     * The column <code>public.user_permission.permission_id</code>.
     */
    public final TableField<UserPermissionRecord, Long> PERMISSION_ID = createField(DSL.name("permission_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.user_permission.user_id</code>.
     */
    public final TableField<UserPermissionRecord, Long> USER_ID = createField(DSL.name("user_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.user_permission.created_on</code>.
     */
    public final TableField<UserPermissionRecord, LocalDateTime> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.user_permission.created_by</code>.
     */
    public final TableField<UserPermissionRecord, String> CREATED_BY = createField(DSL.name("created_by"), SQLDataType.VARCHAR(255), this, "");

    private UserPermission(Name alias, Table<UserPermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserPermission(Name alias, Table<UserPermissionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_permission</code> table reference
     */
    public UserPermission(String alias) {
        this(DSL.name(alias), USER_PERMISSION);
    }

    /**
     * Create an aliased <code>public.user_permission</code> table reference
     */
    public UserPermission(Name alias) {
        this(alias, USER_PERMISSION);
    }

    /**
     * Create a <code>public.user_permission</code> table reference
     */
    public UserPermission() {
        this(DSL.name("user_permission"), null);
    }

    public <O extends Record> UserPermission(Table<O> child, ForeignKey<O, UserPermissionRecord> key) {
        super(child, key, USER_PERMISSION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserPermissionRecord> getPrimaryKey() {
        return Keys.USER_PERMISSION_PKEY;
    }

    @Override
    public List<ForeignKey<UserPermissionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_PERMISSION__FKBCHBPW0QCVTP69MGB54S8AADO, Keys.USER_PERMISSION__FK9OL5KPG1VT9JGKJGJ3HBVF8VP);
    }

    private transient Permission _permission;
    private transient User _user;

    /**
     * Get the implicit join path to the <code>public.permission</code> table.
     */
    public Permission permission() {
        if (_permission == null)
            _permission = new Permission(this, Keys.USER_PERMISSION__FKBCHBPW0QCVTP69MGB54S8AADO);

        return _permission;
    }

    /**
     * Get the implicit join path to the <code>public.user</code> table.
     */
    public User user() {
        if (_user == null)
            _user = new User(this, Keys.USER_PERMISSION__FK9OL5KPG1VT9JGKJGJ3HBVF8VP);

        return _user;
    }

    @Override
    public UserPermission as(String alias) {
        return new UserPermission(DSL.name(alias), this);
    }

    @Override
    public UserPermission as(Name alias) {
        return new UserPermission(alias, this);
    }

    @Override
    public UserPermission as(Table<?> alias) {
        return new UserPermission(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPermission rename(String name) {
        return new UserPermission(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPermission rename(Name name) {
        return new UserPermission(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPermission rename(Table<?> name) {
        return new UserPermission(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, LocalDateTime, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Long, ? super Long, ? super LocalDateTime, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Long, ? super Long, ? super LocalDateTime, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}