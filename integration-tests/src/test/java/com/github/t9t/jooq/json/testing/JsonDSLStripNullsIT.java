package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.stripNulls;

public class JsonDSLStripNullsIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("stripNulls", Arrays.asList(
                test("object").selecting(stripNulls(json)).expectJson(toNode("{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, json world!\"}")),
                test("array").usingJson("[\"a\", null, \"b\", 2, null, 3, null]").selecting(stripNulls(json)).expectJson(toNode("[\"a\", null, \"b\", 2, null, 3, null]")),
                test("mixAndNested").usingJson("{\"ar\": [1, null, {\"d\": null}], \"o\": {\"n\": null, \"x\": 2, \"z\": [null]}, \"t\": null}")
                        .selecting(stripNulls(json)).expectJson(toNode("{\"ar\": [1, null, {}], \"o\": {\"x\": 2, \"z\": [null]}}")),

                btest("object").selecting(JsonbDSL.stripNulls(jsonb)).expectJson(toNode("{\"obj\": {\"i\": 5521, \"b\": true}, \"arr\": [{\"d\": 4408}, 10, true, \"s\"], \"num\": 1337, \"str\": \"Hello, jsonb world!\"}")),
                btest("array").usingJson("[\"a\", null, \"b\", 2, null, 3, null]").selecting(JsonbDSL.stripNulls(jsonb)).expectJson(toNode("[\"a\", null, \"b\", 2, null, 3, null]")),
                btest("mixAndNested").usingJson("{\"ar\": [1, null, {\"d\": null}], \"o\": {\"n\": null, \"x\": 2, \"z\": [null]}, \"t\": null}")
                        .selecting(JsonbDSL.stripNulls(jsonb)).expectJson(toNode("{\"ar\": [1, null, {}], \"o\": {\"x\": 2, \"z\": [null]}}"))
        ));
    }
}
