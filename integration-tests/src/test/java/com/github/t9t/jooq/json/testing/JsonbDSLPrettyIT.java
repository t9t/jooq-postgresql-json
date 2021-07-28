package com.github.t9t.jooq.json.testing;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonbDSL.pretty;

public class JsonbDSLPrettyIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("pretty", Arrays.asList(
                btest("emptyObject").usingJson("{}").selecting(pretty(jsonb)).expectString("{\n}"),
                btest("emptyArray").usingJson("[]").selecting(pretty(jsonb)).expectString("[\n]"),
                btest("object").usingJson("{\"a\": \"b\"}").selecting(pretty(jsonb)).expectString("{\n    \"a\": \"b\"\n}"),
                btest("array").usingJson("[\"a\", \"b\"]").selecting(pretty(jsonb)).expectString("[\n    \"a\",\n    \"b\"\n]")
        ));
    }
}
