package com.github.t9t.jooq.json.testing;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.deletePath;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLDeletePathIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("deletePath", Arrays.asList(
                btest("obj").selecting(deletePath(field("{\"a\": 10, \"b\": 20}"), "a")).expectJsonb("{\"b\": 20}"),
                btest("array").selecting(deletePath(field("[\"a\", 1, \"b\", false, \"c\"]"), "2")).expectJsonb("[\"a\", 1, false, \"c\"]"),
                btest("complex").selecting(deletePath(field("{\"a\": 10, \"b\": [20, {\"x\": {\"y\": [1, 2, 3]}}], \"c\": 30}"), "b", "1", "x", "y", "0"))
                        .expectJsonb("{\"a\": 10, \"b\": [20, {\"x\": {\"y\": [2, 3]}}], \"c\": 30}")
        ));
    }
}
