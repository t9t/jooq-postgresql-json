package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLObjectAtPathIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                test("oneLevel", "\"Hello, " + type + " world!\"", JsonDSL.objectAtPath(f, "str")),
                test("deepVarargs", "4408", JsonDSL.objectAtPath(f, "arr", "0", "d")),
                test("deepCollection", "4408", JsonDSL.objectAtPath(f, Arrays.asList("arr", "0", "d"))),
                test("notExistingPath", null, JsonDSL.objectAtPath(f, "not", "existing", "path"))
        ));
    }
}
