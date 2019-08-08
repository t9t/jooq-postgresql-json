package com.github.t9t.jooq.json;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static org.junit.Assert.assertEquals;

public class JsonDSLIT {
    private DSLContext dsl;

    @Before
    public void setUp() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:23719/jooq");
        ds.setUser("jooq");
        ds.setPassword("jooq");

        this.dsl = DSL.using(ds, SQLDialect.POSTGRES_10);

        // Verify connection naively
        assertEquals(Integer.valueOf(5521), dsl.select(DSL.value("5521")).fetchOneInto(Integer.class));
    }

    @Test
    public void json_fieldByKey_select() {
        Record1<String> r = dsl.select(JsonDSL.fieldByKey(JSON_TEST.DATA, "json"))
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();
        assertEquals("{\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}", r.value1());
    }

    @Test
    public void json_fieldByKey_select_twoLevels() {
        Record1<String> r = dsl.select(JsonDSL.fieldByKey(JsonDSL.fieldByKey(JSON_TEST.DATA, "json"), "str"))
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();
        assertEquals("\"Hello, JSON world!\"", r.value1());
    }

    @Test
    public void jsonb_fieldByKey_select() {
        Record1<String> r = dsl.select(JsonDSL.fieldByKey(JSON_TEST.DATAB, "jsonb"))
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();
        assertEquals("{\"n\": null, \"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}}", r.value1());
    }

    @Test
    public void jsonb_fieldByKey_select_twoLevels() {
        Record1<String> r = dsl.select(JsonDSL.fieldByKey(JsonDSL.fieldByKey(JSON_TEST.DATAB, "jsonb"), "str"))
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();
        assertEquals("\"Hello, JSONB world!\"", r.value1());
    }
}
