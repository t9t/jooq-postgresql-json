package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.deletePath;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLDeletePathIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("deletePath", Arrays.asList(
                btest("obj").selecting(deletePath(field("{\"a\": 10, \"b\": 20}"), "a")).expectJsonb("{\"b\": 20}"),
                btest("array").selecting(deletePath(field("[\"a\", 1, \"b\", false, \"c\"]"), "2")).expectJsonb("[\"a\", 1, false, \"c\"]"),
                btest("complex").selecting(deletePath(field("{\"a\": 10, \"b\": [20, {\"x\": {\"y\": [1, 2, 3]}}], \"c\": 30}"), "b", "1", "x", "y", "0"))
                        .expectJsonb("{\"a\": 10, \"b\": [20, {\"x\": {\"y\": [2, 3]}}], \"c\": 30}")
        ));
    }
}
