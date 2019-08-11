package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.Jsonb.of;
import static com.github.t9t.jooq.json.JsonbDSL.concat;
import static com.github.t9t.jooq.json.JsonbDSL.field;

public class JsonbDSLConcatIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("concat", Arrays.asList(
                btest("objects").selecting(concat(field(of("{\"a\": 10}")), field(of("{\"b\": 20}")))).expectJsonb("{\"a\": 10, \"b\": 20}"),
                btest("arrays").selecting(concat(field(of("[\"a\", 10, true]")), field(of("[50, {\"b\": 20}]")))).expectJsonb("[\"a\", 10, true, 50, {\"b\": 20}]"),
                btest("arrayAndObject").selecting(concat(field(of("[\"a\", 10, true]")), field(of("{\"b\": 20}")))).expectJsonb("[\"a\", 10, true, {\"b\": 20}]")
        ));
    }
}
