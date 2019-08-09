package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElement", (type, f) -> Arrays.asList(
                arrayTest("getFirstObject", "{\"d\": 4408}", JsonDSL.arrayElement(f, 0)),
                arrayTest("getString", "\"" + type + " array\"", JsonDSL.arrayElement(f, 3)),
                arrayTest("outOfBounds", null, JsonDSL.arrayElement(f, 100)),
                arrayTest("negativeIndex", "true", JsonDSL.arrayElement(f, -2)),
                arrayTest("onObject", null, JsonDSL.arrayElement(f, 100))
        ));
    }

}
