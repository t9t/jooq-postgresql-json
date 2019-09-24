package com.github.t9t.jooq.json;


import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonBindingIT {
    private static final DataSource ds = TestDb.createDataSource();
    private static final DSLContext dsl = DSL.using(ds, SQLDialect.POSTGRES_10);

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_TEST).execute();
        assertEquals(4, dsl.execute("insert into jooq.json_test (name, data, datab)" +
                " values " +
                "('both', '{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}', '{\"jsonb\": {\"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}, \"n\": null}}')," +
                "('empty', '{}', '{}')," +
                "('null-sql', null, null)," +
                "('null-json', 'null', 'null')"));
    }

    @Test
    public void selectJson() {
        Record1<Json> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals(Json.of("{\"json\": {\"int\": 100, \"str\": \"Hello, JSON world!\", \"object\": {\"v\":  200}, \"n\": null}}"), r.value1());
    }

    @Test
    public void selectJsonEmpty() {
        Record1<Json> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals(Json.of("{}"), r.value1());
    }

    @Test
    public void selectJsonNullSql() {
        Record1<Json> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne();

        assertNull(r.value1());
    }


    @Test
    public void selectJsonNullJsonValue() {
        Record1<Json> r = dsl.select(JSON_TEST.DATA)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-json"))
                .fetchOne();

        assertEquals(Json.of("null"), r.value1());
    }

    @Test
    public void selectJsonb() {
        Record1<Jsonb> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("both"))
                .fetchOne();

        assertEquals(Jsonb.of("{\"jsonb\": {\"n\": null, \"int\": 100, \"str\": \"Hello, JSONB world!\", \"object\": {\"v\": 200}}}"), r.value1());
    }

    @Test
    public void selectJsonbEmpty() {
        Record1<Jsonb> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("empty"))
                .fetchOne();

        assertEquals(Jsonb.of("{}"), r.value1());
    }

    @Test
    public void selectJsonbNullSql() {
        Record1<Jsonb> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne();

        assertNull(r.value1());
    }

    @Test
    public void selectJsonbNullJsonValue() {
        Record1<Jsonb> r = dsl.select(JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-json"))
                .fetchOne();

        assertEquals(Jsonb.of("null"), r.value1());
    }

    @Test
    public void selectBothJsonAndJsonB() {
        Record2<Json, Jsonb> r = dsl.select(JSON_TEST.DATA, JSON_TEST.DATAB)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq("null-sql"))
                .fetchOne();

        assertNull(r.value1());
        assertNull(r.value2());
    }

    @Test
    public void testInsertJson() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("json-insert")).execute();

        Json value = Json.of("{\"x\": 1}");

        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, "json-insert")
                .set(JSON_TEST.DATA, value)
                .execute());

        assertEquals(value, dsl.select(JSON_TEST.DATA).from(JSON_TEST).where(JSON_TEST.NAME.eq("json-insert")).fetchOne().value1());
    }

    @Test
    public void testInsertJsonb() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-insert")).execute();


        Jsonb value = Jsonb.of("{\"x\": 1}");

        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, "jsonb-insert")
                .set(JSON_TEST.DATAB, value)
                .execute());

        assertEquals(value, dsl.select(JSON_TEST.DATAB).from(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-insert")).fetchOne().value1());
    }

    @Test
    public void testUpdateJson() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("json-update")).execute();

        Json initial = Json.of("{\"x\": 1}");
        Json updated = Json.of("{\"y\": 2}");

        assertEquals(1, dsl.insertInto(JSON_TEST).set(JSON_TEST.NAME, "json-update").set(JSON_TEST.DATA, initial).execute());
        assertEquals(1, dsl.update(JSON_TEST).set(JSON_TEST.DATA, updated).where(JSON_TEST.NAME.eq("json-update")).execute());

        assertEquals(updated, dsl.select(JSON_TEST.DATA).from(JSON_TEST).where(JSON_TEST.NAME.eq("json-update")).fetchOne().value1());
    }

    @Test
    public void testUpdateJsonb() {
        dsl.deleteFrom(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-update")).execute();

        Jsonb initial = Jsonb.of("{\"x\": 1}");
        Jsonb updated = Jsonb.of("{\"y\": 2}");

        assertEquals(1, dsl.insertInto(JSON_TEST).set(JSON_TEST.NAME, "jsonb-update").set(JSON_TEST.DATAB, initial).execute());
        assertEquals(1, dsl.update(JSON_TEST).set(JSON_TEST.DATAB, updated).where(JSON_TEST.NAME.eq("jsonb-update")).execute());

        assertEquals(updated, dsl.select(JSON_TEST.DATAB).from(JSON_TEST).where(JSON_TEST.NAME.eq("jsonb-update")).fetchOne().value1());
    }

}
