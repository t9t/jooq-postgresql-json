package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLObjectAtPathTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("objectAtPath", (type, f) -> Arrays.asList(
                stringTest("oneLevel", "Hello, " + type + " world!", JsonDSL.objectAtPathText(f, "str")),
                test("obj", toNode("{\"i\": 5521, \"b\": true}"), JsonDSL.objectAtPathText(f, "obj")),
                stringTest("deepVarargs", "4408", JsonDSL.objectAtPathText(f, "arr", "0", "d")),
                stringTest("deepCollection", "4408", JsonDSL.objectAtPathText(f, Arrays.asList("arr", "0", "d"))),
                testNull("notExistingPath", JsonDSL.objectAtPathText(f, "not", "existing", "path"))
        ));
    }
}
