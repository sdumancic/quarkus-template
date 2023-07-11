package config;

import io.agroal.api.AgroalDataSource;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;

public class ContextProvider {

    @Singleton
    @Default
    public DSLContext createDefaultDSLContext(final AgroalDataSource efitDataSource) {
        return DSL.using(efitDataSource, SQLDialect.POSTGRES);
    }
}
