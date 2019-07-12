package com.github.t9t.jooq.jsonb;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import static org.junit.Assert.assertEquals;

public class JsonbStringBindingIT {
    private DSLContext dsl;

    @Before
    public void setUp() throws Exception {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:23719/jooq");
        ds.setUser("jooq");
        ds.setPassword("jooq");

        this.dsl = DSL.using(ds, SQLDialect.POSTGRES_10);

        // Verify connection naively
        assertEquals(Integer.valueOf(5521), dsl.select(DSL.value("5521")).fetchOneInto(Integer.class));
    }

    @Test
    public void testJooq() throws Exception {
        dsl.execute("drop table if exists test");
        dsl.execute("create table if not exists test (x text)");

        assertEquals(1, dsl.insertInto(DSL.table("test"))
                .set(DSL.field("x", String.class), "Hello, world!")
                .execute());

        assertEquals("Hello, world!", dsl.select(DSL.field("x", String.class)).from(DSL.table("test")).fetchOneInto(String.class));
    }
}
