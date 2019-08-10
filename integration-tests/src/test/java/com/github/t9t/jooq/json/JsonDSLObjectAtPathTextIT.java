package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLObjectAtPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                test("oneLevel", "Hello, " + type + " world!", JsonDSL.objectAtPathText(f, "str")),
                test("deepVarargs", "4408", JsonDSL.objectAtPathText(f, "arr", "0", "d")),
                test("deepCollection", "4408", JsonDSL.objectAtPathText(f, Arrays.asList("arr", "0", "d"))),
                test("notExistingPath", null, JsonDSL.objectAtPathText(f, "not", "existing", "path"))
        ));
    }
}
