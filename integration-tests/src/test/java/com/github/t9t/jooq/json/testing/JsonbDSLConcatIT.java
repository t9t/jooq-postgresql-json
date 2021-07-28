package com.github.t9t.jooq.json.testing;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.concat;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLConcatIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("concat", Arrays.asList(
                btest("objects").selecting(concat(field("{\"a\": 10}"), field("{\"b\": 20}"))).expectJsonb("{\"a\": 10, \"b\": 20}"),
                btest("arrays").selecting(concat(field("[\"a\", 10, true]"), field("[50, {\"b\": 20}]"))).expectJsonb("[\"a\", 10, true, 50, {\"b\": 20}]"),
                btest("arrayAndObject").selecting(concat(field("[\"a\", 10, true]"), field("{\"b\": 20}"))).expectJsonb("[\"a\", 10, true, {\"b\": 20}]"),
                btest("nested").selecting(concat(concat(field("{\"a\": 10}"), field("{\"b\": 20}")), field("{\"c\": 30}"))).expectJsonb("{\"a\": 10, \"b\": 20, \"c\": 30}")
        ));
    }
}
