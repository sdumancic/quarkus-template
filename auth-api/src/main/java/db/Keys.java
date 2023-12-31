/*
 * This file is generated by jOOQ.
 */
package db;


import db.tables.Permission;
import db.tables.Role;
import db.tables.User;
import db.tables.UserPermission;
import db.tables.UserRole;
import db.tables.records.PermissionRecord;
import db.tables.records.RoleRecord;
import db.tables.records.UserPermissionRecord;
import db.tables.records.UserRecord;
import db.tables.records.UserRoleRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PermissionRecord> PERMISSION_PKEY = Internal.createUniqueKey(Permission.PERMISSION, DSL.name("permission_pkey"), new TableField[] { Permission.PERMISSION.ID }, true);
    public static final UniqueKey<RoleRecord> ROLE_PKEY = Internal.createUniqueKey(Role.ROLE, DSL.name("role_pkey"), new TableField[] { Role.ROLE.ID }, true);
    public static final UniqueKey<UserRecord> USER_PKEY = Internal.createUniqueKey(User.USER, DSL.name("user_pkey"), new TableField[] { User.USER.ID }, true);
    public static final UniqueKey<UserPermissionRecord> USER_PERMISSION_PKEY = Internal.createUniqueKey(UserPermission.USER_PERMISSION, DSL.name("user_permission_pkey"), new TableField[] { UserPermission.USER_PERMISSION.PERMISSION_ID, UserPermission.USER_PERMISSION.USER_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<UserPermissionRecord, UserRecord> USER_PERMISSION__FK9OL5KPG1VT9JGKJGJ3HBVF8VP = Internal.createForeignKey(UserPermission.USER_PERMISSION, DSL.name("fk9ol5kpg1vt9jgkjgj3hbvf8vp"), new TableField[] { UserPermission.USER_PERMISSION.USER_ID }, Keys.USER_PKEY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<UserPermissionRecord, PermissionRecord> USER_PERMISSION__FKBCHBPW0QCVTP69MGB54S8AADO = Internal.createForeignKey(UserPermission.USER_PERMISSION, DSL.name("fkbchbpw0qcvtp69mgb54s8aado"), new TableField[] { UserPermission.USER_PERMISSION.PERMISSION_ID }, Keys.PERMISSION_PKEY, new TableField[] { Permission.PERMISSION.ID }, true);
    public static final ForeignKey<UserRoleRecord, UserRecord> USER_ROLE__FK859N2JVI8IVHUI0RL0ESWS6O = Internal.createForeignKey(UserRole.USER_ROLE, DSL.name("fk859n2jvi8ivhui0rl0esws6o"), new TableField[] { UserRole.USER_ROLE.USER_ID }, Keys.USER_PKEY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<UserRoleRecord, RoleRecord> USER_ROLE__FKA68196081FVOVJHKEK5M97N3Y = Internal.createForeignKey(UserRole.USER_ROLE, DSL.name("fka68196081fvovjhkek5m97n3y"), new TableField[] { UserRole.USER_ROLE.ROLE_ID }, Keys.ROLE_PKEY, new TableField[] { Role.ROLE.ID }, true);
}
