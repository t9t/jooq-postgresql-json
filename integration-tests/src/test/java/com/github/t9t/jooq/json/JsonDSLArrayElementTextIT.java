package com.github.t9t.jooq.json;

import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

public class JsonDSLArrayElementTextIT extends AbstractJsonDSLTest {
    @Parameterized.Parameters(name = "{1}_{0}")
    public static List<Object[]> params() {
        return generateParams("arrayElementText", (type, f) -> Arrays.asList(
                arrayStringTest("getFirstObject", "{\"d\": 4408}", JsonDSL.arrayElementText(f, 0)),
                arrayStringTest("getString", type + " array", JsonDSL.arrayElementText(f, 3)),
                arrayStringTest("outOfBounds", null, JsonDSL.arrayElementText(f, 100)),
                arrayStringTest("negativeIndex", "true", JsonDSL.arrayElementText(f, -2)),
                testNull("onObject", JsonDSL.arrayElementText(f, 100))
        ));
    }

}
