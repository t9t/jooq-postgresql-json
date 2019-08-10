package com.github.t9t.jooq.json;


import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.github.t9t.jooq.generated.Tables.JSON_STR_TEST;
import static org.junit.Assert.*;

public class JsonStringBindingIT {
    private static final DataSource ds = TestDb.createDataSource();
    private static final DSLContext dsl = DSL.using(ds, SQLDialect.POSTGRES_10);

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_STR_TEST).execute();
        assertEquals(3, dsl.execute("insert into jooq.json_str_test (name, data, datab)" +
                " values " +
                "('both', '{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}', '{\"jsonb\": {\"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}, \"n\": null}}')," +
                "('empty', '{}', '{}')," +
                "('null', null, null)"));
    }

    /**
     * Tests the assumption that Postgres allows "::json" for jsonb fields and also that
     * {@link ResultSet#getString(String)} works for json and jsonb fields.
     */
    @Test
    public void testJdbcAssumption() throws Exception {
        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("insert into jooq.json_str_test (name, data, datab) values (?, ?::json, ?::json)")) {

            st.setString(1, "jdbc");
            st.setString(2, "{\"jdbc\": \"json\"}");
            st.setString(3, "{\"jdbc\":\"jsonb\"}");
            assertEquals(1, st.executeUpdate());

            PreparedStatement s = conn.prepareStatement("select name, data, datab from jooq.json_str_test where name = 'jdbc'");
            ResultSet rs = s.executeQuery();
            assertTrue(rs.next());

            assertEquals("{\"jdbc\": \"json\"}", rs.getString("data"));
            assertEquals("{\"jdbc\": \"jsonb\"}", rs.getString("datab"));

            assertFalse(rs.next());
        }
    }

    @Test
    public void selectJson() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATA)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals("{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}", r.value1());
    }

    @Test
    public void selectJsonEmpty() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATA)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals("{}", r.value1());
    }

    @Test
    public void selectJsonNull() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATA)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
    }

    @Test
    public void selectJsonb() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATAB)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals("{\"jsonb\": {\"n\": null, \"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}}}", r.value1());
    }

    @Test
    public void selectJsonbEmpty() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATAB)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals("{}", r.value1());
    }

    @Test
    public void selectJsonbNull() {
        Record1<String> r = dsl.select(JSON_STR_TEST.DATAB)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
    }

    @Test
    public void selectBothJsonAndJsonB() {
        Record2<String, String> r = dsl.select(JSON_STR_TEST.DATA, JSON_STR_TEST.DATAB)
                .from(JSON_STR_TEST)
                .where(JSON_STR_TEST.NAME.eq("null"))
                .fetchOne();

        assertNull(r.value1());
        assertNull(r.value2());
    }

    @Test
    public void testInsertJson() {
        dsl.deleteFrom(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("json-insert")).execute();

        String value = "{\"x\": 1}";

        assertEquals(1, dsl.insertInto(JSON_STR_TEST)
                .set(JSON_STR_TEST.NAME, "json-insert")
                .set(JSON_STR_TEST.DATA, value)
                .execute());

        assertEquals(value, dsl.select(JSON_STR_TEST.DATA).from(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("json-insert")).fetchOne().value1());
    }

    @Test
    public void testInsertJsonb() {
        dsl.deleteFrom(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("jsonb-insert")).execute();


        String value = "{\"x\": 1}";

        assertEquals(1, dsl.insertInto(JSON_STR_TEST)
                .set(JSON_STR_TEST.NAME, "jsonb-insert")
                .set(JSON_STR_TEST.DATAB, value)
                .execute());

        assertEquals(value, dsl.select(JSON_STR_TEST.DATAB).from(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("jsonb-insert")).fetchOne().value1());
    }

    @Test
    public void testUpdateJson() {
        dsl.deleteFrom(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("json-update")).execute();

        String initial = "{\"x\": 1}";
        String updated = "{\"y\": 2}";

        assertEquals(1, dsl.insertInto(JSON_STR_TEST).set(JSON_STR_TEST.NAME, "json-update").set(JSON_STR_TEST.DATA, initial).execute());
        assertEquals(1, dsl.update(JSON_STR_TEST).set(JSON_STR_TEST.DATA, updated).where(JSON_STR_TEST.NAME.eq("json-update")).execute());

        assertEquals(updated, dsl.select(JSON_STR_TEST.DATA).from(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("json-update")).fetchOne().value1());
    }

    @Test
    public void testUpdateJsonb() {
        dsl.deleteFrom(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("jsonb-update")).execute();

        String initial = "{\"x\": 1}";
        String updated = "{\"y\": 2}";

        assertEquals(1, dsl.insertInto(JSON_STR_TEST).set(JSON_STR_TEST.NAME, "jsonb-update").set(JSON_STR_TEST.DATAB, initial).execute());
        assertEquals(1, dsl.update(JSON_STR_TEST).set(JSON_STR_TEST.DATAB, updated).where(JSON_STR_TEST.NAME.eq("jsonb-update")).execute());

        assertEquals(updated, dsl.select(JSON_STR_TEST.DATAB).from(JSON_STR_TEST).where(JSON_STR_TEST.NAME.eq("jsonb-update")).fetchOne().value1());
    }

}
