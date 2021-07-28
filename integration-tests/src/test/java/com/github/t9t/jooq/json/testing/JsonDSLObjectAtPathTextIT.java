package com.github.t9t.jooq.json.testing;

import com.github.t9t.jooq.json.JsonbDSL;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.objectAtPathText;

public class JsonDSLObjectAtPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPathText", Arrays.asList(
                test("oneLevel").selecting(objectAtPathText(json, "str")).expectString("Hello, json world!"),
                test("obj").selecting(objectAtPathText(json, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(objectAtPathText(json, "arr", "0", "d")).expectString("4408"),
                test("deepCollection").selecting(objectAtPathText(json, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                test("notExistingPath").selecting(objectAtPathText(json, "not", "existing", "path")).expectNull(),

                btest("oneLevel").selecting(JsonbDSL.objectAtPathText(jsonb, "str")).expectString("Hello, jsonb world!"),
                btest("obj").selecting(JsonbDSL.objectAtPathText(jsonb, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                btest("deepVarargs").selecting(JsonbDSL.objectAtPathText(jsonb, "arr", "0", "d")).expectString("4408"),
                btest("deepCollection").selecting(JsonbDSL.objectAtPathText(jsonb, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                btest("notExistingPath").selecting(JsonbDSL.objectAtPathText(jsonb, "not", "existing", "path")).expectNull()
        ));
    }
}
