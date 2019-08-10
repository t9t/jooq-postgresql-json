package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.objectAtPath;

public class JsonDSLObjectAtPathIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                test("oneLevel").selecting(objectAtPath(f, "str")).expectJson("\"Hello, " + type + " world!\""),
                test("obj").selecting(objectAtPath(f, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(objectAtPath(f, "arr", "0", "d")).expectJson("4408"),
                test("deepCollection").selecting(objectAtPath(f, Arrays.asList("arr", "0", "d"))).expectJson("4408"),
                test("notExistingPath").selecting(objectAtPath(f, "not", "existing", "path")).expectNull()
        ));
    }
}
