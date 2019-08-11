package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.objectAtPathText;

public class JsonDSLObjectAtPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", Arrays.asList(
                test("oneLevel").selecting(objectAtPathText(json, "str")).expectString("Hello, json world!"),
                test("obj").selecting(objectAtPathText(json, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(objectAtPathText(json, "arr", "0", "d")).expectString("4408"),
                test("deepCollection").selecting(objectAtPathText(json, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                test("notExistingPath").selecting(objectAtPathText(json, "not", "existing", "path")).expectNull()
        ));
    }
}
