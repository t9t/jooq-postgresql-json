package com.github.t9t.jooq.json.testing;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.delete;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLDeleteIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("delete", Arrays.asList(
                btest("obj").selecting(delete(field("{\"a\": 10, \"b\": 20}"), "a")).expectJsonb("{\"b\": 20}"),
                btest("array").selecting(delete(field("[\"a\", 1, \"b\", false, \"c\"]"), "b")).expectJsonb("[\"a\", 1, false, \"c\"]"),
                btest("nested").selecting(delete(delete(field("{\"a\": 10, \"b\": 20, \"c\": 30}"), "a"), "c")).expectJsonb("{\"b\": 20}"),

                btest("multi_obj").selecting(delete(field("{\"a\": 10, \"b\": 20, \"c\": 30}"), "b", "a")).expectJsonb("{\"c\": 30}"),
                btest("multi_array").selecting(delete(field("[\"a\", 1, \"b\", false, \"c\"]"), "b", "a")).expectJsonb("[1, false, \"c\"]")
        ));
    }
}
