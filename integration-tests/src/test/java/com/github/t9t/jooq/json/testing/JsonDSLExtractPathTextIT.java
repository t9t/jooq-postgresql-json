package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.extractPathText;

public class JsonDSLExtractPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("extractPathText", Arrays.asList(
                test("oneLevel").selecting(extractPathText(json, "str")).expectString("Hello, json world!"),
                test("obj").selecting(extractPathText(json, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(extractPathText(json, "arr", "0", "d")).expectString("4408"),
                test("deepCollection").selecting(extractPathText(json, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                test("notExistingPath").selecting(extractPathText(json, "not", "existing", "path")).expectNull(),

                btest("oneLevel").selecting(JsonbDSL.extractPathText(jsonb, "str")).expectString("Hello, jsonb world!"),
                btest("obj").selecting(JsonbDSL.extractPathText(jsonb, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                btest("deepVarargs").selecting(JsonbDSL.extractPathText(jsonb, "arr", "0", "d")).expectString("4408"),
                btest("deepCollection").selecting(JsonbDSL.extractPathText(jsonb, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                btest("notExistingPath").selecting(JsonbDSL.extractPathText(jsonb, "not", "existing", "path")).expectNull()
        ));
    }
}
