package com.github.t9t.jooq.json;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class JsonDSLIT {
    private static final DSLContext dsl = DSL.using(TestDb.createDataSource(), SQLDialect.POSTGRES_10);
    private static final String testRowName = "json-dsl";

    private final String expected;
    private final Field<String> fieldToSelect;

    public JsonDSLIT(String name, String type, String expected, Field<String> fieldToSelect) {
        this.expected = requireNonNull(expected, "expected");
        this.fieldToSelect = requireNonNull(fieldToSelect, "fieldToSelect");
    }

    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        List<Object[]> params = new ArrayList<>();
        for (String type : Arrays.asList("json", "jsonb")) {
            Field<String> f = "json".equals(type) ? JSON_TEST.DATA : JSON_TEST.DATAB;
            params.addAll(Arrays.asList(
                    params("fieldByKey", type, "\"Hello, " + type.toUpperCase() + " world!\"", JsonDSL.fieldByKey(f, "str")),
                    params("fieldByKey_twoLevels", type, "5521", JsonDSL.fieldByKey(JsonDSL.fieldByKey(f, "obj"), "i"))
            ));
        }
        return params;
    }

    private static Object[] params(String name, String type, String expected, Field<String> field) {
        return new Object[]{name, type, expected, field};
    }

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_TEST).execute();

        String template = "{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, %s world!\", \"n\": null}";
        assertEquals(1, dsl.insertInto(JSON_TEST)
                .set(JSON_TEST.NAME, testRowName)
                .set(JSON_TEST.DATA, String.format(template, "JSON"))
                .set(JSON_TEST.DATAB, String.format(template, "JSONB"))
                .execute());
        assertEquals(1, dsl.fetchCount(JSON_TEST));
    }

    @Test
    public void test() {
        assertEquals(expected, select(fieldToSelect));
    }

    private static String select(Field<String> field) {
        return dsl.select(field)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq(testRowName))
                .fetchOne().value1();
    }
}
