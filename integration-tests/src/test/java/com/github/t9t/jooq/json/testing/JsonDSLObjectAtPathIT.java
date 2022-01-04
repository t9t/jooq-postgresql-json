package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.objectAtPath;

public class JsonDSLObjectAtPathIT extends AbstractJsonDSLTest {
    public static List<Arguments> params() {
        return generateParams("objectAtPath", Arrays.asList(
                test("oneLevel").selecting(objectAtPath(json, "str")).expectJson("\"Hello, json world!\""),
                test("obj").selecting(objectAtPath(json, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(objectAtPath(json, "arr", "0", "d")).expectJson("4408"),
                test("deepCollection").selecting(objectAtPath(json, Arrays.asList("arr", "0", "d"))).expectJson("4408"),
                test("notExistingPath").selecting(objectAtPath(json, "not", "existing", "path")).expectNull(),

                btest("oneLevel").selecting(JsonbDSL.objectAtPath(jsonb, "str")).expectJsonb("\"Hello, jsonb world!\""),
                btest("obj").selecting(JsonbDSL.objectAtPath(jsonb, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                btest("deepVarargs").selecting(JsonbDSL.objectAtPath(jsonb, "arr", "0", "d")).expectJsonb("4408"),
                btest("deepCollection").selecting(JsonbDSL.objectAtPath(jsonb, Arrays.asList("arr", "0", "d"))).expectJsonb("4408"),
                btest("notExistingPath").selecting(JsonbDSL.objectAtPath(jsonb, "not", "existing", "path")).expectNull()
        ));
    }
}
