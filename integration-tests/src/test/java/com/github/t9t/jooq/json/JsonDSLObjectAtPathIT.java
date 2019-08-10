package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLObjectAtPathIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                test("oneLevel").selecting(JsonDSL.objectAtPath(f, "str")).expectJson("\"Hello, " + type + " world!\""),
                test("obj").selecting(JsonDSL.objectAtPath(f, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(JsonDSL.objectAtPath(f, "arr", "0", "d")).expectJson("4408"),
                test("deepCollection").selecting(JsonDSL.objectAtPath(f, Arrays.asList("arr", "0", "d"))).expectJson("4408"),
                test("notExistingPath").selecting(JsonDSL.objectAtPath(f, "not", "existing", "path")).expectNull()
        ));
    }
}
