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
    private static final String genericRow = "json-dsl";
    private static final String arrayRow = "array";

    private final String rowName;
    private final String expected;
    private final Field<String> fieldToSelect;

    public JsonDSLIT(String name, String type, String rowName, String expected, Field<String> fieldToSelect) {
        this.rowName = requireNonNull(rowName, "rowName");
        this.expected = expected;
        this.fieldToSelect = requireNonNull(fieldToSelect, "fieldToSelect");
    }

    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        List<Object[]> params = new ArrayList<>();
        for (String type : Arrays.asList("json", "jsonb")) {
            Field<String> f = "json".equals(type) ? JSON_TEST.DATA : JSON_TEST.DATAB;
            params.addAll(Arrays.asList(
                    params("arrayElement_getFirstObject", type, arrayRow, "{\"d\": 4408}", JsonDSL.arrayElement(f, 0)),
                    params("arrayElement_getString", type, arrayRow, "\"" + type + " array\"", JsonDSL.arrayElement(f, 3)),
                    params("arrayElement_outOfBounds", type, arrayRow, null, JsonDSL.arrayElement(f, 100)),
                    params("arrayElement_negativeIndex", type, arrayRow, "true", JsonDSL.arrayElement(f, -2)),
                    params("arrayElement_onObject", type, genericRow, null, JsonDSL.arrayElement(f, 100)),

                    params("fieldByKey", type, "\"Hello, " + type + " world!\"", JsonDSL.fieldByKey(f, "str")),
                    params("fieldByKey_twoLevels", type, "5521", JsonDSL.fieldByKey(JsonDSL.fieldByKey(f, "obj"), "i")),
                    params("fieldByKey_nullField", type, "null", JsonDSL.fieldByKey(f, "n")),
                    params("fieldByKey_notExistingField", type, null, JsonDSL.fieldByKey(f, "notExisting")),

                    params("arrayElementText_getFirstObject", type, arrayRow, "{\"d\": 4408}", JsonDSL.arrayElementText(f, 0)),
                    params("arrayElementText_getString", type, arrayRow, type + " array", JsonDSL.arrayElementText(f, 3)),
                    params("arrayElementText_outOfBounds", type, arrayRow, null, JsonDSL.arrayElementText(f, 100)),
                    params("arrayElementText_negativeIndex", type, arrayRow, "true", JsonDSL.arrayElementText(f, -2)),
                    params("arrayElementText_onObject", type, genericRow, null, JsonDSL.arrayElementText(f, 100)),

                    params("fieldByKeyText", type, "Hello, " + type + " world!", JsonDSL.fieldByKeyText(f, "str")),
                    params("fieldByKeyText_twoLevels", type, "5521", JsonDSL.fieldByKeyText(JsonDSL.fieldByKey(f, "obj"), "i")),
                    params("fieldByKeyText_nullField", type, null, JsonDSL.fieldByKeyText(f, "n")),
                    params("fieldByKeyText_notExistingField", type, null, JsonDSL.fieldByKeyText(f, "notExisting"))
            ));
        }
        return params;
    }

    private static Object[] params(String name, String type, String expected, Field<String> field) {
        return params(name, type, genericRow, expected, field);
    }

    private static Object[] params(String name, String type, String rowName, String expected, Field<String> field) {
        return new Object[]{name, type, rowName, expected, field};
    }

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_TEST).execute();

        String template = "{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, %s world!\", \"n\": null}";
        String artmpl = "[{\"d\": 4408}, 10, true, \"%s array\"]";
        assertEquals(2, dsl.insertInto(JSON_TEST)
                .columns(JSON_TEST.NAME, JSON_TEST.DATA, JSON_TEST.DATAB)
                .values(genericRow, String.format(template, "json"), String.format(template, "jsonb"))
                .values(arrayRow, String.format(artmpl, "json"), String.format(artmpl, "jsonb"))
                .execute());
        assertEquals(2, dsl.fetchCount(JSON_TEST));
    }

    @Test
    public void test() {
        assertEquals(expected, select(rowName, fieldToSelect));
    }

    private static String select(String rowName, Field<String> field) {
        return dsl.select(field)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq(rowName))
                .fetchOne().value1();
    }
}
