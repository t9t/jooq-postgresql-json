package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.github.t9t.jooq.json.JsonDSL.objectAtPathText;

public class JsonDSLObjectAtPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                test("oneLevel").selecting(objectAtPathText(f, "str")).expectString("Hello, " + type + " world!"),
                test("obj").selecting(objectAtPathText(f, "obj")).expectJson(toNode("{\"i\": 5521, \"b\": true}")),
                test("deepVarargs").selecting(objectAtPathText(f, "arr", "0", "d")).expectString("4408"),
                test("deepCollection").selecting(objectAtPathText(f, Arrays.asList("arr", "0", "d"))).expectString("4408"),
                test("notExistingPath").selecting(objectAtPathText(f, "not", "existing", "path")).expectNull()
        ));
    }
}
