package com.github.t9t.jooq.json;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static org.junit.Assert.assertEquals;

public class JsonDSLIT {
    private static final DSLContext dsl = DSL.using(TestDb.createDataSource(), SQLDialect.POSTGRES_10);
    private static final String name = "json-dsl";

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_TEST).execute();

        String template = "{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, %s world!\", \"n\": null}";
        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, name)
                .set(JSON_TEST.DATA, String.format(template, "JSON"))
                .set(JSON_TEST.DATAB, String.format(template, "JSONB"))
                .execute());
        assertEquals(1, dsl.fetchCount(JSON_TEST));
    }

    @Test
    public void json_fieldByKey_select() {
        assertEquals("\"Hello, JSON world!\"", select(JsonDSL.fieldByKey(JSON_TEST.DATA, "str")));
    }

    @Test
    public void json_fieldByKey_select_twoLevels() {
        assertEquals("5521", select(JsonDSL.fieldByKey(JsonDSL.fieldByKey(JSON_TEST.DATA, "obj"), "i")));
    }

    @Test
    public void jsonb_fieldByKey_select() {
        assertEquals("\"Hello, JSONB world!\"", select(JsonDSL.fieldByKey(JSON_TEST.DATAB, "str")));
    }

    @Test
    public void jsonb_fieldByKey_select_twoLevels() {
        assertEquals("5521", select(JsonDSL.fieldByKey(JsonDSL.fieldByKey(JSON_TEST.DATAB, "obj"), "i")));
    }

    private static String select(Field<String> field) {
        return dsl.select(field)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq(name))
                .fetchOne().value1();
    }
}
