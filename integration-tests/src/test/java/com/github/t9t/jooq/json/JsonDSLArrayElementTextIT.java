package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElementText", (type, f) -> Arrays.asList(
                arrayTest("getFirstObject", "{\"d\": 4408}", JsonDSL.arrayElementText(f, 0)),
                arrayTest("getString", type + " array", JsonDSL.arrayElementText(f, 3)),
                arrayTest("outOfBounds", null, JsonDSL.arrayElementText(f, 100)),
                arrayTest("negativeIndex", "true", JsonDSL.arrayElementText(f, -2)),
                test("onObject", null, JsonDSL.arrayElementText(f, 100))
        ));
    }

}
