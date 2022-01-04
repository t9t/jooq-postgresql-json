package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.extractPath;

public class JsonDSLExtractPathIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("extractPath", Arrays.asList(
                test("oneLevel").selecting(extractPath(json, "str")).expectJson("\"Hello, json world!\""),
                test("obj").selecting(extractPath(json, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(extractPath(json, "arr", "0", "d")).expectJson("4408"),
                test("deepCollection").selecting(extractPath(json, Arrays.asList("arr", "0", "d"))).expectJson("4408"),
                test("notExistingPath").selecting(extractPath(json, "not", "existing", "path")).expectNull(),

                btest("oneLevel").selecting(JsonbDSL.extractPath(jsonb, "str")).expectJsonb("\"Hello, jsonb world!\""),
                btest("obj").selecting(JsonbDSL.extractPath(jsonb, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                btest("deepVarargs").selecting(JsonbDSL.extractPath(jsonb, "arr", "0", "d")).expectJsonb("4408"),
                btest("deepCollection").selecting(JsonbDSL.extractPath(jsonb, Arrays.asList("arr", "0", "d"))).expectJsonb("4408"),
                btest("notExistingPath").selecting(JsonbDSL.extractPath(jsonb, "not", "existing", "path")).expectNull()
        ));
    }
}
