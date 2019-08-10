package com.github.t9t.jooq.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static com.github.t9t.jooq.generated.Tables.JSON_TEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public abstract class AbstractJsonDSLTest {
    private static final DSLContext dsl = DSL.using(TestDb.createDataSource(), SQLDialect.POSTGRES_10);
    private static final ObjectMapper om = new ObjectMapper();

    static final String genericRow = "json-dsl";
    static final String arrayRow = "array";

    @Parameterized.Parameter
    public String testName;
    @Parameterized.Parameter(1)
    public String rowName;
    @Parameterized.Parameter(2)
    public Object expected;
    @Parameterized.Parameter(3)
    public Field<?> fieldToSelect;

    static List<Object[]> generateParams(String baseName, BiFunction<String, Field<Json>, List<Params>> testParamFunc) {
        List<Object[]> params = new ArrayList<>();
        for (String type : Arrays.asList("json", "jsonb")) {
            Field<Json> f = "json".equals(type) ? JSON_TEST.DATA : JSON_TEST.DATAB;
            List<Params> paramList = testParamFunc.apply(type, f);
            for (Params p : paramList) {
                String name = String.format("%s_%s_%s", baseName, p.name, type);
                params.add(new Object[]{name, p.dataSet, p.expected, p.fieldToSelect});
            }
        }
        return params;
    }

    static Params test(String name, String expected, Field<Json> fieldToSelect) {
        return params(name, genericRow, Json.ofNullable(expected), fieldToSelect);
    }

    static Params stringTest(String name, String expected, Field<String> fieldToSelect) {
        return params(name, genericRow, expected, fieldToSelect);
    }

    static Params test(String name, JsonNode expected, Field<?> fieldToSelect) {
        return params(name, genericRow, expected, fieldToSelect);
    }

    static Params testNull(String name, Field<?> fieldToSelect) {
        return params(name, genericRow, null, fieldToSelect);
    }

    static Params arrayTest(String name, String expected, Field<Json> fieldToSelect) {
        return params(name, arrayRow, Json.ofNullable(expected), fieldToSelect);
    }

    static Params arrayStringTest(String name, String expected, Field<String> fieldToSelect) {
        return params(name, arrayRow, expected, fieldToSelect);
    }

    private static Params params(String name, String rowName, Object expected, Field<?> fieldToSelect) {
        return new Params(name, rowName, expected, fieldToSelect);
    }

    @Before
    public void setUp() {
        dsl.deleteFrom(JSON_TEST).execute();

        String template = "{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, %s world!\", \"n\": null}";
        String arrayTemplate = "[{\"d\": 4408}, 10, true, \"%s array\"]";
        assertEquals(2, dsl.insertInto(JSON_TEST)
                .columns(JSON_TEST.NAME, JSON_TEST.DATA, JSON_TEST.DATAB)
                .values(genericRow, Json.of(String.format(template, "json")), Json.of(String.format(template, "jsonb")))
                .values(arrayRow, Json.of(String.format(arrayTemplate, "json")), Json.of(String.format(arrayTemplate, "jsonb")))
                .execute());
        assertEquals(2, dsl.fetchCount(JSON_TEST));
    }

    @Test
    public void test() {
        Object data = select(rowName, fieldToSelect);

        if (expected == null) {
            assertNull(data);
        } else if (expected instanceof String || expected instanceof Json) {
            assertEquals(expected, data);
        } else if (expected instanceof JsonNode) {
            if (data instanceof Json) {
                assertEquals(expected, toNode(((Json) data).getValue()));
            } else {
                assertEquals(expected, toNode((String) data));
            }
        } else {
            throw new IllegalArgumentException("Cannot assert object: " + expected.getClass());
        }
    }

    static JsonNode toNode(String s) {
        try {
            return om.readTree(s);
        } catch (IOException e) {
            throw new AssertionError("Unable to parse JSON: " + s, e);
        }
    }

    private Object select(String rowName, Field<?> field) {
        return dsl.select(field)
                .from(JSON_TEST)
                .where(JSON_TEST.NAME.eq(rowName))
                .fetchOne().value1();
    }

    static class Params {
        final String name;
        final String dataSet;
        final Object expected;
        final Field<?> fieldToSelect;

        public Params(String name, String dataSet, Object expected, Field<?> fieldToSelect) {
            this.name = name;
            this.dataSet = dataSet;
            this.expected = expected;
            this.fieldToSelect = fieldToSelect;
        }
    }
}
