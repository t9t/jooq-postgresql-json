package com.github.t9t.jooq.json.testing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.t9t.jooq.generated.java.Tables.JSON_TEST;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class AbstractJsonDSLTest {
    private static final DSLContext dsl = DSL.using(TestDb.createDataSource(), SQLDialect.POSTGRES);
    private static final ObjectMapper om = new ObjectMapper();

    private static final String template = "{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, %s world!\", \"n\": null}";
    private static final String arrayTemplate = "[{\"d\": 4408}, 10, true, \"%s array\"]";

    static final Field<JSON> json = JSON_TEST.DATA;
    static final Field<JSONB> jsonb = JSON_TEST.DATAB;

    static List<Arguments> generateParams(String baseName, List<Params> paramList) {
        return paramList.stream().map(p -> {
            String name = String.format("%s_%s", baseName, p.name);
            return Arguments.of(name, requireNonNull(p.jsonData, "jsonData"), p.expected, requireNonNull(p.fieldToSelect, "fieldToSelect"));
        }).collect(Collectors.toList());
    }

    private void setUp(String jsonData) {
        dsl.deleteFrom(JSON_TEST).execute();

        assertEquals(1, dsl.insertInto(JSON_TEST)
                .columns(JSON_TEST.NAME, JSON_TEST.DATA, JSON_TEST.DATAB)
                .values("json-dsl-test", JSON.valueOf(jsonData), JSONB.valueOf(jsonData))
                .execute());
        assertEquals(1, dsl.fetchCount(JSON_TEST));
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("params")
    public void test(String ignoredTestName, String jsonData, Object expected, Field<?> fieldToSelect) {
        setUp(jsonData);

        Object data = dsl.select(fieldToSelect).from(JSON_TEST).fetchOne().value1();

        if (expected == null) {
            assertNull(data);
            return;
        }

        if (expected instanceof JsonNode) {
            if (data instanceof JSON) {
                assertEquals(expected, toNode(data.toString()));
            } else if (data instanceof JSONB) {
                assertEquals(expected, toNode(data.toString()));
            } else {
                assertEquals(expected, toNode((String) data));
            }
            return;
        }

        assertEquals(expected, data);
    }

    static JsonNode toNode(String s) {
        try {
            return om.readTree(s);
        } catch (IOException e) {
            throw new AssertionError("Unable to parse JSON: " + s, e);
        }
    }

    static Params test(String name) {
        return new Params(name + "_json", String.format(template, "json"));
    }

    static Params btest(String name) {
        return new Params(name + "_jsonb", String.format(template, "jsonb"));
    }

    static class Params {
        private final String name;
        private String jsonData;
        private Object expected;
        private Field<?> fieldToSelect;

        private Params(String name, String jsonData) {
            this.name = name;
            this.jsonData = jsonData;
        }

        Params forArray() {
            this.jsonData = String.format(arrayTemplate, "json" + (name.endsWith("b") ? "b" : ""));
            return this;
        }

        Params usingJson(String json) {
            this.jsonData = json;
            return this;
        }

        Params selecting(Field<?> fieldToSelect) {
            this.fieldToSelect = fieldToSelect;
            return this;
        }

        Params expectNull() {
            this.expected = null;
            return this;
        }

        Params expect(Object o) {
            this.expected = o;
            return this;
        }

        Params expectString(String s) {
            return expect(s);
        }

        Params expectJson(String s) {
            this.expected = JSON.valueOf(s);
            return this;
        }

        Params expectJsonb(String s) {
            this.expected = JSONB.valueOf(s);
            return this;
        }

        Params expectJson(JsonNode node) {
            this.expected = node;
            return this;
        }
    }
}
