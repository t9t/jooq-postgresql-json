package com.github.t9t.jooq.json;


import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static org.junit.Assert.*;

public class JsonStringBindingIT {
    private DataSource ds;
    private DSLContext dsl;

    @Before
    public void setUp() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:23719/jooq");
        ds.setUser("jooq");
        ds.setPassword("jooq");

        this.ds = ds;
        this.dsl = DSL.using(ds, SQLDialect.POSTGRES_10);

        // Verify connection naively
        assertEquals(Integer.valueOf(5521), dsl.select(DSL.value("5521")).fetchOneInto(Integer.class));
    }

    /**
     * Tests the assumption that the PostgreSQL driver accepts a JGobject with type="json" as input for both json
     * and jsonb fields.
     */
    @Test
    public void testJdbcAssumption() throws Exception {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("jdbc")).execute();

        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("insert into jooq.json_test (name, data, datab) values (?, ?, ?)")) {

            st.setString(1, "jdbc");
            st.setObject(2, new PGobject() {{type = "json"; value = "{\"jdbc\": \"json\"}";}});
            st.setObject(3, new PGobject() {{type = "json"; value = "{\"jdbc\":\"jsonb\"}";}});
            assertEquals(1, st.executeUpdate());

            PreparedStatement s = conn.prepareStatement("select name, data, datab from jooq.json_test where name = 'jdbc'");
            ResultSet rs = s.executeQuery();
            assertTrue(rs.next());

            assertEquals("{\"jdbc\": \"json\"}", rs.getString("data"));
            assertEquals("{\"jdbc\": \"jsonb\"}", rs.getString("datab"));

            assertFalse(rs.next());
        }
    }

    @Test
    public void selectJson() {
        Record1<String> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals("{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}", r.value1());
    }

    @Test
    public void selectJsonEmpty() {
        Record1<String> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals("{}", r.value1());
    }

    @Test
    public void selectJsonNull() {
        Record1<String> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
    }

    @Test
    public void selectJsonb() {
        Record1<String> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals("{\"jsonb\": {\"n\": null, \"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\": 200}}}", r.value1());
    }

    @Test
    public void selectJsonbEmpty() {
        Record1<String> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals("{}", r.value1());
    }

    @Test
    public void selectJsonbNull() {
        Record1<String> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
    }

    @Test
    public void selectBothJsonAndJsonB() {
        Record2<String, String> r = dsl.select(JSON_TEST.DATA, JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
        assertNull(r.value2());
    }

    @Test
    public void testInsertJson() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("json-insert")).execute();

        String value = "{\"x\": 1}";

        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, "json-insert")
                .set(JSON_TEST.DATA, value)
                .execute());

        assertEquals(value, dsl.select(JSON_TEST.DATA).from(JSON_TEST).where(JSON_TEST.NAME.eq("json-insert")).fetchOne().value1());
    }

    @Test
    public void testInsertJsonb() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-insert")).execute();


        String value = "{\"x\": 1}";

        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, "jsonb-insert")
                .set(JSON_TEST.DATAB, value)
                .execute());

        assertEquals(value, dsl.select(JSON_TEST.DATAB).from(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-insert")).fetchOne().value1());
    }

    @Test
    public void testUpdateJson() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("json-update")).execute();

        String initial = "{\"x\": 1}";
        String updated = "{\"y\": 2}";

        assertEquals(1, dsl.insertInto(JSON_TEST).set(JSON_TEST.NAME, "json-update").set(JSON_TEST.DATA, initial).execute());
        assertEquals(1, dsl.update(JSON_TEST).set(JSON_TEST.DATA, updated).where(JSON_TEST.NAME.eq("json-update")).execute());

        assertEquals(updated, dsl.select(JSON_TEST.DATA).from(JSON_TEST).where(JSON_TEST.NAME.eq("json-update")).fetchOne().value1());
    }

    @Test
    public void testUpdateJsonb() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-update")).execute();

        String initial = "{\"x\": 1}";
        String updated = "{\"y\": 2}";

        assertEquals(1, dsl.insertInto(JSON_TEST).set(JSON_TEST.NAME, "jsonb-update").set(JSON_TEST.DATAB, initial).execute());
        assertEquals(1, dsl.update(JSON_TEST).set(JSON_TEST.DATAB, updated).where(JSON_TEST.NAME.eq("jsonb-update")).execute());

        assertEquals(updated, dsl.select(JSON_TEST.DATAB).from(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-update")).fetchOne().value1());
    }

}
