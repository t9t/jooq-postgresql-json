package com.github.t9t.jooq.json.testing;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

final class TestDb {
    private TestDb() {
    }

    static DataSource createDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:23719/jooq");
        ds.setUser("jooq");
        ds.setPassword("jooq");
        return ds;
    }

}
